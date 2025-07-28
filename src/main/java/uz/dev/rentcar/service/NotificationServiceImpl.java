package uz.dev.rentcar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dev.rentcar.entity.Notification;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.enums.NotificationTypeEnum;
import uz.dev.rentcar.exceptions.EntityNotFoundException;
import uz.dev.rentcar.mapper.NotificationMapper;
import uz.dev.rentcar.payload.NotificationDTO;
import uz.dev.rentcar.repository.NotificationRepository;
import uz.dev.rentcar.service.template.NotificationService;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public void createNotification(User user, String message, NotificationTypeEnum type) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setType(type);
        notification.setRead(false);
        notificationRepository.save(notification);
    }

    @Override
    public List<NotificationDTO> getNotificationsForUser(User user) {
        List<Notification> notifications = notificationRepository.findAllByUserIdOrderByCreatedAtDesc(user.getId());
        return notificationMapper.toDtoList(notifications);
    }

    @Override
    @Transactional
    public NotificationDTO markAsRead(Long notificationId, User user) {
        Notification notification = findNotificationByIdAndUser(notificationId, user);
        notification.setRead(true);
        return notificationMapper.toDto(notificationRepository.save(notification));
    }

    @Override
    @Transactional
    public void markAllAsRead(User user) {
        List<Notification> notifications = notificationRepository.findAllByUserIdOrderByCreatedAtDesc(user.getId());
        notifications.stream()
                .filter(n -> !n.isRead())
                .forEach(n -> n.setRead(true));
        notificationRepository.saveAll(notifications);
    }

    @Override
    public long getUnreadNotificationCount(User user) {
        return notificationRepository.countByUserAndIsReadFalse(user);
    }

    @Override
    public void deleteNotification(Long notificationId, User user) {
        if (!notificationRepository.existsById(notificationId)) {
            throw new EntityNotFoundException("Notification not found", HttpStatus.NOT_FOUND);
        }
        findNotificationByIdAndUser(notificationId, user);
        notificationRepository.deleteById(notificationId);
    }

    private Notification findNotificationByIdAndUser(Long notificationId, User user) {
        return notificationRepository.findById(notificationId)
                .filter(n -> n.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new EntityNotFoundException("Notification not found or access denied", HttpStatus.FORBIDDEN));
    }
}