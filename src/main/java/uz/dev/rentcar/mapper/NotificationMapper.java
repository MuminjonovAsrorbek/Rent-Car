package uz.dev.rentcar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import uz.dev.rentcar.entity.Notification;
import uz.dev.rentcar.payload.NotificationDTO;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/19/25 14:39
 **/

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationMapper {

    @Mapping(target = "userId", source = "user.id")
    NotificationDTO toDTO(Notification notification);

    List<NotificationDTO> toDTO(List<Notification> notifications);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "read", constant = "false")
    Notification toEntity(NotificationDTO notificationDTO);
}
