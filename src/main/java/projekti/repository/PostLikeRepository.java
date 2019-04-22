package projekti.repository;

import projekti.domain.PostLike;
import projekti.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    PostLike findByLiker(Account owner);
}
