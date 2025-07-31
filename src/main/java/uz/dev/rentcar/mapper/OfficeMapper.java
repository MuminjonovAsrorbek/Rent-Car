package uz.dev.rentcar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import uz.dev.rentcar.entity.Office;
import uz.dev.rentcar.payload.OfficeDTO;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/19/25 13:25
 **/

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface OfficeMapper {

    OfficeDTO toDTO(Office office);

    List<OfficeDTO> toDTO(List<Office> offices);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "pickupBookings", ignore = true)
    @Mapping(target = "returnBookings", ignore = true)
    Office toEntity(OfficeDTO officeDTO);

}
