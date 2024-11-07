User Stories

**US 1:

In order to save money
As a bank client
I want to make a deposit in my account

@PostMapping("/accounts/{accountId}/deposit")
public ResponseEntity<transactionDTO> deposit(@PathVariable String accountId, @RequestBody transactionDTO transactionDTO)


**US 2:

In order to retrieve some or all of my savings
As a bank client
I want to make a withdrawal from my account

@PostMapping("/accounts/{accountId}/withdrawal")
public ResponseEntity<transactionDTO> withdrawal(@PathVariable String accountId, @RequestBody transactionDTO transactionDTO)

**US 3:

In order to check my operations
As a bank client
I want to see the history (operation, date, amount, balance) of my operations

@GetMapping("/accounts/{accountId}/operations")
public AccountHistoryDTO getAccountHistory(@PathVariable String accountId

