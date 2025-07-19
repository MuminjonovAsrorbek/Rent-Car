package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.rentcar.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}