package sg.banking.kata.katasgbanking.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import sg.banking.kata.katasgbanking.Enum.TransactionType;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
public class Transaction {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
    private double amount;
    private LocalDateTime transactionDate;
    private double balance;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

}
