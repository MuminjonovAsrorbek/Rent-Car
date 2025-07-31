package uz.dev.rentcar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import uz.dev.rentcar.entity.Booking;
import uz.dev.rentcar.entity.PromoCode;
import uz.dev.rentcar.payload.BookingDTO;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {PaymentMapper.class, CarLocationMapper.class, OfficeMapper.class}
)
public interface BookingMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userFullName", source = "user.fullName")
    @Mapping(target = "carId", source = "car.id")
    @Mapping(target = "carBrand", source = "car.brand")
    @Mapping(target = "carModel", source = "car.model")
    @Mapping(target = "carSeats", source = "car.seats")
    @Mapping(target = "carFuelType", source = "car.fuelType")
    @Mapping(target = "carFuelConsumption", source = "car.fuelConsumption")
    @Mapping(target = "carTransmission", source = "car.transmission")
    @Mapping(target = "hasPromoCode", source = "promoCode", qualifiedByName = "hasPromoCode")
    BookingDTO toDTO(Booking booking);


    List<BookingDTO> toDTO(List<Booking> bookings);

    @Named("hasPromoCode")
    default boolean hasPromoCode(PromoCode promoCode) {
        return promoCode != null;
    }
}