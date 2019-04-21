package projekti.service;

import java.util.*;
import projekti.domain.Account;
import projekti.domain.FriendList;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import projekti.repository.UserRepository;
import projekti.repository.FriendRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projekti.config.DevelopmentSecurityConfiguration;

@Service
public class FriendService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private FriendRepository friendRepository;
    
    public void addFriend(Account firstAccount, Account secondAccount) {

        FriendList firstList = firstAccount.getFriends();
        FriendList secondList = secondAccount.getFriends();        
        
        firstList.getFriendSet().add(secondAccount);        
        secondList.getFriendSet().add(firstAccount);
                
        this.save(firstList);
        this.save(secondList);                
        
        //userRepository.save(firstAccount);
        //userRepository.save(secondAccount);
        
    }
    
    public void save(FriendList friendList) {        
        friendRepository.save(friendList);
    }
}
