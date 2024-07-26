package ru.anikeeva.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.anikeeva.bank.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
