package uz.dev.rentcar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.dev.rentcar.payload.CarDTO;
import uz.dev.rentcar.entity.Car;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarMapper {

    CarDTO toDTO(Car car);

    Car toEntity(CarDTO carDTO);
}