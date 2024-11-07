package sg.banking.kata.katasgbanking.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sg.banking.kata.katasgbanking.Dto.AccountHistoryDTO;
import sg.banking.kata.katasgbanking.Dto.TransactionDTO;
import sg.banking.kata.katasgbanking.Exception.AccountNotFoundException;
import sg.banking.kata.katasgbanking.Exception.TransactionException;
import sg.banking.kata.katasgbanking.Service.AccountService;
import sg.banking.kata.katasgbanking.Service.TransactionService;


@RestController
@RequestMapping(TransactionController.BASE_URL)
public class TransactionController {

    public static final String BASE_URL = "/api/accounts";
    private final TransactionService transactionService;
    private final AccountService accountService;


    @Autowired
    public TransactionController(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<TransactionDTO> deposit(@PathVariable Long accountId, @RequestBody TransactionDTO transactionDTO) {
        try {
            TransactionDTO result = transactionService.deposit(accountId, transactionDTO);
            return ResponseEntity.ok(result);
        } catch (AccountNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (TransactionException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping("/{accountId}/withdrawal")
    public ResponseEntity<TransactionDTO> withdrawal(@PathVariable Long accountId, @RequestBody TransactionDTO transactionDTO) {
        try {
            TransactionDTO result = transactionService.withdrawal(accountId, transactionDTO);
            return ResponseEntity.ok(result);
        } catch (AccountNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (TransactionException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/{accountId}/operations")
    public ResponseEntity<AccountHistoryDTO> getAccountHistory(@PathVariable Long accountId) {
        try {
            AccountHistoryDTO result = accountService.getAccountHistory(accountId);
            return ResponseEntity.ok(result);
        } catch (AccountNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
