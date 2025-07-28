package uz.dev.rentcar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.dev.rentcar.entity.Notification;
import uz.dev.rentcar.payload.NotificationDTO;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(source = "user.id", target = "userId")
    NotificationDTO toDto(Notification notification);

    List<NotificationDTO> toDtoList(List<Notification> notifications);
}