package uz.dev.rentcar.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.entity.Booking;
import uz.dev.rentcar.entity.CarLocation;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.enums.BookingStatusEnum;
import uz.dev.rentcar.enums.RoleEnum;
import uz.dev.rentcar.exceptions.EntityNotFoundException;
import uz.dev.rentcar.exceptions.LocationIllegalException;
import uz.dev.rentcar.mapper.CarLocationMapper;
import uz.dev.rentcar.payload.CarLocationDTO;
import uz.dev.rentcar.repository.BookingRepository;
import uz.dev.rentcar.repository.CarLocationRepository;
import uz.dev.rentcar.service.template.LocationService;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/25/25 23:08
 **/

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final BookingRepository bookingRepository;

    private final CarLocationRepository carLocationRepository;

    private final CarLocationMapper carLocationMapper;

    @Override
    @Transactional
    public CarLocationDTO createLocation(CarLocationDTO carLocationDTO) {

        Booking booking = bookingRepository.getByIdOrThrow(carLocationDTO.getBookingId());

        if (!booking.getStatus().equals(BookingStatusEnum.CONFIRMED)) {

            throw new LocationIllegalException("Booking is not confirmed, cannot create location.", HttpStatus.BAD_REQUEST);

        }

        CarLocation carLocation = carLocationMapper.toEntity(carLocationDTO);

        carLocation.setBooking(booking);

        CarLocation saved = carLocationRepository.save(carLocation);

        return carLocationMapper.toDTO(saved);

    }

    @Override
    @Transactional
    public CarLocationDTO updateLocation(Long id, CarLocationDTO carLocationDTO) {

        CarLocation carLocation = carLocationRepository.getByIdOrThrow(id);

        Booking booking = bookingRepository.getByIdOrThrow(carLocationDTO.getBookingId());

        if (!booking.getStatus().equals(BookingStatusEnum.CONFIRMED)) {

            throw new LocationIllegalException("Booking is not confirmed, cannot update location.", HttpStatus.BAD_REQUEST);

        }

        carLocation.setLatitude(carLocationDTO.getLatitude());
        carLocation.setLongitude(carLocationDTO.getLongitude());
        carLocation.setBooking(booking);

        CarLocation updated = carLocationRepository.save(carLocation);

        return carLocationMapper.toDTO(updated);

    }

    @Override
    public List<CarLocationDTO> getBookingLocations(Long bookingId, User currentUser) {

        Booking booking = bookingRepository.getByIdOrThrow(bookingId);

        if (!booking.getUser().equals(currentUser) || !currentUser.getRole().equals(RoleEnum.ADMIN)) {

            throw new SecurityException("You do not have permission to access this booking's locations.");

        }

        List<CarLocation> carLocations = carLocationRepository.findByBookingId(bookingId);

        return carLocationMapper.toDTO(carLocations);

    }
}
