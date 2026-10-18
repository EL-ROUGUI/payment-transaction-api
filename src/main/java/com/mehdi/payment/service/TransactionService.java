package com.mehdi.payment.service;

import com.mehdi.payment.entity.Transaction;
import com.mehdi.payment.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(Transaction t) {
        if (t.getAmount() == null || t.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif");
        }

        if (t.getTimestamp() == null) {
            t.setTimestamp(LocalDateTime.now());
        }

        if (t.getStatus() == null || t.getStatus().isBlank()) {
            t.setStatus("PENDING");
        }

        return transactionRepository.save(t);
    }

    @Transactional(readOnly = true)
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Transaction introuvable : " + id));
    }

    @Transactional(readOnly = true)
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction updateStatus(Long id, String status) {
        Transaction transaction = getTransactionById(id);
        transaction.setStatus(status);
        return transactionRepository.save(transaction);
    }
}
