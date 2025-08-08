package uz.dev.rentcar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.dev.rentcar.entity.Attachment;
import uz.dev.rentcar.payload.AttachmentDTO;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/20/25 14:09
 **/

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttachmentMapper {

    @Mapping(
            target = "url",
            expression = "java(\"/api/attachments/open/download/\" + attachment.getId())"
    )
    AttachmentDTO toDTO(Attachment attachment);

    List<AttachmentDTO> toDTO(List<Attachment> attachments);
}
