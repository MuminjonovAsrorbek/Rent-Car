package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.rentcar.entity.PromoCode;

public interface PromoCodeRepository extends JpaRepository<PromoCode, Long> {
}