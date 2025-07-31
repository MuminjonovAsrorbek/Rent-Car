package uz.dev.rentcar.service.template;

import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.payload.PenaltyDTO;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/29/25 13:37
 **/

public interface PenaltyService {

    void checkOverdueReturns(PenaltyDTO penaltyDTO);

    List<PenaltyDTO> getMyPenalties(User currentUser);

    PenaltyDTO confirmPenalty(Long bookingId);

    PenaltyDTO confirmPenaltyWithPenaltyId(Long penaltyId);

    PenaltyDTO cancelPenaltyWithBookingId(Long bookingId);

    PenaltyDTO cancelPenaltyWithPenaltyId(Long penaltyId);

    List<PenaltyDTO> getMyOverdueReturns(User currentUser);
}
