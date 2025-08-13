package uz.dev.rentcar.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.entity.Booking;
import uz.dev.rentcar.entity.Penalty;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.enums.PenaltyStatusEnum;
import uz.dev.rentcar.mapper.PenaltyMapper;
import uz.dev.rentcar.payload.PenaltyDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.repository.BookingRepository;
import uz.dev.rentcar.repository.PenaltyRepository;
import uz.dev.rentcar.service.template.PenaltyService;

import java.util.List;
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

    private final PenaltyMapper penaltyMapper;

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
            penalty.setStatus(PenaltyStatusEnum.PENDING);

            penaltyRepository.save(penalty);

        }

    }

    @Override
    public PageableDTO getMyPenalties(User currentUser, int page, int size) {

        Sort sort = Sort.by(Penalty.Fields.id).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Penalty> penaltyPage = penaltyRepository.findByBookingUserId(currentUser.getId(), pageable);

        List<Penalty> penalties = penaltyPage.getContent();

        return new PageableDTO(
                penaltyPage.getSize(),
                penaltyPage.getTotalElements(),
                penaltyPage.getTotalPages(),
                penaltyPage.hasNext(),
                penaltyPage.hasPrevious(),
                penaltyMapper.toDTO(penalties)
        );

    }

    @Override
    @Transactional
    public PenaltyDTO confirmPenalty(Long bookingId) {

        Booking booking = bookingRepository.getByIdOrThrow(bookingId);

        Penalty penalty = penaltyRepository.findByBookingIdOrThrowException(booking.getId());

        penalty.setStatus(PenaltyStatusEnum.COMPLETED);

        Penalty save = penaltyRepository.save(penalty);

        return penaltyMapper.toDTO(save);
    }

    @Override
    @Transactional
    public PenaltyDTO confirmPenaltyWithPenaltyId(Long penaltyId) {

        Penalty penalty = penaltyRepository.findByIdOrThrowException(penaltyId);

        penalty.setStatus(PenaltyStatusEnum.COMPLETED);

        Penalty save = penaltyRepository.save(penalty);

        return penaltyMapper.toDTO(save);

    }

    @Override
    @Transactional
    public PenaltyDTO cancelPenaltyWithBookingId(Long bookingId) {

        Booking booking = bookingRepository.getByIdOrThrow(bookingId);

        Penalty penalty = penaltyRepository.findByBookingIdOrThrowException(booking.getId());

        penalty.setStatus(PenaltyStatusEnum.CANCELED);

        Penalty save = penaltyRepository.save(penalty);

        return penaltyMapper.toDTO(save);

    }

    @Override
    @Transactional
    public PenaltyDTO cancelPenaltyWithPenaltyId(Long penaltyId) {

        Penalty penalty = penaltyRepository.findByIdOrThrowException(penaltyId);

        penalty.setStatus(PenaltyStatusEnum.CANCELED);

        Penalty save = penaltyRepository.save(penalty);

        return penaltyMapper.toDTO(save);

    }

    @Override
    public PageableDTO getMyOverdueReturns(User currentUser, int page, int size) {

        Sort sort = Sort.by(Penalty.Fields.id).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Penalty> penaltyPage = penaltyRepository.findByBookingUserIdAndStatus(currentUser.getId(), PenaltyStatusEnum.PENDING, pageable);

        List<Penalty> penalties = penaltyPage.getContent();

        return new PageableDTO(
                penaltyPage.getSize(),
                penaltyPage.getTotalElements(),
                penaltyPage.getTotalPages(),
                penaltyPage.hasNext(),
                penaltyPage.hasPrevious(),
                penaltyMapper.toDTO(penalties)
        );

    }
}