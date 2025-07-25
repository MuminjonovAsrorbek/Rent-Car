package uz.dev.rentcar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import uz.dev.rentcar.entity.Office;
import uz.dev.rentcar.entity.PromoCode;
import uz.dev.rentcar.payload.OfficeDTO;
import uz.dev.rentcar.payload.PromoCodeDTO;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/19/25 13:25
 **/

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {BookingMapper.class}
)
public interface PromoCodeMapper {

    PromoCodeDTO toDTO(PromoCode promoCode);

    List<PromoCodeDTO> toDTO(List<PromoCode> promoCodes);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    PromoCode toEntity(PromoCodeDTO promoCodeDTO);

}
