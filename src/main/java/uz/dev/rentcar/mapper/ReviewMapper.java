package uz.dev.rentcar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.dev.rentcar.entity.Review;
import uz.dev.rentcar.payload.ReviewDTO;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CarMapper.class, UserMapper.class})
public interface ReviewMapper {

    @Mapping(target = "carId", source = "car.id")
    @Mapping(target = "userId", source = "user.id")
    ReviewDTO toDTO(Review review);

    List<ReviewDTO> toDTO(List<Review> reviews);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "car.id", source = "carId")
    @Mapping(target = "user.id", source = "userId")
    Review toEntity(ReviewDTO reviewDTO);

}
