package uz.dev.rentcar.projection;

public interface UserStatistic {

    Long getTotalUsers();

    Long getDeletedUsers();

    Long getAdmins();

    Long getUsers();

    Long getLastMonthUsers();

    Long getLastWeekUsers();

    Long getTodayUsers();
}