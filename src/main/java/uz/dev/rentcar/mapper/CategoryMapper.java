package uz.dev.rentcar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.dev.rentcar.entity.Category;
import uz.dev.rentcar.payload.CategoryDTO;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING , uses = {CarMapper.class})
public interface CategoryMapper {

    CategoryDTO toDTO(Category category);

    List<CategoryDTO> toDTO(List<Category> categories);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "cars", ignore = true)
    Category toEntity(CategoryDTO categoryDTO);
}