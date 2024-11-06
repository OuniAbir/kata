package sg.banking.kata.katasgbanking.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sg.banking.kata.katasgbanking.Enum.TransactionType;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionDTO {
    private Long id;
    private Date transactionDate;
    private double amount;
    private double balance;
    private TransactionType transactionType;
}