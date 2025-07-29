package uz.dev.rentcar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.payload.NotificationDTO;
import uz.dev.rentcar.service.template.NotificationService;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/28/25 18:40
 **/

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/my/all-notifications")
    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    public List<NotificationDTO> getMyAllNotifications(@AuthenticationPrincipal User currentUser) {

        return notificationService.getMyAllNotifications(currentUser);

    }

    @GetMapping("/my/unread-notifications")
    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    public List<NotificationDTO> getMyUnreadNotifications(@AuthenticationPrincipal User currentUser) {

        return notificationService.getMyUnreadNotifications(currentUser);

    }

    @PatchMapping("/my/mark-all-as-read")
    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    public ResponseEntity<?> markAllNotificationsAsRead(@AuthenticationPrincipal User currentUser) {

        notificationService.markAllNotificationsAsRead(currentUser);

        return ResponseEntity.ok("All notifications marked as read.");
    }

    @PatchMapping("/my/mark-all-as-unread")
    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    public ResponseEntity<?> markAllNotificationsAsUnread(@AuthenticationPrincipal User currentUser) {

        notificationService.markAllNotificationsAsUnread(currentUser);

        return ResponseEntity.ok("All notifications marked as unread.");

    }

    @PatchMapping("/my/mark-as-read/{notificationId}")
    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    public NotificationDTO markNotificationAsRead(@AuthenticationPrincipal User currentUser, @PathVariable Long notificationId) {

        return notificationService.markNotificationAsRead(currentUser, notificationId);

    }
}
