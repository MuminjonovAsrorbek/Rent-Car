package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.rentcar.entity.Office;

public interface OfficeRepository extends JpaRepository<Office, Long> {
}