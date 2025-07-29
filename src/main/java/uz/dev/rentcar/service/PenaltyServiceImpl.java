package uz.dev.rentcar.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.entity.Booking;
import uz.dev.rentcar.entity.Penalty;
import uz.dev.rentcar.payload.PenaltyDTO;
import uz.dev.rentcar.repository.BookingRepository;
import uz.dev.rentcar.repository.PenaltyRepository;
import uz.dev.rentcar.service.template.PenaltyService;

import java.util.Optional;

/**
 * Created by: asrorbek
 * DateTime: 7/29/25 13:38
 **/

@Service
@RequiredArgsConstructor
public class PenaltyServiceImpl implements PenaltyService {

    private final PenaltyRepository penaltyRepository;

    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public void checkOverdueReturns(PenaltyDTO penaltyDTO) {

        Optional<Penalty> optionalPenalty = penaltyRepository.findByBookingId(penaltyDTO.getBookingId());

        if (optionalPenalty.isPresent()) {

            Penalty penalty = optionalPenalty.get();

            penalty.setPenaltyAmount(penaltyDTO.getPenaltyAmount());
            penalty.setOverdueDays(penaltyDTO.getOverdueDays());

            penaltyRepository.save(penalty);
        } else {

            Booking booking = bookingRepository.getByIdOrThrow(penaltyDTO.getBookingId());

            Penalty penalty = new Penalty();
            penalty.setBooking(booking);
            penalty.setPenaltyAmount(penaltyDTO.getPenaltyAmount());
            penalty.setOverdueDays(penaltyDTO.getOverdueDays());

            penaltyRepository.save(penalty);

        }

    }
}
