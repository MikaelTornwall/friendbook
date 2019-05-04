package projekti.domain;

import java.util.*;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor @AllArgsConstructor @Data
public class Friend extends AbstractPersistable<Long> {        
    
    @ManyToOne(cascade = CascadeType.ALL)
    private Account owner;
        
    @ManyToOne(cascade = CascadeType.ALL)
    private Account person;
    
    private LocalDateTime date;
    
    private boolean isActive;
    
    @Override
    public boolean equals(Object another) {
        
        final Friend other = (Friend) another;
                        
        if (owner.getUsername().equals(person.getUsername())) {
            return true;
        }
                
        if (person.getUsername().equals(other.getPerson().getUsername())) {
            return true;
        }
        
        return false;
    }                 
}
