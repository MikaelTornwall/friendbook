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
    
    @PostMapping("/profiles/{identifier}/newpost")
    public String newPost(@RequestParam String content, @PathVariable String identifier) {                
        
        System.out.println("Content: " + content);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();                
        
        postService.addPost(identifier, username, content);
        
        System.out.println("Profile: " + identifier);
        System.out.println("My account: " + username);
        
        return "redirect:/profiles/" + identifier;
    }
    
}
