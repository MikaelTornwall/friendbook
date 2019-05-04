package projekti.controller;

import java.util.*;
import java.time.LocalDateTime;
import projekti.domain.Account;
import projekti.domain.Friend;
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
    private CustomUserDetailsService userService;        
    
    @Autowired
    private FriendService friendService;    
    
    @GetMapping("/profiles/{identifier}/friends")
    public String friends(@PathVariable String identifier, Model model) {
                
        Account account = userService.getCurrentUser();                        
        
        model.addAttribute("myprofile", account.getIdentifier());  
        
        List<Friend> friends = friendService.getFriends(identifier);
                       
        model.addAttribute("friends", friends);
       
        model.addAttribute("profile", userService.findByIdentifier(identifier).getUsername());
        
        return "friends";
    }
    
    @PostMapping("/profiles/{identifier}/friendrequests/accept")
    public String acceptFriendRequest(@PathVariable String identifier) {
                
        String username = userService.getCurrentUsername();
        String currentIdentifier = userService.getCurrentIdentifier();
        
        friendService.acceptFriendRequest(username, identifier);
                        
        return "redirect:/profiles/" + currentIdentifier + "/friendrequests";
    }
    
    @PostMapping("/profiles/{identifier}/friendrequests/decline")
    public String declineFriendRequest(@PathVariable String identifier) {
                
        String username = userService.getCurrentUsername();
        String currentIdentifier = userService.getCurrentIdentifier();
        
        friendService.declineFriendRequest(username, identifier);
                        
        return "redirect:/profiles/" + currentIdentifier + "/friendrequests";
    }
    
    @GetMapping("/profiles/{identifier}/friendrequests")
    public String friendRequests(@PathVariable String identifier, Model model) {
        
        Account account = userService.getCurrentUser();
        model.addAttribute("myprofile", account.getIdentifier());  
        
        List<Friend> friendRequests = friendService.getFriendRequests(identifier);
        
        if (friendRequests.isEmpty()) model.addAttribute("norequests", "No friend requests");        
        
        model.addAttribute("friendrequests", friendRequests);                
        model.addAttribute("date", LocalDateTime.now());        
        
        return "friendrequests";
    }
    
    @PostMapping("/profiles/add/{identifier}")
    public String sendRequest(@PathVariable String identifier) {                              
        
        Account firstAccount = userService.getCurrentUser();                                        
        Account secondAccount = userService.findByIdentifier(identifier);   
        
        friendService.sendFriendRequest(firstAccount, secondAccount);                
        
        return "redirect:/profiles";
    }
}
