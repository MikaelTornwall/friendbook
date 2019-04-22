package projekti.domain;

import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor @AllArgsConstructor @Data
public class Profile {
    
    private Account account;
    private boolean friend;
    private boolean user;
}
