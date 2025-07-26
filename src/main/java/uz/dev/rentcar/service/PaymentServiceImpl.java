package uz.dev.rentcar.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.entity.Booking;
import uz.dev.rentcar.entity.Payment;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.enums.PaymentStatus;
import uz.dev.rentcar.enums.PaymetMethodEnum;
import uz.dev.rentcar.enums.RoleEnum;
import uz.dev.rentcar.exceptions.EntityAlreadyExistException;
import uz.dev.rentcar.mapper.PaymentMapper;
import uz.dev.rentcar.payload.PaymentDTO;
import uz.dev.rentcar.repository.BookingRepository;
import uz.dev.rentcar.repository.PaymentRepository;
import uz.dev.rentcar.service.template.PaymentService;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by: asrorbek
 * DateTime: 7/26/25 17:09
 **/

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final BookingRepository bookingRepository;

    private final PaymentMapper paymentMapper;

    @Override
    public List<String> getPaymentMethods() {

        PaymetMethodEnum[] values = PaymetMethodEnum.values();

        return Stream.of(values)
                .map(Enum::name)
                .toList();

    }

    @Override
    @Transactional
    public PaymentDTO cretePayment(PaymentDTO paymentDTO, User currentUser) {

        Booking booking = bookingRepository.getByIdOrThrow(paymentDTO.getBookingId());

        User user = booking.getUser();

        if (!user.getId().equals(currentUser.getId()) || !currentUser.getRole().equals(RoleEnum.ADMIN)) {

            throw new SecurityException("You are not authorized to create a payment for this booking.");

        }

        boolean exists = paymentRepository.existsByBookingId(paymentDTO.getBookingId());

        if (exists)
            throw new EntityAlreadyExistException("Payment already exist for this booking.", HttpStatus.BAD_REQUEST);

        Payment payment = paymentMapper.toEntity(paymentDTO);

        payment.setBooking(booking);

        payment.setStatus(PaymentStatus.COMPLETED);

        Payment save = paymentRepository.save(payment);

        return paymentMapper.toDTO(save);
    }

    @Override
    public PaymentDTO getPaymentById(Long id) {

        Payment payment = paymentRepository.getByIdOrThrow(id);

        return paymentMapper.toDTO(payment);

    }

    @Override
    public PaymentDTO getPaymentByBookingId(Long bookingId, User currentUser) {

        Booking booking = bookingRepository.getByIdOrThrow(bookingId);

        User user = booking.getUser();

        if (!user.getId().equals(currentUser.getId()) || !currentUser.getRole().equals(RoleEnum.ADMIN)) {

            throw new SecurityException("You are not authorized to view the payment for this booking.");

        }

        Payment payment = paymentRepository.findByBookingIdOrThrowException(bookingId);

        return paymentMapper.toDTO(payment);

    }

    @Override
    public List<PaymentDTO> getUserPayments(User currentUser) {

        List<Payment> payments = paymentRepository.findByBookingUserId(currentUser.getId());

        return paymentMapper.toDTO(payments);

    }

    @Override
    @Transactional
    public PaymentDTO confirmPayment(Long bookingId) {

        Booking booking = bookingRepository.getByIdOrThrow(bookingId);

        Payment payment = paymentRepository.findByBookingIdOrThrowException(bookingId);

        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new IllegalStateException("Payment is not Pending.");
        }

        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setBooking(booking);

        Payment updatedPayment = paymentRepository.save(payment);

        return paymentMapper.toDTO(updatedPayment);

    }

    @Override
    @Transactional
    public PaymentDTO cancelPayment(Long bookingId, User currentUser) {

        Booking booking = bookingRepository.getByIdOrThrow(bookingId);

        User user = booking.getUser();

        if (!user.getId().equals(currentUser.getId()) || !currentUser.getRole().equals(RoleEnum.ADMIN)) {

            throw new SecurityException("You are not authorized to cancel the payment for this booking.");

        }

        Payment payment = paymentRepository.findByBookingIdOrThrowException(bookingId);

        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new IllegalStateException("Payment is not Pending.");
        }

        payment.setStatus(PaymentStatus.FAILED);
        payment.setBooking(booking);

        Payment updatedPayment = paymentRepository.save(payment);

        PaymentDTO dto = paymentMapper.toDTO(updatedPayment);

        paymentRepository.delete(payment);

        return dto;

    }
}
