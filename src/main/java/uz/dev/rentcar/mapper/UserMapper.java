package uz.dev.rentcar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.payload.request.RegisterDTO;

/**
 * Created by: asrorbek
 * DateTime: 7/19/25 13:25
 **/

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "role", defaultValue = "USER")
    @Mapping(target = "password", ignore = true)
    User toEntity(RegisterDTO registerDTO);

}
