package uz.dev.rentcar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.dev.rentcar.dto.CarDTO;
import uz.dev.rentcar.dto.CategoryDTO;
import uz.dev.rentcar.entity.Car;
import uz.dev.rentcar.entity.Category;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarMapper {

    CarDTO toDTO(Car car);

    Car toEntity(CarDTO carDTO);
}