package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.rentcar.entity.UserAddress;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
}