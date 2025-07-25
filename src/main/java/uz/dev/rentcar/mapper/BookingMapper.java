package uz.dev.rentcar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import uz.dev.rentcar.entity.Booking;
import uz.dev.rentcar.entity.Car;
import uz.dev.rentcar.payload.BookingDTO;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "car.id", target = "carId")
    @Mapping(source = "car", target = "carInfo", qualifiedByName = "carToCarInfo")
    @Mapping(source = "createdAt", target = "createdAt")
    BookingDTO toDto(Booking booking);

    @Named("carToCarInfo")
    default String carToCarInfo(Car car) {
        if (car == null) {
            return null;
        }
        return "Car information not available";
    }
}