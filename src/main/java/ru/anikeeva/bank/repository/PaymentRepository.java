package ru.anikeeva.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.anikeeva.bank.entity.Client;
import ru.anikeeva.bank.entity.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllBySender(Client sender);
    List<Payment> findAllByRecipient(Client recipient);
}
