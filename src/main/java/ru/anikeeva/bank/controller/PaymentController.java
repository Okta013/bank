package ru.anikeeva.bank.controller;

import org.springframework.web.bind.annotation.*;
import ru.anikeeva.bank.dto.PaymentDTO;
import ru.anikeeva.bank.service.PaymentService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public PaymentDTO createPayment(@RequestBody PaymentDTO paymentDTO,
                                    @RequestParam("senderId") Long senderId,
                                    @RequestParam("recipientId") Long recipientId) {
        return paymentService.createPayment(paymentDTO, senderId, recipientId);
    }

    @PostMapping("/transfer")
    public void transferMoney(@RequestParam Long senderId, @RequestParam Long recipientId, @RequestParam BigDecimal amount) {
        paymentService.transferMoney(senderId, recipientId, amount);
    }

    @GetMapping("/outgoing/{senderId}")
    public List<PaymentDTO> getOutgoingPayments(@PathVariable Long senderId) {
        return paymentService.getOutgoingPayments(senderId);
    }

    @GetMapping("/incoming/{recipientId}")
    public List<PaymentDTO> getIncomingPaymentsByRecipient(@PathVariable Long recipientId) {
        return paymentService.getIncomingPayments(recipientId);
    }
}