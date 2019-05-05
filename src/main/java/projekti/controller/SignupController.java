package projekti.controller;

import java.util.*;
import projekti.domain.Account;
import projekti.domain.Wall;
import projekti.domain.PhotoAlbum;
import projekti.service.CustomUserDetailsService;
import projekti.repository.WallRepository;
import projekti.config.DevelopmentSecurityConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.repository.UserRepository;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;


@Controller
public class SignupController {
    
    @Autowired
    private CustomUserDetailsService userDetailsService;    
    
    @Autowired 
    private UserRepository userRepository;
    
    @GetMapping("/signup")
    public String signupPage(Model model) { 
                
        String username = userDetailsService.getCurrentUsername();
        List<Account> list = userRepository.findListByUsername(username);
        
        if (!list.isEmpty()) {
            return "redirect:/profiles";
        }
        
        return "signup";
    }
    
    @PostMapping("/signup")
    public String create(@RequestParam String username, @RequestParam String name, @RequestParam String password, @RequestParam String identifier) {                
                
        if (username.isEmpty() || name.isEmpty() || password.isEmpty() || identifier.isEmpty()) {
            return "signup";
        }
        
        Account account = new Account();
        account.setUsername(username);
        account.setName(name);
        account.setPassword(password);
        account.setIdentifier(username);                
        
        Wall wall = new Wall();
        wall.setOwner(account);
        
        account.setWall(wall);
        
        PhotoAlbum photoAlbum = new PhotoAlbum();
        photoAlbum.setOwner(account);
        
        account.setPhotoAlbum(photoAlbum);
                
        userDetailsService.save(account);        
        
        return "redirect:/";
    }
}
