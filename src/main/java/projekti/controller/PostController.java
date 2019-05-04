package projekti.controller;

import java.util.*;
import projekti.domain.Account;
import projekti.service.CustomUserDetailsService;
import projekti.service.PostService;
import projekti.config.DevelopmentSecurityConfiguration;
import org.springframework.stereotype.Controller;
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
public class PostController {
    
    @Autowired
    private PostService postService; 
    
    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    @PostMapping("/profiles/{identifier}/newpost")
    public String post(@RequestParam String content, @PathVariable String identifier) {                        
                
        String username = userDetailsService.getCurrentUsername();        
        
        postService.addPost(identifier, username, content);                
        
        return "redirect:/profiles/" + identifier;
    }
 
    @PostMapping("/profiles/{identifier}/post/{id}/like")
    public String like(@PathVariable String identifier, @PathVariable Long id) {
                
        String username = userDetailsService.getCurrentUsername();        
                
        postService.like(username, id);
        
        return "redirect:/profiles/" + identifier;
    }
    
    @PostMapping("/profiles/{identifier}/post/{id}/comment")
    public String comment(@PathVariable String identifier, @PathVariable Long id, @RequestParam String comment) {
                
        String username = userDetailsService.getCurrentUsername();        
                
        postService.comment(username, id, comment);
        
        return "redirect:/profiles/" + identifier;
    }
        
}
