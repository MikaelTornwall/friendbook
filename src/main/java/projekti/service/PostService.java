package projekti.service;

import java.util.*;
import java.time.LocalDateTime;
import projekti.domain.Account;
import projekti.domain.Friend;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import projekti.repository.UserRepository;
import projekti.repository.WallRepository;
import projekti.repository.PostRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projekti.config.DevelopmentSecurityConfiguration;

@Service
public class PostService {
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private WallRepository wallRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public void addPost(String identifier, String username, String content) {
        
        
        
    }
}
