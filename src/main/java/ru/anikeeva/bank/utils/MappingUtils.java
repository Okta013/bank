package ru.anikeeva.bank.utils;

import org.springframework.stereotype.Service;
import ru.anikeeva.bank.dto.ClientDTO;
import ru.anikeeva.bank.dto.PaymentDTO;
import ru.anikeeva.bank.entity.Client;
import ru.anikeeva.bank.entity.Payment;

@Service
public class MappingUtils {
    public ClientDTO mapToClientDto(Client client){
        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setPhoneNumber(client.getPhoneNumber());
        dto.setBalance(client.getBalance());
        return dto;
    }

    public Client mapToClientEntity(ClientDTO dto){
        Client client = new Client();
        client.setId(dto.getId());
        client.setName(dto.getName());
        client.setPhoneNumber(dto.getPhoneNumber());
        client.setBalance(dto.getBalance());
        return client;
    }

    public Payment mapToPaymentEntity(PaymentDTO dto){
        Payment payment = new Payment();
        payment.setId(dto.getId());
        payment.setDate(dto.getDate());
        payment.setAmount(dto.getAmount());
        payment.setRecipient(dto.getRecipient());
        payment.setMessage(dto.getMessage());
        return payment;
    }

    public PaymentDTO mapToPaymentDto(Payment payment){
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setDate(payment.getDate());
        dto.setAmount(payment.getAmount());
        dto.setRecipient(payment.getRecipient());
        dto.setMessage(payment.getMessage());
        return dto;
    }
}
