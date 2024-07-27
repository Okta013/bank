package ru.anikeeva.bank.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.anikeeva.bank.dto.ClientDTO;
import ru.anikeeva.bank.dto.PaymentDTO;
import ru.anikeeva.bank.entity.Client;
import ru.anikeeva.bank.entity.Payment;
import ru.anikeeva.bank.repository.ClientRepository;
import ru.anikeeva.bank.repository.PaymentRepository;
import ru.anikeeva.bank.utils.MappingUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ClientService clientService;
    private final MappingUtils mappingUtils;
    private final ClientRepository clientRepository;

    public PaymentService(PaymentRepository paymentRepository, ClientService clientService, MappingUtils mappingUtils, ClientRepository clientRepository) {
        this.paymentRepository = paymentRepository;
        this.clientService = clientService;
        this.mappingUtils = mappingUtils;
        this.clientRepository = clientRepository;
    }

    public PaymentDTO createPayment(PaymentDTO paymentDTO, Long senderId, Long recipientId) {
        Client sender = clientRepository.findById(senderId).orElseThrow(() -> new RuntimeException("Sender not found"));
        Client recipient = clientRepository.findById(recipientId).orElseThrow(() -> new RuntimeException("Recipient not found"));
        Payment payment = mappingUtils.mapToPaymentEntity(paymentDTO);
        payment.setSender(sender);
        payment.setRecipient(recipient);
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
        paymentDTO.setRecipientId(recipient.getId());
        paymentDTO.setSenderId(sender.getId());
        paymentDTO.setMessage("Transfer from " + sender.getName());
        mappingUtils.mapToPaymentEntity(createPayment(paymentDTO, senderId, recipientId));
    }

    public List<PaymentDTO> getOutgoingPayments(Long senderId) {
        Client sender = clientRepository.findById(senderId).orElseThrow(() -> new RuntimeException("Client not found"));
        List<Payment> outgoingPayments = paymentRepository.findAllBySender(sender);
        return outgoingPayments.stream().map(mappingUtils::mapToPaymentDto).collect(Collectors.toList());
    }

    public List<PaymentDTO> getIncomingPayments(Long recipientId) {
        Client recipient = clientRepository.findById(recipientId).orElseThrow(() -> new RuntimeException("Client not found"));
        List<Payment> incomingPayments = paymentRepository.findAllByRecipient(recipient);
        return incomingPayments.stream().map(mappingUtils::mapToPaymentDto).collect(Collectors.toList());
    }
}