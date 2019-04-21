package projekti.repository;

import projekti.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
    Account findByIdentifier(String identifier);
}
