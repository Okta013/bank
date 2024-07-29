package ru.anikeeva.bank.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    private Long id;

    @NotBlank(message = "Name shouldn't be empty")
    @Size(min = 2, message = "Name should be longer than 2 symbols")
    private String name;

    @NotBlank(message = "Phone number shouldn't be empty")
    private String phoneNumber;

    private BigDecimal balance;
}
