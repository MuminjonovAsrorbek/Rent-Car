package uz.dev.rentcar.service.template;

import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.enums.NotificationTypeEnum;
import uz.dev.rentcar.payload.NotificationDTO;
import java.util.List;

public interface NotificationService {

    void createNotification(User user, String message, NotificationTypeEnum type);
    List<NotificationDTO> getNotificationsForUser(User user);
    NotificationDTO markAsRead(Long notificationId, User user);
    void markAllAsRead(User user);
    long getUnreadNotificationCount(User user);
    void deleteNotification(Long notificationId, User user);
}
