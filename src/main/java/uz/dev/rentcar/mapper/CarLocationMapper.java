package uz.dev.rentcar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import uz.dev.rentcar.entity.CarLocation;
import uz.dev.rentcar.payload.CarLocationDTO;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/20/25 14:12
 **/

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarLocationMapper {

    @Mapping(target = "bookingId", source = "booking.id")
    CarLocationDTO toDTO(CarLocation carLocation);

    List<CarLocationDTO> toDTO(List<CarLocation> carLocations);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "booking", ignore = true)
    CarLocation toEntity(CarLocationDTO carLocationDTO);

}
