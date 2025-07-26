package uz.dev.rentcar.service.template;

import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.payload.CarLocationDTO;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/25/25 23:08
 **/

public interface LocationService {
    CarLocationDTO createLocation(CarLocationDTO carLocationDTO);

    CarLocationDTO updateLocation(Long id, CarLocationDTO carLocationDTO);

    List<CarLocationDTO> getBookingLocations(Long bookingId, User currentUser);
}
