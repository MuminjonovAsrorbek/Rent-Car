package uz.dev.rentcar.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: asrorbek
 * DateTime: 8/13/25 20:26
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserStatisticDTO {

    private Long totalUsers;

    private Long deletedUsers;

    private Long admins;

    private Long users;

    private Long lastMonthUsers;

    private Long lastWeekUsers;

    private Long todayUsers;

}
