package projekti.controller;

import java.util.*;
import projekti.domain.Account;
import projekti.domain.Wall;
import projekti.service.CustomUserDetailsService;
import projekti.repository.WallRepository;
import projekti.config.DevelopmentSecurityConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.repository.UserRepository;

@Controller
public class SignupController {
    
    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    @Autowired
    private WallRepository wallRepository;
    
    @GetMapping("/signup")
    public String signupPage(Model model) {          
        return "signup";
    }
    
    @PostMapping("/signup")
    public String signup(@RequestParam String username, @RequestParam String name, @RequestParam String password, @RequestParam String identifier) {
        
        System.out.println(username);
        System.out.println(name);
        System.out.println(password);
        
        Account account = new Account();
        account.setUsername(username);
        account.setName(name);
        account.setPassword(password);
        account.setIdentifier(identifier);                
        
        Wall wall = new Wall();
        wall.setOwner(account);
        
        account.setWall(wall);
                
        userDetailsService.save(account);        
        
        return "redirect:/signup";
    }
}
