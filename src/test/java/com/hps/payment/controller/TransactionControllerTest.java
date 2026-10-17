package com.hps.payment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hps.payment.entity.Transaction;
import com.hps.payment.exception.GlobalExceptionHandler;
import com.hps.payment.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
@Import(GlobalExceptionHandler.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    @Test
    void testCreateTransaction_ValidJson_Returns201Created() throws Exception {
        Transaction input = new Transaction(new BigDecimal("150.00"), "MAD", "Store", null, null);
        Transaction saved = new Transaction(1L, new BigDecimal("150.00"), "MAD", "Store", LocalDateTime.now(), "PENDING");

        when(transactionService.createTransaction(any(Transaction.class))).thenReturn(saved);

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.currency").value("MAD"));
    }

    @Test
    void testCreateTransaction_NegativeAmount_Returns400BadRequest() throws Exception {
        String invalidJson = """
                {
                    "amount": -50.00,
                    "currency": "MAD",
                    "merchant": "Store"
                }
                """;

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void testGetTransactionById_NonExistent_Returns404NotFound() throws Exception {
        Long nonExistentId = 99999L;
        when(transactionService.getTransactionById(nonExistentId))
                .thenThrow(new NoSuchElementException("Transaction introuvable : " + nonExistentId));

        mockMvc.perform(get("/api/transactions/{id}", nonExistentId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }
}
