package projekti.controller;

import java.util.*;
import projekti.domain.Account;
import projekti.service.CustomUserDetailsService;
import projekti.config.DevelopmentSecurityConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import projekti.repository.UserRepository;

@Controller
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/profiles")
    public String getProfiles(Model model) {
        
        model.addAttribute("profiles", userRepository.findAll());
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();        
        Account account = userRepository.findByUsername(username);                        
        model.addAttribute("myprofile", account.getIdentifier());        
        
        return "profiles";
    }
    
    @GetMapping("/profiles/{identifier}")
    public String getProfile(@PathVariable String identifier, Model model) {
                
        model.addAttribute("profile", userRepository.findByIdentifier(identifier));
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();        
        Account account = userRepository.findByUsername(username);                        
        model.addAttribute("myprofile", account.getIdentifier());
        
        return "profile";
    }
    
    @PostMapping("/profiles")
    public String search(@RequestParam String username, Model model) {
        
        List<Account> allUsers = userRepository.findAll();
        
        List<Account> foundUsers = new ArrayList<>();
        
        for (Account account : allUsers) {
            if (account.getName().contains(username) || account.getUsername().contains(username)) {
                foundUsers.add(account);
            }
        }
        
        model.addAttribute("profiles", foundUsers);
        
        return "profiles";
    }
}