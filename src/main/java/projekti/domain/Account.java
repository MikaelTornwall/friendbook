package projekti.domain;

import java.util.*;
import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor @AllArgsConstructor @Data
public class Account extends AbstractPersistable<Long> {
    
    private String username;
    private String name;
    private String password; 
    private String identifier;       
        
    @OneToOne(cascade = CascadeType.ALL)    
    private FriendList friends;
}
