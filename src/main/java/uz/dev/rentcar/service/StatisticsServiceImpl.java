package uz.dev.rentcar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.payload.UserStatisticDTO;
import uz.dev.rentcar.projection.UserStatistic;
import uz.dev.rentcar.repository.UserRepository;
import uz.dev.rentcar.service.template.StatisticsService;

/**
 * Created by: asrorbek
 * DateTime: 8/13/25 20:25
 **/

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final UserRepository userRepository;

    @Override
    public UserStatisticDTO getUsersStatistics() {

        UserStatistic userStatistics = userRepository.getUserStatistics();

        return new UserStatisticDTO(
                userStatistics.getTotalUsers(),
                userStatistics.getDeletedUsers(),
                userStatistics.getAdmins(),
                userStatistics.getUsers(),
                userStatistics.getLastMonthUsers(),
                userStatistics.getLastWeekUsers(),
                userStatistics.getTodayUsers()
        );

    }
}
