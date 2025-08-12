package uz.dev.rentcar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.payload.NotificationDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.service.template.NotificationService;

/**
 * Created by: asrorbek
 * DateTime: 7/28/25 18:40
 **/

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
@Tag(name = "Notification API", description = "Notification API")
@SecurityRequirement(name = "bearerAuth")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/my/all-notifications")
    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    @Operation(
            summary = "Get all notifications for the current user",
            description = "This endpoint retrieves all notifications for the authenticated user."
    )
    public PageableDTO getMyAllNotifications(@RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "size", defaultValue = "10") int size,
                                             @AuthenticationPrincipal User currentUser) {

        return notificationService.getMyAllNotifications(currentUser, page, size);

    }

    @GetMapping("/my/unread-notifications")
    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    @Operation(
            summary = "Get unread notifications for the current user",
            description = "This endpoint retrieves all unread notifications for the authenticated user."
    )
    public PageableDTO getMyUnreadNotifications(@RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "10") int size,
                                                @AuthenticationPrincipal User currentUser) {

        return notificationService.getMyUnreadNotifications(currentUser, page, size);

    }

    @PatchMapping("/my/mark-all-as-read")
    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    @Operation(
            summary = "Mark all notifications as read",
            description = "This endpoint marks all notifications for the authenticated user as read."
    )
    public ResponseEntity<?> markAllNotificationsAsRead(@AuthenticationPrincipal User currentUser) {

        notificationService.markAllNotificationsAsRead(currentUser);

        return ResponseEntity.ok("All notifications marked as read.");
    }

    @PatchMapping("/my/mark-all-as-unread")
    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    @Operation(
            summary = "Mark all notifications as unread",
            description = "This endpoint marks all notifications for the authenticated user as unread."
    )
    public ResponseEntity<?> markAllNotificationsAsUnread(@AuthenticationPrincipal User currentUser) {

        notificationService.markAllNotificationsAsUnread(currentUser);

        return ResponseEntity.ok("All notifications marked as unread.");

    }

    @PatchMapping("/my/mark-as-read/{notificationId}")
    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    @Operation(
            summary = "Mark a specific notification as read",
            description = "This endpoint marks a specific notification as read for the authenticated user."
    )

    public NotificationDTO markNotificationAsRead(@AuthenticationPrincipal User currentUser,
                                                  @Parameter(description = "Notification id", example = "1") @PathVariable Long notificationId) {

        return notificationService.markNotificationAsRead(currentUser, notificationId);

    }
}
