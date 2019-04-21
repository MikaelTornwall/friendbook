package projekti.repository;

import java.util.*;
import projekti.domain.Wall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WallRepository extends JpaRepository<Wall, Long> {
    
}
