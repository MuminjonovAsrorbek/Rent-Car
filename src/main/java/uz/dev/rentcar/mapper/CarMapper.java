package uz.dev.rentcar.mapper;

import org.mapstruct.*;
import uz.dev.rentcar.entity.Car;
import uz.dev.rentcar.payload.CarDTO;
import uz.dev.rentcar.payload.request.CreateCarDTO;
import uz.dev.rentcar.payload.request.UpdateCarDTO;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CarFeatureMapper.class, BookingMapper.class, ReviewMapper.class, AttachmentMapper.class, CarLocationMapper.class, FavoriteMapper.class}
)
public interface CarMapper {

    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "images", source = "attachments")
    CarDTO toDTO(Car car);

    List<CarDTO> toDTO(List<Car> cars);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "attachments", ignore = true)
    @Mapping(target = "features", ignore = true)
    @Mapping(target = "favorites", ignore = true)
    Car toEntity(CreateCarDTO carDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "attachments", ignore = true)
    @Mapping(target = "features", ignore = true)
    @Mapping(target = "favorites", ignore = true)
    Car updateCar(UpdateCarDTO updateCarDTO, @MappingTarget Car car);
}