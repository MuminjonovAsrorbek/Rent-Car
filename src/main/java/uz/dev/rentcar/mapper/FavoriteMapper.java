package uz.dev.rentcar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.dev.rentcar.entity.Favorite;
import uz.dev.rentcar.payload.FavoriteDTO;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/19/25 14:38
 **/

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CarMapper.class, UserMapper.class})
public interface FavoriteMapper {

    @Mapping(target = "carId", source = "car.id")
    @Mapping(target = "userId", source = "user.id")
    FavoriteDTO toDTO(Favorite favorite);

    List<FavoriteDTO> toDTO(List<Favorite> favorites);


}
