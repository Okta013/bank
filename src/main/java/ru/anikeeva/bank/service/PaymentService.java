package ru.anikeeva.bank.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.anikeeva.bank.entity.Client;
import ru.anikeeva.bank.entity.Payment;
import ru.anikeeva.bank.repository.PaymentRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ClientService clientService;

    public PaymentService(PaymentRepository paymentRepository, ClientService clientService) {
        this.paymentRepository = paymentRepository;
        this.clientService = clientService;
    }

    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public void transferMoney(Long senderId, Long recipientId, BigDecimal amount) {
        Client sender = clientService.getClientById(senderId);
        Client recipient = clientService.getClientById(recipientId);

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds in the sender's account");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        recipient.setBalance(recipient.getBalance().add(amount));

        clientService.updateClient(sender.getId(), sender);
        clientService.updateClient(recipient.getId(), recipient);

        Payment payment = new Payment();
        payment.setDate(LocalDateTime.now());
        payment.setAmount(amount);
        payment.setRecipient(recipient.getName());
        payment.setMessage("Transfer from " + sender.getName());
        createPayment(payment);
    }
}