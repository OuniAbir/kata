package sg.banking.kata.katasgbanking.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sg.banking.kata.katasgbanking.Dto.AccountHistoryDTO;
import sg.banking.kata.katasgbanking.Dto.TransactionDTO;
import sg.banking.kata.katasgbanking.Enum.TransactionType;
import sg.banking.kata.katasgbanking.Exception.AccountNotFoundException;
import sg.banking.kata.katasgbanking.Mapper.TransactionMapper;
import sg.banking.kata.katasgbanking.Repository.AccountRepository;
import sg.banking.kata.katasgbanking.Service.AccountServiceImpl;
import sg.banking.kata.katasgbanking.entities.Account;
import sg.banking.kata.katasgbanking.entities.Transaction;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAccountHistory_Success() throws AccountNotFoundException {
        // Arrange
        Long accountId = 1L;
        Account account = new Account();
        account.setId(accountId);
        account.setBalance(1000.0);

        Transaction transaction1 = new Transaction();
        transaction1.setAmount(100);
        transaction1.setBalance(1100.0);
        transaction1.setTransactionDate(LocalDateTime.now());
        transaction1.setTransactionType(TransactionType.DEPOSIT);
        transaction1.setAccount(account);

        List<Transaction> transactions = Collections.singletonList(transaction1);
        account.setTransactions(transactions);

        // Mock repository and mapper
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(transactionMapper.toDto(transaction1)).thenReturn(new TransactionDTO(100L, transaction1.getTransactionDate(), 100, 1100, TransactionType.DEPOSIT));

        // Act
        AccountHistoryDTO accountHistoryDTO = accountService.getAccountHistory(accountId);

        // Assert
        assertEquals(accountId, accountHistoryDTO.getAccountId());
        assertEquals(account.getBalance(), accountHistoryDTO.getBalance());
        assertEquals(1, accountHistoryDTO.getTransactionDTOs().size());
        assertEquals(100, accountHistoryDTO.getTransactionDTOs().get(0).getAmount());
    }

    @Test
    void testGetAccountHistory_AccountNotFound() {
        // Arrange
        Long accountId = 1L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AccountNotFoundException.class, () -> accountService.getAccountHistory(accountId));
    }
}
