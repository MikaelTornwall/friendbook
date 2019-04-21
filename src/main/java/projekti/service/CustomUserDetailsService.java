package projekti.service;

import java.util.*;
import projekti.domain.Account;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import projekti.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projekti.config.DevelopmentSecurityConfiguration;

@Service
public class CustomUserDetailsService implements UserDetailsService {
        
    @Autowired
    private UserRepository userRepository;        
    
    @Autowired
    private DevelopmentSecurityConfiguration config;
        
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = userRepository.findByUsername(username);
        
        if (account == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }              
        
        User user = new User(
                account.getUsername(), 
                account.getPassword(), 
                true, 
                true, 
                true, 
                true, 
                Arrays.asList(new SimpleGrantedAuthority("USER")));
        
        return user;
    }       
        
    public void save(Account account) {
        PasswordEncoder encoder = config.passwordEncoder();
        account.setPassword(encoder.encode(account.getPassword()));                
        
        List<Account> users = userRepository.findAll();
        
        for (Account user : users) {
            if (user.getUsername().equals(account.getUsername())) return;
        }
        
        userRepository.save(account);
    }
}
