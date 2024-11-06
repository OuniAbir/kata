package sg.banking.kata.katasgbanking.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sg.banking.kata.katasgbanking.Dto.TransactionDTO;
import sg.banking.kata.katasgbanking.Exception.AccountNotFoundException;
import sg.banking.kata.katasgbanking.Exception.TransactionException;
import sg.banking.kata.katasgbanking.Service.TransactionService;


@RestController
@RequestMapping(TransactionController.BASE_URL)
public class TransactionController {

    private final TransactionService transactionService;
    public static final String BASE_URL = "/api/accounts";


    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
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

}
