package projekti.controller;

import java.util.*;
import java.util.stream.Collectors;
import projekti.domain.Account;
import projekti.domain.Post;
import projekti.service.CustomUserDetailsService;
import projekti.service.ProfileService;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;


@Controller
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProfileService profileService;
    
    @GetMapping("/profiles")
    public String getProfiles(Model model) {                
        
        model.addAttribute("profiles", profileService.profiles());                
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();        
        Account account = userRepository.findByUsername(username);                        
        model.addAttribute("myprofile", account.getIdentifier());        
        
        return "profiles";
    }
    
    @GetMapping("/profiles/{identifier}")
    public String getProfile(@PathVariable String identifier, Model model) {
                
        Account profile = userRepository.findByIdentifier(identifier);
        
        model.addAttribute("profile", profile);        
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();        
        Account account = userRepository.findByUsername(username);                        
        model.addAttribute("myprofile", account.getIdentifier());

        List<Post> posts = profile.getWall().getPosts();
                
        Collections.sort(posts, Collections.reverseOrder());
        
        List<Post> list = posts.stream().limit(25).collect(Collectors.toList());
        
        model.addAttribute("posts", list);                
        
        return "profile";
    }
    
    @PostMapping("/profiles")
    public String search(@RequestParam String username, Model model) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();        
        Account account = userRepository.findByUsername(name);                        
        model.addAttribute("myprofile", account.getIdentifier());
        
        model.addAttribute("profiles", profileService.profiles(username));
        
        return "profiles";
    }
    
    @GetMapping("/profiles/{identifier}/settings")
    public String settings(@PathVariable String identifier, Model model) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();        
        Account account = userRepository.findByUsername(username);  
        
        if (!identifier.equals(account.getIdentifier())) return "redirect:/profiles/" + identifier;
        
        model.addAttribute("profile", account);
        model.addAttribute("myprofile", account.getIdentifier());
        
        return "settings";
    }
}
