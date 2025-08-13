package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.exceptions.EntityNotFoundException;
import uz.dev.rentcar.projection.UserStatistic;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    default User findByIdOrThrowException(Long id) {

        return findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with ID : " + id, HttpStatus.NOT_FOUND));

    }

    Optional<User> findByPhoneNumber(String phoneNumber);

    default User findByPhoneNumberOrThrowException(String phoneNumber) {

        return findByPhoneNumber(phoneNumber).orElseThrow(() -> new EntityNotFoundException("User not found with phoneNumber : " + phoneNumber, HttpStatus.NOT_FOUND));

    }

    @Query("""
                SELECT\s
                    COUNT(u) AS totalUsers,
                    SUM(CASE WHEN u.deleted = true THEN 1 ELSE 0 END) AS deletedUsers,
                    SUM(CASE WHEN u.role = 'ADMIN' THEN 1 ELSE 0 END) AS admins,
                    SUM(CASE WHEN u.role = 'USER' THEN 1 ELSE 0 END) AS users
                FROM User u
           \s""")
    UserStatistic getUserStatistics();
}