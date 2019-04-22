package projekti.domain;

import javax.persistence.Entity;
import javax.persistence.Lob;
import org.springframework.data.jpa.domain.AbstractPersistable;
import java.util.*;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @AllArgsConstructor @Data
public class PostLike extends AbstractPersistable<Long> {
    
    @OneToOne//(cascade = CascadeType.ALL)
    private Account liker;        
}
