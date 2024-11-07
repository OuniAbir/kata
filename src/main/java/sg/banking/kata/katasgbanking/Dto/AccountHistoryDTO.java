package sg.banking.kata.katasgbanking.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountHistoryDTO {
    private Long accountId;
    private double balance;
    private List<TransactionDTO> transactionDTOs;
}
