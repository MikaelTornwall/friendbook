package projekti.controller;

import projekti.service.CustomUserDetailsService;
import java.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context .SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import projekti.repository.UserRepository;
import projekti.domain.Account;

@Controller
public class DefaultController {

    @Autowired
    private UserRepository userRepository;    
            
    @GetMapping("*")
    public String helloWorld(Model model) { 
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();        
        List<Account> list = userRepository.findListByUsername(username);
        
        if (!list.isEmpty()) {
            return "redirect:/profiles";
        }
        
        model.addAttribute("brand", "FriendBook!");                
        
        return "index";
    }
    
    /*
    @GetMapping("/home")
    public String home(Model model) {                 
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();        
        Account account = userRepository.findByUsername(username);                        
        model.addAttribute("myprofile", account.getIdentifier());
        
        model.addAttribute("brand", username);
        return "home";
    }
    */
}
