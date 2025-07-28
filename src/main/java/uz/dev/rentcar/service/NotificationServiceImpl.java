package uz.dev.rentcar.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.entity.Booking;
import uz.dev.rentcar.entity.Notification;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.enums.BookingStatusEnum;
import uz.dev.rentcar.enums.NotificationTypeEnum;
import uz.dev.rentcar.mapper.NotificationMapper;
import uz.dev.rentcar.payload.CancelledBookingDTO;
import uz.dev.rentcar.payload.CompleteBookingDTO;
import uz.dev.rentcar.payload.ConfirmBookingDTO;
import uz.dev.rentcar.payload.SendEmailBookingDTO;
import uz.dev.rentcar.repository.NotificationRepository;
import uz.dev.rentcar.service.template.NotificationService;

import java.time.LocalDateTime;

/**
 * Created by: asrorbek
 * DateTime: 7/28/25 16:35
 **/

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final NotificationMapper notificationMapper;

    private final ApplicationEventPublisher applicationEventPublisher;


    @Override
    @Transactional
    public void createNotification(User user, String message, NotificationTypeEnum type, Booking booking) {

        Notification notification = new Notification();

        notification.setUser(user);
        notification.setMessage(message);
        notification.setType(type);

        notificationRepository.save(notification);

        SendEmailBookingDTO sendEmailBookingDTO = new SendEmailBookingDTO();

        sendEmailBookingDTO.setUserEmail(user.getEmail());
        sendEmailBookingDTO.setCarBrandAndModel(booking.getCar().getBrand() + " " + booking.getCar().getModel());
        sendEmailBookingDTO.setCreatedAt(booking.getCreatedAt().toLocalDateTime());
        sendEmailBookingDTO.setTotalPrice(booking.getTotalPrice());

        applicationEventPublisher.publishEvent(sendEmailBookingDTO);

    }

    @Override
    @Transactional
    public void updateBookingStatus(User user, String message, NotificationTypeEnum type, Long bookingId, BookingStatusEnum bookingStatus) {

        Notification notification = new Notification();

        notification.setUser(user);
        notification.setMessage(message);
        notification.setType(type);

        notificationRepository.save(notification);


        if (bookingStatus.equals(BookingStatusEnum.CANCELLED)) {

            CancelledBookingDTO cancelledBookingDTO = new CancelledBookingDTO(
                    bookingId,
                    LocalDateTime.now(),
                    user.getEmail()
            );

            applicationEventPublisher.publishEvent(cancelledBookingDTO);

        } else if (bookingStatus.equals(BookingStatusEnum.CONFIRMED)) {

            ConfirmBookingDTO confirmBookingDTO = new ConfirmBookingDTO(
                    bookingId,
                    user.getEmail()
            );

            applicationEventPublisher.publishEvent(confirmBookingDTO);

        } else {

            CompleteBookingDTO completeBookingDTO = new CompleteBookingDTO(
                    bookingId,
                    user.getEmail()
            );

            applicationEventPublisher.publishEvent(completeBookingDTO);

        }
    }
}