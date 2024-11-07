package sg.banking.kata.katasgbanking.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sg.banking.kata.katasgbanking.Enum.TransactionType;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionDTO {
    private Long id;
    private LocalDateTime transactionDate;
    private double amount;
    private double balance;
    private TransactionType transactionType;
}