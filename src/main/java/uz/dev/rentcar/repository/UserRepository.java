package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.exceptions.EntityNotFoundException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    default User findByIdOrThrowException(Long id) {

        return findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with ID : " + id, HttpStatus.NOT_FOUND));

    }
}