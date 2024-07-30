package ru.anikeeva.bank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="phone")
    private String phoneNumber;

    @Column(name="balance")
    private BigDecimal balance;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> sentPayments = new ArrayList<>();

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> receivedPayments = new ArrayList<>();

    public Client(Long id, String name, String phoneNumber, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
    }

    public Client(String name, String phoneNumber, BigDecimal balance) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
    }
}