package sg.banking.kata.katasgbanking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.banking.kata.katasgbanking.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
