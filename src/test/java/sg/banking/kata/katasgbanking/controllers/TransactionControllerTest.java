package sg.banking.kata.katasgbanking.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import sg.banking.kata.katasgbanking.Dto.TransactionDTO;
import sg.banking.kata.katasgbanking.Exception.AccountNotFoundException;
import sg.banking.kata.katasgbanking.Service.TransactionService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    void shouldDepositSuccessfully() throws Exception {
        Long accountId = 1L;
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(50.0);

        TransactionDTO resultDTO = new TransactionDTO();
        resultDTO.setAmount(50.0);

        when(transactionService.deposit(eq(accountId), any(TransactionDTO.class)))
                .thenReturn(resultDTO);

        mockMvc.perform(post("/api/accounts/{accountId}/deposit", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(transactionDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(50.0));
    }

    @Test
    void shouldReturnNotFoundWhenAccountDoesNotExist() throws Exception {
        Long accountId = 99L;  // Non-existent account ID
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(50.0);

        when(transactionService.deposit(eq(accountId), any(TransactionDTO.class)))
                .thenThrow(new AccountNotFoundException(TransactionService.ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE));
        mockMvc.perform(post("/api/accounts/{accountId}/deposit", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(transactionDTO)))
                .andExpect(status().isNotFound())  // Expecting a 404 Not Found response
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains(TransactionService.ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE)));
    }

}