package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import uz.dev.rentcar.entity.PromoCode;
import uz.dev.rentcar.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface PromoCodeRepository extends JpaRepository<PromoCode, Long> {

    default PromoCode getByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PromoCode not found with id : " + id, HttpStatus.NOT_FOUND));
    }

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, Long id);

    Optional<PromoCode> findByCode(String code);

    default PromoCode findByCodeOrThrow(String code) {
        return findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("PromoCode not found with name : " + code, HttpStatus.NOT_FOUND));
    }
}