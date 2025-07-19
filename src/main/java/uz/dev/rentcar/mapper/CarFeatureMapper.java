package uz.dev.rentcar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.dev.rentcar.dto.CarFeatureDTO;
import uz.dev.rentcar.entity.CarFeature;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarFeatureMapper {

    @Mapping(target = "carId",source = "car.id")
    CarFeatureDTO toDTO(CarFeature carFeature);

    @Mapping(target = "car.id",source = "carId")
    CarFeature toEntity(CarFeatureDTO carFeatureDTO);
}