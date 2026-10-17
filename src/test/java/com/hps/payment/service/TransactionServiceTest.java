package com.hps.payment.service;

import com.hps.payment.entity.Transaction;
import com.hps.payment.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void testCreateTransaction_Valid_ShouldSaveWithPendingAndTimestamp() {
        Transaction transaction = new Transaction();
        transaction.setAmount(new BigDecimal("100.00"));
        transaction.setCurrency("MAD");
        transaction.setMerchant("Amazon");

        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transaction created = transactionService.createTransaction(transaction);

        assertNotNull(created);
        assertEquals("PENDING", created.getStatus());
        assertNotNull(created.getTimestamp());
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    void testCreateTransaction_NegativeAmount_ShouldThrowIllegalArgumentException() {
        Transaction transaction = new Transaction();
        transaction.setAmount(new BigDecimal("-50.00"));
        transaction.setCurrency("MAD");
        transaction.setMerchant("Amazon");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.createTransaction(transaction);
        });

        assertEquals("Le montant doit être positif", exception.getMessage());
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void testGetTransactionById_NonExistent_ShouldThrowNoSuchElementException() {
        Long nonExistentId = 99999L;
        when(transactionRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            transactionService.getTransactionById(nonExistentId);
        });

        assertEquals("Transaction introuvable : " + nonExistentId, exception.getMessage());
        verify(transactionRepository, times(1)).findById(nonExistentId);
    }
}
