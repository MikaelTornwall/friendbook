package projekti.domain;

import java.util.*;
import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;


@Entity
@NoArgsConstructor @AllArgsConstructor @Data
public class Account extends AbstractPersistable<Long> {   
        
    private String username;
        
    private String name;
        
    private String password;
        
    private String identifier;               
        
    @OneToOne(cascade = CascadeType.ALL)
    private Photo profilePicture;
    
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Friend> friendSet = new ArrayList<>();    
    
    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL)
    private Wall wall;
    
    @OneToMany(mappedBy = "owner")
    private List<Post> posts = new ArrayList<>();
    
    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL)
    private PhotoAlbum photoAlbum;            
}
