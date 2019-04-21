package projekti.controller;

import java.util.*;
import projekti.domain.Account;
import projekti.domain.FriendList;
import projekti.service.CustomUserDetailsService;
import projekti.service.FriendService;
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
import projekti.repository.FriendRepository;

@Controller
public class FriendController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private FriendRepository friendRepository;
    
    @Autowired
    private FriendService friendService;
    
    @GetMapping("/profiles/{identifier}/friends")
    public String friends(@PathVariable String identifier, Model model) {
        
        Account account = userRepository.findByIdentifier(identifier);
        
        FriendList friendList = account.getFriends();
        
        Long id = friendList.getId();
        
        friendList = friendRepository.getOne(id);
        
        List<Account> friends = friendList.getFriendSet();        
        
        model.addAttribute("friends", friends);
        
        return "friends";
    }
    
    @PostMapping("/profiles/add/{identifier}")
    public String addFriend(@PathVariable String identifier) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();        
        
        System.out.println("Eka: " + username);
        System.out.println("Toka: " + identifier);
        
        Account firstAccount = userRepository.findByUsername(username);                                        
        Account secondAccount = userRepository.findByIdentifier(identifier);   
        
        friendService.addFriend(firstAccount, secondAccount);                
        
        return "redirect:/profiles";
    }
}
