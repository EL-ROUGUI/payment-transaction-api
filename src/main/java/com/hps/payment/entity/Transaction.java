package com.hps.payment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal amount;

    @NotBlank
    @Column(nullable = false)
    private String currency;

    @NotBlank
    @Column(nullable = false)
    private String merchant;

    private LocalDateTime timestamp;

    private String status;

    // Constructeur vide requis par JPA
    public Transaction() {
    }

    // Constructeur avec les champs métier
    public Transaction(BigDecimal amount, String currency, String merchant, LocalDateTime timestamp, String status) {
        this.amount = amount;
        this.currency = currency;
        this.merchant = merchant;
        this.timestamp = timestamp;
        this.status = status;
    }

    // Constructeur complet avec identifiant
    public Transaction(Long id, BigDecimal amount, String currency, String merchant, LocalDateTime timestamp, String status) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.merchant = merchant;
        this.timestamp = timestamp;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
