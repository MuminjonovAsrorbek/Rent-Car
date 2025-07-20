package uz.dev.rentcar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.dev.rentcar.payload.CarFeatureDTO;
import uz.dev.rentcar.entity.CarFeature;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CarMapper.class})
public interface CarFeatureMapper {

    @Mapping(target = "carId", source = "car.id")
    CarFeatureDTO toDTO(CarFeature carFeature);

    List<CarFeatureDTO> toDTO(List<CarFeature> carFeatures);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "car.id", source = "carId")
    CarFeature toEntity(CarFeatureDTO carFeatureDTO);

}