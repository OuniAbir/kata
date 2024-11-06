package sg.banking.kata.katasgbanking.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.banking.kata.katasgbanking.Dto.TransactionDTO;
import sg.banking.kata.katasgbanking.Enum.TransactionType;
import sg.banking.kata.katasgbanking.Exception.AccountNotFoundException;
import sg.banking.kata.katasgbanking.Exception.TransactionException;
import sg.banking.kata.katasgbanking.Mapper.TransactionMapper;
import sg.banking.kata.katasgbanking.Repository.AccountRepository;
import sg.banking.kata.katasgbanking.Repository.TransactionRepository;
import sg.banking.kata.katasgbanking.entities.Account;
import sg.banking.kata.katasgbanking.entities.Transaction;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;


    @Autowired
    public TransactionServiceImpl(AccountRepository accountRepository,
                                  TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Transactional
    public TransactionDTO deposit(Long accountId, TransactionDTO transactionDTO) throws AccountNotFoundException, TransactionException {
        Account foundAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE));

        if (transactionDTO.getAmount() <= 0) {
            throw new TransactionException(NOT_ALLOWED_AMOUNT);
        }

        // Create a DepositTransaction
        Transaction transaction = transactionMapper.toEntity(transactionDTO);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setAccount(foundAccount);
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTransactionDate(java.time.LocalDateTime.now());

        foundAccount.getTransactions().add(transaction);
        foundAccount.setBalance( foundAccount.getBalance() + transactionDTO.getAmount() );

        // Save the transaction
        transactionRepository.save(transaction);
        accountRepository.save(foundAccount);

        // Convert the entity to DTO and return
        return transactionMapper.toDto(transaction);
    }


}
