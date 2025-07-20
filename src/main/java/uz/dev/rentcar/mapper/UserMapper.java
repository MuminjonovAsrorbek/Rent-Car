package uz.dev.rentcar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.payload.UserDTO;
import uz.dev.rentcar.payload.request.RegisterDTO;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/19/25 13:25
 **/

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {BookingMapper.class, FavoriteMapper.class, NotificationMapper.class, UserAddressMapper.class, ReviewMapper.class}
)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
//    @Mapping(target = "role", defaultValue = "USER")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "favorites", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    User toEntity(RegisterDTO registerDTO);

    UserDTO toDTO(User user);

    List<UserDTO> toDTO(List<User> users);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "favorites", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    User toEntity(UserDTO userDTO);

}
