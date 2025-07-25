package uz.dev.rentcar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.dev.rentcar.entity.Payment;
import uz.dev.rentcar.payload.PaymentDTO;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/26/25 00:25
 **/

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {

    @Mapping(target = "bookingId", source = "booking.id")
    PaymentDTO toDTO(Payment payment);

    List<PaymentDTO> toDTO(List<Payment> payments);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "booking", ignore = true)
    @Mapping(target = "status", ignore = true)
    Payment toEntity(PaymentDTO paymentDTO);
}
