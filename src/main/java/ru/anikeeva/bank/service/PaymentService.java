package ru.anikeeva.bank.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.anikeeva.bank.dto.ClientDTO;
import ru.anikeeva.bank.dto.PaymentDTO;
import ru.anikeeva.bank.entity.Client;
import ru.anikeeva.bank.entity.Payment;
import ru.anikeeva.bank.repository.PaymentRepository;
import ru.anikeeva.bank.utils.MappingUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ClientService clientService;
    private final MappingUtils mappingUtils;

    public PaymentService(PaymentRepository paymentRepository, ClientService clientService, MappingUtils mappingUtils) {
        this.paymentRepository = paymentRepository;
        this.clientService = clientService;
        this.mappingUtils = mappingUtils;
    }

    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        Payment payment = mappingUtils.mapToPaymentEntity(paymentDTO);
        payment = paymentRepository.save(payment);
        return mappingUtils.mapToPaymentDto(payment);
    }

    public void transferMoney(Long senderId, Long recipientId, BigDecimal amount) {
        ClientDTO sender = clientService.getClientById(senderId);
        ClientDTO recipient = clientService.getClientById(recipientId);

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds in the sender's account");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        recipient.setBalance(recipient.getBalance().add(amount));

        mappingUtils.mapToClientEntity(clientService.updateClient(sender.getId(), sender));
        mappingUtils.mapToClientEntity(clientService.updateClient(recipient.getId(), recipient));

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setDate(LocalDateTime.now());
        paymentDTO.setAmount(amount);
        paymentDTO.setRecipient(recipient.getName());
        paymentDTO.setMessage("Transfer from " + sender.getName());
        mappingUtils.mapToPaymentEntity(createPayment(paymentDTO));
    }
}