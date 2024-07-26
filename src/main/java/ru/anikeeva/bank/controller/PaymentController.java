package ru.anikeeva.bank.controller;

import org.springframework.web.bind.annotation.*;
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
    public Payment createPayment(@RequestBody Payment payment) {
        return paymentService.createPayment(payment);
    }

    @PostMapping("/transfer")
    public void transferMoney(@RequestParam Long senderId, @RequestParam Long recipientId, @RequestParam BigDecimal amount) {
        paymentService.transferMoney(senderId, recipientId, amount);
    }
}