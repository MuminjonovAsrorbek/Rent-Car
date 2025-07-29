package uz.dev.rentcar.service.template;

import uz.dev.rentcar.payload.PenaltyDTO;

/**
 * Created by: asrorbek
 * DateTime: 7/29/25 13:37
 **/

public interface PenaltyService {

    void checkOverdueReturns(PenaltyDTO penaltyDTO);

}
