package projekti.domain;

import java.util.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
public class FriendList extends AbstractPersistable<Long> {

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "friends")
    private Account account;
            
    @OneToMany
    private List<Account> friendSet = new ArrayList<>();
}
