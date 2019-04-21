package projekti.repository;

import java.util.*;
import projekti.domain.Friend;
import projekti.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByOwner(Account account);
}
