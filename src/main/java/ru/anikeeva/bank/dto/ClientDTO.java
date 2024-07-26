package ru.anikeeva.bank.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClientDTO {
    private Long id;
    private String name;
    private String phoneNumber;
    private BigDecimal balance;
}
