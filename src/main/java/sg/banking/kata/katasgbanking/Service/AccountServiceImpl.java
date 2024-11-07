package sg.banking.kata.katasgbanking.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.banking.kata.katasgbanking.Dto.AccountHistoryDTO;
import sg.banking.kata.katasgbanking.Dto.TransactionDTO;
import sg.banking.kata.katasgbanking.Exception.AccountNotFoundException;
import sg.banking.kata.katasgbanking.Mapper.TransactionMapper;
import sg.banking.kata.katasgbanking.Repository.AccountRepository;
import sg.banking.kata.katasgbanking.entities.Account;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, TransactionMapper transactionMapper) {
        this.accountRepository = accountRepository;
        this.transactionMapper = transactionMapper;
    }

    public AccountHistoryDTO getAccountHistory(Long accountId) throws AccountNotFoundException {
        Account foundAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(AccountService.ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE));


        List<TransactionDTO> transactionDTOs = foundAccount.getTransactions().stream()
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());

        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        accountHistoryDTO.setAccountId(accountId);
        accountHistoryDTO.setBalance(foundAccount.getBalance());
        accountHistoryDTO.setTransactionDTOs(transactionDTOs);

        return accountHistoryDTO;
    }
}
