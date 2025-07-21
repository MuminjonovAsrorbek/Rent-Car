package uz.dev.rentcar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import uz.dev.rentcar.entity.Attachment;
import uz.dev.rentcar.payload.AttachmentDTO;

/**
 * Created by: asrorbek
 * DateTime: 7/20/25 14:09
 **/

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttachmentMapper {

    @Mapping(target = "carId", source = "car.id")
    @Mapping(target = "originalName", ignore = true)
    @Mapping(target = "size", ignore = true)
    AttachmentDTO toDTO(Attachment attachment);

}
