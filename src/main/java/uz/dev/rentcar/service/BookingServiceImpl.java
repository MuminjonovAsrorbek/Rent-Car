package uz.dev.rentcar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dev.rentcar.entity.*;
import uz.dev.rentcar.enums.BookingStatusEnum;
import uz.dev.rentcar.enums.RoleEnum;
import uz.dev.rentcar.exceptions.*;
import uz.dev.rentcar.mapper.BookingMapper;
import uz.dev.rentcar.payload.*;
import uz.dev.rentcar.repository.*;
import uz.dev.rentcar.service.template.BookingService;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingHistoryRepository bookingHistoryRepository;
    private final CarRepository carRepository;
    private final OfficeRepository officeRepository;
    private final PromoCodeRepository promoCodeRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;

    @Override
    @Transactional
    public BookingDTO createBooking(BookingCreateDTO dto, User currentUser) {
        validateBookingDates(dto.getPickupDate(), dto.getReturnDate());

        Car car = carRepository.findById(dto.getCarId())
                .orElseThrow(() -> new EntityNotFoundException("Car not found", HttpStatus.NOT_FOUND));

        Office pickupOffice = officeRepository.findById(dto.getPickupOfficeId())
                .orElseThrow(() -> new EntityNotFoundException("Pickup office not found", HttpStatus.NOT_FOUND));

        List<BookingStatusEnum> activeStatuses = List.of(BookingStatusEnum.PENDING, BookingStatusEnum.CONFIRMED);
        if (bookingRepository.existsOverlappingBooking(car.getId(), dto.getPickupDate(), dto.getReturnDate(), activeStatuses)) {
            throw new CarNotAvailableException("This car is not available for the selected dates.", HttpStatus.CONFLICT);
        }

        long hours = ChronoUnit.HOURS.between(dto.getPickupDate(), dto.getReturnDate());
        long totalPrice = (long) (car.getPricePerDay() / 24.0 * hours);

        if (dto.getPromoCode() != null && !dto.getPromoCode().isBlank()) {
            PromoCode promoCode = promoCodeRepository.findByCode(dto.getPromoCode())
                    .orElseThrow(() -> new InvalidRequestException("Invalid promo code.", HttpStatus.BAD_REQUEST));
            totalPrice -= (long) (totalPrice * (promoCode.getDiscount().doubleValue() / 100.0));
        }

        Booking booking = new Booking();
        booking.setUser(currentUser);
        booking.setCar(car);
        booking.setPickupOffice(pickupOffice);
        booking.setPickupDate(dto.getPickupDate());
        booking.setReturnDate(dto.getReturnDate());
        booking.setTotalPrice(totalPrice);
        booking.setStatus(BookingStatusEnum.PENDING);
        booking.setForSelf(dto.isForSelf());

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
        return bookingMapper.toDto(savedBooking);
    }

    @Override
    public BookingDTO getBookingById(Long id, User currentUser) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found", HttpStatus.NOT_FOUND));
        if (!isAdmin(currentUser) && !Objects.equals(booking.getUser().getId(), currentUser.getId())) {
            throw new SecurityException("You do not have permission to view this booking.");
        }
        return bookingMapper.toDto(booking);
    }

    @Override
    public List<BookingDTO> getMyBookings(User currentUser) {
        return bookingRepository.findAllByUserId(currentUser.getId())
                .stream().map(bookingMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> getBookingsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found", HttpStatus.NOT_FOUND);
        }
        return bookingRepository.findAllByUserId(userId)
                .stream().map(bookingMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookingDTO cancelBooking(Long id, User currentUser) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found", HttpStatus.NOT_FOUND));

        if (!isAdmin(currentUser) && !Objects.equals(booking.getUser().getId(), currentUser.getId())) {
            throw new SecurityException("You do not have permission to cancel this booking.");
        }
        if (booking.getStatus() != BookingStatusEnum.PENDING && booking.getStatus() != BookingStatusEnum.CONFIRMED) {
            throw new InvalidRequestException("Only PENDING or CONFIRMED bookings can be cancelled.", HttpStatus.BAD_REQUEST);
        }
        booking.setStatus(BookingStatusEnum.CANCELLED);
        createBookingHistory(booking, BookingStatusEnum.CANCELLED);
        return bookingMapper.toDto(bookingRepository.save(booking));
    }

    @Override
    @Transactional
    public BookingDTO confirmBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found", HttpStatus.NOT_FOUND));
        if (booking.getStatus() != BookingStatusEnum.PENDING) {
            throw new InvalidRequestException("Only PENDING bookings can be confirmed.", HttpStatus.BAD_REQUEST);
        }
        booking.setStatus(BookingStatusEnum.CONFIRMED);
        createBookingHistory(booking, BookingStatusEnum.CONFIRMED);
        return bookingMapper.toDto(bookingRepository.save(booking));
    }

    @Override
    @Transactional
    public BookingDTO completeBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found", HttpStatus.NOT_FOUND));
        if (booking.getStatus() != BookingStatusEnum.CONFIRMED) {
            throw new InvalidRequestException("Only CONFIRMED bookings can be completed.", HttpStatus.BAD_REQUEST);
        }
        booking.setStatus(BookingStatusEnum.COMPLETED);
        createBookingHistory(booking, BookingStatusEnum.COMPLETED);
        return bookingMapper.toDto(bookingRepository.save(booking));
    }

    private void createBookingHistory(Booking booking, BookingStatusEnum status) {
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
        return user.getRole() == RoleEnum.ADMIN;
    }
}