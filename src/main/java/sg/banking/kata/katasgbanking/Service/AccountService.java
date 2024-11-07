package sg.banking.kata.katasgbanking.Service;

import sg.banking.kata.katasgbanking.Dto.AccountHistoryDTO;
import sg.banking.kata.katasgbanking.Exception.AccountNotFoundException;

public interface AccountService {
    String ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE = "Account not found";

    AccountHistoryDTO getAccountHistory(Long accountId) throws AccountNotFoundException;
}
