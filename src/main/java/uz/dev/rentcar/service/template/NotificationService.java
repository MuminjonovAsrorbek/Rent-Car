package uz.dev.rentcar.service.template;

import uz.dev.rentcar.entity.Booking;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.enums.BookingStatusEnum;
import uz.dev.rentcar.enums.NotificationTypeEnum;
import uz.dev.rentcar.payload.NotificationDTO;
import uz.dev.rentcar.payload.ReturnDeadlineDTO;
import uz.dev.rentcar.payload.SendPenaltyDTO;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/28/25 16:34
 **/

public interface NotificationService {

    void createNotification(User user, String message, NotificationTypeEnum type, Booking booking);

    void updateBookingStatus(User user, String message, NotificationTypeEnum type, Long bookingId, BookingStatusEnum bookingStatus);

    List<NotificationDTO> getMyAllNotifications(User currentUser);

    List<NotificationDTO> getMyUnreadNotifications(User currentUser);

    void markAllNotificationsAsRead(User currentUser);

    void markAllNotificationsAsUnread(User currentUser);

    NotificationDTO markNotificationAsRead(User currentUser, Long notificationId);

    void checkReturnDeadlines(ReturnDeadlineDTO dto);

    void checkOverdueReturns(SendPenaltyDTO dto);
}
