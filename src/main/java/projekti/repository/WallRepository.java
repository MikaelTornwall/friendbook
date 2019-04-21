package projekti.repository;

import java.util.*;
import projekti.domain.Wall;
import projekti.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WallRepository extends JpaRepository<Wall, Long> {
    Wall findByOwner(Account account);
}
