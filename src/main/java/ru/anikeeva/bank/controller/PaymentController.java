package ru.anikeeva.bank.controller;

import org.springframework.web.bind.annotation.*;
import ru.anikeeva.bank.dto.PaymentDTO;
import ru.anikeeva.bank.entity.Payment;
import ru.anikeeva.bank.service.PaymentService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public PaymentDTO createPayment(@RequestBody PaymentDTO paymentDTO) {
        return paymentService.createPayment(paymentDTO);
    }

    @PostMapping("/transfer")
    public void transferMoney(@RequestParam Long senderId, @RequestParam Long recipientId, @RequestParam BigDecimal amount) {
        paymentService.transferMoney(senderId, recipientId, amount);
    }
}