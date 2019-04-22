package projekti.service;

import java.util.*;
import java.time.LocalDateTime;
import projekti.domain.Account;
import projekti.domain.Profile;
import projekti.domain.Friend;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import projekti.repository.UserRepository;
import projekti.repository.WallRepository;
import projekti.repository.PostRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projekti.config.DevelopmentSecurityConfiguration;
import projekti.repository.PostLikeRepository;
import projekti.repository.CommentRepository;

@Service
public class ProfileService {
    
    @Autowired
    private UserRepository userRepository;
    
    public List<Profile> profiles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();        
        Account currentUser = userRepository.findByUsername(username);        
        
        List<Account> accounts = userRepository.findAll();                
        List<Profile> profiles = new ArrayList<>();
        
        for (Account account : accounts) {                                    
            profiles.add(new Profile(account, false, false));
        }
        
        for (Profile profile : profiles) {
            for (Friend user : profile.getAccount().getFriendSet()) {
                if (user.getPerson().equals(currentUser)) profile.setFriend(true);
            }            
                
            if (profile.getAccount().equals(currentUser)) {
                profile.setUser(true);
            }
        }
        
        return profiles;
    }
    
    public List<Profile> profiles(String searchterm) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();        
        Account currentUser = userRepository.findByUsername(username);        
        
        List<Account> accounts = userRepository.findAll();                
        List<Profile> profiles = new ArrayList<>();
        
        for (Account account : accounts) {       
            if (account.getName().contains(searchterm) || account.getUsername().contains(searchterm)) {
                profiles.add(new Profile(account, false, false));
            }            
        }
        
        for (Profile profile : profiles) {
            for (Friend user : profile.getAccount().getFriendSet()) {
                if (user.getPerson().equals(currentUser)) profile.setFriend(true);
            }            
                
            if (profile.getAccount().equals(currentUser)) {
                profile.setUser(true);
            }
        }
        
        return profiles;               
    }
}
