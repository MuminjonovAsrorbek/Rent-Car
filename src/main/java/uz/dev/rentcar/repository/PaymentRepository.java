package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.rentcar.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}