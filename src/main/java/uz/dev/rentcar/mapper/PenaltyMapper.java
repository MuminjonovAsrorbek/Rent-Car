package uz.dev.rentcar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.dev.rentcar.entity.Penalty;
import uz.dev.rentcar.payload.PenaltyDTO;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/29/25 14:04
 **/

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface PenaltyMapper {

    @Mapping(target = "bookingId", source = "booking.id")
    PenaltyDTO toDTO(Penalty penalty);

    List<PenaltyDTO> toDTO(List<Penalty> penalties);
}
