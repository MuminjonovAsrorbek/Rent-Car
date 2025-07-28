package uz.dev.rentcar.service.template;

import uz.dev.rentcar.entity.Booking;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.enums.BookingStatusEnum;
import uz.dev.rentcar.enums.NotificationTypeEnum;

/**
 * Created by: asrorbek
 * DateTime: 7/28/25 16:34
 **/

public interface NotificationService {

    void createNotification(User user, String message, NotificationTypeEnum type, Booking booking);

    void updateBookingStatus(User user, String message, NotificationTypeEnum type, Long bookingId, BookingStatusEnum bookingStatus);

}
