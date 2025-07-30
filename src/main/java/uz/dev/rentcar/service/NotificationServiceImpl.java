package uz.dev.rentcar.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.entity.Booking;
import uz.dev.rentcar.entity.Notification;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.entity.template.AbsLongEntity;
import uz.dev.rentcar.enums.BookingStatusEnum;
import uz.dev.rentcar.enums.NotificationTypeEnum;
import uz.dev.rentcar.enums.RoleEnum;
import uz.dev.rentcar.mapper.NotificationMapper;
import uz.dev.rentcar.payload.*;
import uz.dev.rentcar.payload.request.CancelledBookingDTO;
import uz.dev.rentcar.repository.NotificationRepository;
import uz.dev.rentcar.service.template.NotificationService;

import java.time.LocalDateTime;
import java.util.List;

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
        sendEmailBookingDTO.setBookingId(booking.getId());
        sendEmailBookingDTO.setCarBrandAndModel(booking.getCar().getBrand() + " " + booking.getCar().getModel());
        sendEmailBookingDTO.setCreatedAt(booking.getCreatedAt().toLocalDateTime());
        sendEmailBookingDTO.setTotalPrice(booking.getTotalPrice());
        sendEmailBookingDTO.setPaymentMethod(booking.getPayment().getPaymentMethod());

        applicationEventPublisher.publishEvent(sendEmailBookingDTO);

    }

    @Override
    @Transactional
    public void checkReturnDeadlines(ReturnDeadlineDTO dto) {

        Notification notification = new Notification();

        notification.setUser(dto.getUser());
        notification.setMessage("Your return date is over, please pay the remaining amount.");
        notification.setType(NotificationTypeEnum.WARNING);

        notificationRepository.save(notification);

        applicationEventPublisher.publishEvent(dto);

    }

    @Override
    @Transactional
    public void checkOverdueReturns(SendPenaltyDTO dto) {

        Notification notification = new Notification();

        notification.setUser(dto.getUser());
        notification.setMessage("You have overdue returns. Please pay the penalty amount of " + dto.getPenaltyAmount() + " Tiyin.");
        notification.setType(NotificationTypeEnum.WARNING);

        notificationRepository.save(notification);

        applicationEventPublisher.publishEvent(dto);

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

    @Override
    public List<NotificationDTO> getMyAllNotifications(User currentUser) {

        Sort sort = Sort.by((AbsLongEntity.Fields.id)).descending();

        List<Notification> notifications = notificationRepository.findByUserId(currentUser.getId(), sort);

        return notificationMapper.toDTO(notifications);

    }

    @Override
    public List<NotificationDTO> getMyUnreadNotifications(User currentUser) {

        Sort sort = Sort.by((AbsLongEntity.Fields.id)).descending();

        List<Notification> notifications = notificationRepository.findByUserIdAndIsReadFalse(currentUser.getId() , sort);

        return notificationMapper.toDTO(notifications);

    }

    @Override
    @Transactional
    public void markAllNotificationsAsRead(User currentUser) {

        Sort sort = Sort.by((AbsLongEntity.Fields.id)).descending();

        List<Notification> notifications = notificationRepository.findByUserId(currentUser.getId(), sort);

        for (Notification notification : notifications) {
            notification.setRead(true);
        }

        notificationRepository.saveAll(notifications);

        log.info("All notifications marked as read for user: {}", currentUser.getEmail());

    }

    @Override
    @Transactional
    public void markAllNotificationsAsUnread(User currentUser) {

        Sort sort = Sort.by((AbsLongEntity.Fields.id)).descending();

        List<Notification> notifications = notificationRepository.findByUserId(currentUser.getId(), sort);

        for (Notification notification : notifications) {
            notification.setRead(false);
        }

        notificationRepository.saveAll(notifications);

        log.info("All notifications marked as unread for user: {}", currentUser.getEmail());

    }

    @Override
    @Transactional
    public NotificationDTO markNotificationAsRead(User currentUser, Long notificationId) {

        Notification notification = notificationRepository.findByIdOrThrowException(notificationId);

        if (!notification.getUser().getId().equals(currentUser.getId()) || !currentUser.getRole().equals(RoleEnum.ADMIN)) {

            throw new SecurityException("You do not have permission to mark this notification as read.");

        }

        notification.setRead(true);

        Notification save = notificationRepository.save(notification);

        return notificationMapper.toDTO(save);
    }
}