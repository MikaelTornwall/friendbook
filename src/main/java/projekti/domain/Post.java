package projekti.domain;

import java.util.*;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor @AllArgsConstructor @Data
public class Post extends AbstractPersistable<Long> {

    @ManyToOne
    private Wall wall;
    
    @ManyToOne
    private Account owner;
    
    private LocalDateTime date;

    private String content;
}
