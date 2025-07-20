package uz.dev.rentcar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.dev.rentcar.entity.Car;
import uz.dev.rentcar.payload.CarDTO;
import uz.dev.rentcar.payload.request.CreateCarDTO;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CarFeatureMapper.class, BookingMapper.class, ReviewMapper.class, CarImageMapper.class, CarLocationMapper.class, FavoriteMapper.class}
)
public interface CarMapper {

    @Mapping(target = "categories", ignore = true)
    CarDTO toDTO(Car car);

    List<CarDTO> toDTO(List<Car> cars);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "features", ignore = true)
    @Mapping(target = "locations", ignore = true)
    @Mapping(target = "favorites", ignore = true)
    Car toEntity(CreateCarDTO carDTO);
}