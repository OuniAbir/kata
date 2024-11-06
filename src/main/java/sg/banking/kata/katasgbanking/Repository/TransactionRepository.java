package sg.banking.kata.katasgbanking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.banking.kata.katasgbanking.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
