package projekti.repository;

import java.util.*;
import projekti.domain.FriendList;
import projekti.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<FriendList, Long> {
    List<Account> findByAccount(Account account);
}
