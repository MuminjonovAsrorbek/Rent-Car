package uz.dev.rentcar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.dev.rentcar.entity.CarFeature;
import uz.dev.rentcar.payload.CarFeatureDTO;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarFeatureMapper {

    @Mapping(target = "carId", source = "car.id")
    CarFeatureDTO toDTO(CarFeature carFeature);

    List<CarFeatureDTO> toDTO(List<CarFeature> carFeatures);
}