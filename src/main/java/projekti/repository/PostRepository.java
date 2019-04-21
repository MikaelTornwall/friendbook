package projekti.repository;

import java.util.*;
import projekti.domain.Post;
import projekti.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByOwner(Account account);
}
