package uz.dev.rentcar.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dev.rentcar.entity.*;
import uz.dev.rentcar.entity.template.AbsLongEntity;
import uz.dev.rentcar.enums.BookingStatusEnum;
import uz.dev.rentcar.enums.NotificationTypeEnum;
import uz.dev.rentcar.enums.PaymentStatus;
import uz.dev.rentcar.enums.RoleEnum;
import uz.dev.rentcar.exceptions.CarNotAvailableException;
import uz.dev.rentcar.exceptions.EntityNotFoundException;
import uz.dev.rentcar.exceptions.InvalidRequestException;
import uz.dev.rentcar.mapper.BookingMapper;
import uz.dev.rentcar.payload.BookingDTO;
import uz.dev.rentcar.payload.request.BookingCreateDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.repository.*;
import uz.dev.rentcar.service.template.BookingService;
import uz.dev.rentcar.service.template.NotificationService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final BookingHistoryRepository bookingHistoryRepository;

    private final CarRepository carRepository;

    private final OfficeRepository officeRepository;

    private final PromoCodeRepository promoCodeRepository;

    private final UserRepository userRepository;

    private final BookingMapper bookingMapper;

    private final NotificationService notificationService;

    @Override
    @Transactional
    public BookingDTO createBooking(BookingCreateDTO dto, User currentUser) {

        validateBookingDates(dto.getPickupDate(), dto.getReturnDate());

        Car car = carRepository.getByIdOrThrow(dto.getCarId());

        Office pickupOffice = officeRepository.getByIdOrThrow(dto.getPickupOfficeId());

        Office returnOffice = officeRepository.getByIdOrThrow(dto.getReturnOfficeId());

        List<BookingStatusEnum> activeStatuses = List.of(BookingStatusEnum.PENDING, BookingStatusEnum.CONFIRMED);

        if (bookingRepository.existsOverlappingBooking(car.getId(), dto.getPickupDate(), dto.getReturnDate(), activeStatuses)) {

            throw new CarNotAvailableException("This car is not available for the selected dates.", HttpStatus.CONFLICT);

        }

        long hours = ChronoUnit.HOURS.between(dto.getPickupDate(), dto.getReturnDate());

        long totalPrice = (long) (car.getPricePerDay() / 24.0 * hours);

        Booking booking = new Booking();

        if (dto.getPromoCode() != null && !dto.getPromoCode().isBlank()) {

            PromoCode promoCode = promoCodeRepository.findByCodeOrThrow(dto.getPromoCode());

            totalPrice -= (long) (totalPrice * (promoCode.getDiscount().doubleValue() / 100.0));

            booking.setPromoCode(promoCode);
        }

        booking.setUser(currentUser);
        booking.setCar(car);
        booking.setPickupOffice(pickupOffice);
        booking.setReturnOffice(returnOffice);
        booking.setPickupDate(dto.getPickupDate());
        booking.setReturnDate(dto.getReturnDate());
        booking.setTotalPrice(totalPrice);
        booking.setStatus(BookingStatusEnum.PENDING);
        booking.setIsForSelf(dto.isForSelf());

        if (!dto.isForSelf()) {

            if (dto.getRecipientFullName() == null || dto.getRecipientPhone() == null) {

                throw new InvalidRequestException("Recipient full name and phone are required.", HttpStatus.BAD_REQUEST);

            }

            booking.setRecipientFullName(dto.getRecipientFullName());

            booking.setRecipientPhone(dto.getRecipientPhone());
        } else {

            booking.setRecipientFullName(currentUser.getFullName());

            booking.setRecipientPhone(currentUser.getPhoneNumber());

        }

        Booking savedBooking = bookingRepository.save(booking);

        createBookingHistory(savedBooking, savedBooking.getStatus());

        car.setAvailable(false);

        carRepository.save(car);

        Payment payment = new Payment();

        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setBooking(savedBooking);
        payment.setAmount(booking.getTotalPrice());

        booking.setPayment(payment);

        log.info("Booking created successfully for user: {}, car: {}, pickup: {}, return: {}",
                currentUser.getId(), car.getId(), dto.getPickupDate(), dto.getReturnDate());

        Booking save = bookingRepository.save(booking);

        notificationService.createNotification(currentUser, "Your booking has been created successfully.",
                NotificationTypeEnum.INFO, save);

        return bookingMapper.toDTO(save);
    }

    @Override
    public BookingDTO getBookingById(Long id, User currentUser) {

        Booking booking = bookingRepository.getByIdOrThrow(id);

        if (isAdmin(currentUser) && !Objects.equals(booking.getUser().getId(), currentUser.getId())) {

            throw new SecurityException("You do not have permission to view this booking.");

        }

        return bookingMapper.toDTO(booking);
    }

    @Override
    public PageableDTO getMyBookings(User currentUser, int page, int size) {

        Sort sort = Sort.by(AbsLongEntity.Fields.id).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Booking> bookingPage = bookingRepository.findByUserId(currentUser.getId(), pageable);

        List<Booking> bookings = bookingPage.getContent();

        log.info("Fetching bookings for user: {}", currentUser.getId());

        return new PageableDTO(
                bookingPage.getSize(),
                bookingPage.getTotalElements(),
                bookingPage.getTotalPages(),
                bookingPage.hasNext(),
                bookingPage.hasPrevious(),
                bookingMapper.toDTO(bookings)
        );
    }

    @Override
    public PageableDTO getBookingsByUserId(Long userId, int page, int size) {

        if (!userRepository.existsById(userId)) {

            throw new EntityNotFoundException("User not found", HttpStatus.NOT_FOUND);

        }

        Sort sort = Sort.by(AbsLongEntity.Fields.id).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Booking> bookingPage = bookingRepository.findByUserId(userId, pageable);

        List<Booking> bookings = bookingPage.getContent();

        log.info("Fetching bookings for user: {}", userId);

        return new PageableDTO(
                bookingPage.getSize(),
                bookingPage.getTotalElements(),
                bookingPage.getTotalPages(),
                bookingPage.hasNext(),
                bookingPage.hasPrevious(),
                bookingMapper.toDTO(bookings)
        );
    }

    @Override
    @Transactional
    public BookingDTO cancelBooking(Long id, User currentUser) {

        Booking booking = bookingRepository.getByIdOrThrow(id);

        if (isAdmin(currentUser) && !Objects.equals(booking.getUser().getId(), currentUser.getId())) {

            throw new SecurityException("You do not have permission to cancel this booking.");

        }

        if (booking.getStatus() != BookingStatusEnum.PENDING && booking.getStatus() != BookingStatusEnum.CONFIRMED) {

            throw new InvalidRequestException("Only PENDING or CONFIRMED bookings can be cancelled.", HttpStatus.BAD_REQUEST);

        }

        booking.setStatus(BookingStatusEnum.CANCELLED);

        createBookingHistory(booking, BookingStatusEnum.CANCELLED);

        Booking save = bookingRepository.save(booking);

        Car car = booking.getCar();

        car.setAvailable(true);

        carRepository.save(car);

        log.info("Booking cancelled successfully for user: {}, booking ID: {}", currentUser.getId(), id);

        notificationService.updateBookingStatus(currentUser, "Your booking has been cancelled successfully.",
                NotificationTypeEnum.WARNING, id, BookingStatusEnum.CANCELLED);

        return bookingMapper.toDTO(save);
    }

    @Override
    @Transactional
    public BookingDTO confirmBooking(Long id) {

        Booking booking = bookingRepository.getByIdOrThrow(id);

        if (booking.getStatus() != BookingStatusEnum.PENDING) {

            throw new InvalidRequestException("Only PENDING bookings can be confirmed.", HttpStatus.BAD_REQUEST);

        }

        Payment payment = booking.getPayment();

        if (!payment.getStatus().equals(PaymentStatus.COMPLETED)) {

            throw new InvalidRequestException("Payment is not completed yet.", HttpStatus.BAD_REQUEST);

        }

        booking.setStatus(BookingStatusEnum.CONFIRMED);

        createBookingHistory(booking, BookingStatusEnum.CONFIRMED);

        Booking save = bookingRepository.save(booking);

        Car car = booking.getCar();

        car.setAvailable(false);

        carRepository.save(car);

        Long userId = booking.getUser().getId();

        log.info("Booking confirmed successfully for user: {}, booking ID: {}", userId, id);

        notificationService.updateBookingStatus(booking.getUser(), "Your booking has been confirmed successfully.",
                NotificationTypeEnum.INFO, id, BookingStatusEnum.CONFIRMED);

        return bookingMapper.toDTO(save);
    }

    @Override
    @Transactional
    public BookingDTO completeBooking(Long id) {

        Booking booking = bookingRepository.getByIdOrThrow(id);

        if (booking.getStatus() != BookingStatusEnum.CONFIRMED) {

            throw new InvalidRequestException("Only CONFIRMED bookings can be completed.", HttpStatus.BAD_REQUEST);

        }

        booking.setStatus(BookingStatusEnum.COMPLETED);

        createBookingHistory(booking, BookingStatusEnum.COMPLETED);

        Booking save = bookingRepository.save(booking);

        Car car = booking.getCar();

        car.setAvailable(true);

        carRepository.save(car);

        Long userId = booking.getUser().getId();

        log.info("Booking completed successfully for user: {}, booking ID: {}", userId, id);

        notificationService.updateBookingStatus(booking.getUser(), "Your booking has been completed successfully.",
                NotificationTypeEnum.INFO, id, BookingStatusEnum.COMPLETED);

        return bookingMapper.toDTO(save);
    }

    @Transactional
    public void createBookingHistory(Booking booking, BookingStatusEnum status) {

        BookingHistory history = new BookingHistory();

        history.setBooking(booking);

        history.setStatus(status);

        bookingHistoryRepository.save(history);
    }

    private void validateBookingDates(LocalDateTime pickup, LocalDateTime ret) {
        if (pickup == null || ret == null || !ret.isAfter(pickup)) {
            throw new InvalidRequestException("Return date must be after pickup date.", HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isAdmin(User user) {
        return user.getRole() != RoleEnum.ADMIN;
    }
}