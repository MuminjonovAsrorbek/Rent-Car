package uz.dev.rentcar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.entity.Booking;
import uz.dev.rentcar.enums.BookingStatusEnum;
import uz.dev.rentcar.payload.PenaltyDTO;
import uz.dev.rentcar.payload.ReturnDeadlineDTO;
import uz.dev.rentcar.repository.BookingRepository;
import uz.dev.rentcar.service.template.NotificationService;
import uz.dev.rentcar.service.template.PenaltyService;
import uz.dev.rentcar.service.template.ScheduledService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/29/25 12:39
 **/

@Service
@RequiredArgsConstructor
public class ScheduledServiceImpl implements ScheduledService {

    private final BookingRepository bookingRepository;

    private final NotificationService notificationService;

    private final PenaltyService penaltyService;

    @Override
    //  @Scheduled(cron = "0 0 0 * * ?") // Every day at midnight
    @Scheduled(cron = "0 * * * * *")
    public void checkReturnDeadlines() {

        LocalDateTime now = LocalDateTime.now();

//        LocalDateTime twoHoursFromNow = now.plusHours(2);

        LocalDateTime twoHoursFromNow = now;

        List<Booking> activeBookings = bookingRepository.findByStatusAndReturnDateBetween(
                BookingStatusEnum.CONFIRMED,
                now.toLocalDate().atStartOfDay(),
                twoHoursFromNow
        );

        for (Booking activeBooking : activeBookings) {

            ReturnDeadlineDTO dto = new ReturnDeadlineDTO();

            dto.setReturnDate(activeBooking.getReturnDate());
            dto.setBookingId(activeBooking.getId());
            dto.setReturnOfficeName(activeBooking.getReturnOffice().getName());
            dto.setUser(activeBooking.getUser());
            dto.setCarBrandAndModel(activeBooking.getCar().getBrand() + " " + activeBooking.getCar().getModel());

            notificationService.checkReturnDeadlines(dto);
        }
    }

    @Override
//    @Scheduled(cron = "0 0 9 * * ?")
    @Scheduled(cron = "0 * * * * *")
    public void checkOverdueReturns() {

        LocalDateTime now = LocalDateTime.now();

        List<Booking> overdueBookings = bookingRepository.findByStatusAndReturnDateBefore(
                BookingStatusEnum.CONFIRMED,
                now
        );

        for (Booking overdueBooking : overdueBookings) {

            Long overdueDays = ChronoUnit.DAYS.between(overdueBooking.getReturnDate().toLocalDate(), now.toLocalDate());

            Long penaltyInTiyin = Math.round(overdueBooking.getCar().getPricePerDay() * overdueDays * 0.25D * 100);

            PenaltyDTO penaltyDTO = new PenaltyDTO();

            penaltyDTO.setBookingId(overdueBooking.getId());
            penaltyDTO.setOverdueDays(overdueDays);
            penaltyDTO.setPenaltyAmount(penaltyInTiyin);
            penaltyDTO.setPenaltyDate(Timestamp.valueOf(now));

            penaltyService.checkOverdueReturns(penaltyDTO);

        }

    }

}
