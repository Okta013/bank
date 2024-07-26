package ru.anikeeva.bank.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentDTO {
    private Long id;
    private LocalDateTime date;
    private BigDecimal amount;
    private String message;
    private Long senderId;
    private Long recipientId;
}
