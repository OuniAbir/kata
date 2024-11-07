package sg.banking.kata.katasgbanking.Service;

import sg.banking.kata.katasgbanking.Dto.TransactionDTO;
import sg.banking.kata.katasgbanking.Exception.AccountNotFoundException;
import sg.banking.kata.katasgbanking.Exception.TransactionException;

public interface TransactionService {
    String NOT_ALLOWED_AMOUNT = "Amount must be greater than zero";
    String INSUFFICIENT_BALANCE   = "Insufficient balance";

    TransactionDTO deposit(Long accountId, TransactionDTO transactionDTO ) throws AccountNotFoundException, TransactionException;
    TransactionDTO withdrawal(Long accountId, TransactionDTO transactionDTO) throws AccountNotFoundException, TransactionException;

}
