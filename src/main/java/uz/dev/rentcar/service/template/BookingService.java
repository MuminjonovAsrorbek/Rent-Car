package uz.dev.rentcar.service.template;

import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.payload.request.BookingCreateDTO;
import uz.dev.rentcar.payload.BookingDTO;
import uz.dev.rentcar.payload.BookingExtendDTO;
import java.util.List;

public interface BookingService {
    BookingDTO createBooking(BookingCreateDTO dto, User currentUser);
    BookingDTO getBookingById(Long id, User currentUser);
    List<BookingDTO> getMyBookings(User currentUser);
    List<BookingDTO> getBookingsByUserId(Long userId);
    BookingDTO cancelBooking(Long id, User currentUser);
    BookingDTO confirmBooking(Long id);
    BookingDTO completeBooking(Long id);
    BookingDTO extendBooking(Long id, BookingExtendDTO dto, User currentUser);
}