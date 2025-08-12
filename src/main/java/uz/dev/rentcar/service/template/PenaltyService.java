package uz.dev.rentcar.service.template;

import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.payload.PenaltyDTO;
import uz.dev.rentcar.payload.response.PageableDTO;

/**
 * Created by: asrorbek
 * DateTime: 7/29/25 13:37
 **/

public interface PenaltyService {

    void checkOverdueReturns(PenaltyDTO penaltyDTO);

    PageableDTO getMyPenalties(User currentUser, int page, int size);

    PenaltyDTO confirmPenalty(Long bookingId);

    PenaltyDTO confirmPenaltyWithPenaltyId(Long penaltyId);

    PenaltyDTO cancelPenaltyWithBookingId(Long bookingId);

    PenaltyDTO cancelPenaltyWithPenaltyId(Long penaltyId);

    PageableDTO getMyOverdueReturns(User currentUser, int page, int size);
}
