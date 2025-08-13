package uz.dev.rentcar.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.dev.rentcar.payload.UserStatisticDTO;
import uz.dev.rentcar.service.template.StatisticsService;

/**
 * Created by: asrorbek
 * DateTime: 8/13/25 20:23
 **/

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/statistics")
@Tag(name = "Statistics API", description = "Service Statistics")
@SecurityRequirement(name = "bearerAuth")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public UserStatisticDTO getUserStatistics() {

        return statisticsService.getUsersStatistics();

    }
}
