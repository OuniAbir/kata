package sg.banking.kata.katasgbanking.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import sg.banking.kata.katasgbanking.Dto.TransactionDTO;
import sg.banking.kata.katasgbanking.Repository.AccountRepository;
import sg.banking.kata.katasgbanking.entities.Account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class AccountIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void setupData() {
        // Create and save an Account in the H2 database before each test
        Account account = new Account();
        account.setId(1L);
        account.setBalance(900.0);  // Initial balance
        accountRepository.save(account);
    }

    @Test
    void testDeposit() throws Exception {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(200.0);

        mockMvc.perform(post("/api/accounts/{accountId}/deposit", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 200.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(200.0))
                .andExpect(jsonPath("$.balance").value(1100.0));


        Account updatedAccount = accountRepository.findById(1L).orElseThrow();
        assertEquals(1100.0, updatedAccount.getBalance());
    }

    @Test
    void testWithdraw() throws Exception {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(200.0);

        mockMvc.perform(post("/api/accounts/{accountId}/withdrawal", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 200.0}"))
                .andExpect(status().isOk())  // Verify that the status is OK
                .andExpect(jsonPath("$.amount").value(200.0))
                .andExpect(jsonPath("$.balance").value(700.0));

        Account updatedAccount = accountRepository.findById(1L).orElseThrow();
        assertEquals(700.0, updatedAccount.getBalance());
    }
}