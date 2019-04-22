package projekti.repository;

import java.util.*;
import projekti.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
    List<Account> findListByUsername(String username);
    Account findByIdentifier(String identifier);
}
