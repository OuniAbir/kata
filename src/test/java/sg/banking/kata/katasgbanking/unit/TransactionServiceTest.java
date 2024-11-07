package sg.banking.kata.katasgbanking.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sg.banking.kata.katasgbanking.Dto.TransactionDTO;
import sg.banking.kata.katasgbanking.Exception.AccountNotFoundException;
import sg.banking.kata.katasgbanking.Exception.TransactionException;
import sg.banking.kata.katasgbanking.Mapper.TransactionMapper;
import sg.banking.kata.katasgbanking.Repository.AccountRepository;
import sg.banking.kata.katasgbanking.Repository.TransactionRepository;
import sg.banking.kata.katasgbanking.Service.TransactionServiceImpl;
import sg.banking.kata.katasgbanking.entities.Account;
import sg.banking.kata.katasgbanking.entities.Transaction;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    private Account account;
    private TransactionDTO transactionDTO;
    private Transaction transaction;


    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        account = new Account();
        transactionDTO = new TransactionDTO();
        transaction = new Transaction();

        account.setId(1L);
        account.setBalance(100.0);
        transactionDTO.setAmount(50.0);
        transaction.setAmount(50.0);
    }

    @Test
    void shouldDepositSuccessfully() throws TransactionException, AccountNotFoundException {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        when(transactionMapper.toEntity(transactionDTO)).thenReturn(transaction);
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        when(transactionMapper.toDto(transaction)).thenReturn(transactionDTO);

        TransactionDTO result = transactionService.deposit(1L, transactionDTO);

        assertEquals(150.0, account.getBalance());
        assertNotNull(result);
    }

    @Test
    void shouldThrowExceptionWhenAccountNotFound() {
        // Given: Account ID that does not exist in the repository
        when(accountRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then: Attempting to deposit or withdraw should throw AccountNotFoundException
        assertThrows(AccountNotFoundException.class, () ->
                transactionService.deposit(99L, transactionDTO)
        );
    }

    @Test
    void shouldThrowExceptionForInvalidDepositAmount() {

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        // Test for zero amount
        transactionDTO.setAmount(0);
        assertThrows(TransactionException.class, () ->
                transactionService.deposit(1L, transactionDTO)
        );

        // Test for negative amount
        transactionDTO.setAmount(-1);
        assertThrows(TransactionException.class, () ->
                transactionService.deposit(1L, transactionDTO)
        );

        // Verify that accountRepository.save() is also never called
        verify(accountRepository, never()).save(account);

        // Ensure the account balance remains unchanged
        assertEquals(100.0, account.getBalance());
    }

    @Test
    void shouldWithdrawSuccessfully() throws TransactionException, AccountNotFoundException {

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        when(transactionMapper.toEntity(transactionDTO)).thenReturn(transaction);
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        when(transactionMapper.toDto(transaction)).thenReturn(transactionDTO);

        TransactionDTO result = transactionService.withdrawal(1L, transactionDTO);

        assertEquals(50.0, account.getBalance());
        assertNotNull(result);
    }


    @Test
    void shouldThrowInsufficientFundsExceptionWhenBalanceIsLow() {
        account.setBalance(30.0); // Setting the balance to 30, which is less than the withdrawal amount
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        assertThrows(TransactionException.class, () -> transactionService.withdrawal(1L, transactionDTO));

        // Verify that the mapper was never called in this case (since withdrawal failed)
        verify(transactionMapper, never()).toEntity(transactionDTO);
    }

}
