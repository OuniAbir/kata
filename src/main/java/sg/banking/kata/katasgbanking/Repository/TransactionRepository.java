package sg.banking.kata.katasgbanking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.banking.kata.katasgbanking.entities.Transaction;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findById(Long id);
}
