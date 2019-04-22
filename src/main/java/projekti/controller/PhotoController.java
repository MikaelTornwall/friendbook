package projekti.controller;

import java.util.*;
import projekti.domain.Account;
import projekti.domain.Photo;
import projekti.service.CustomUserDetailsService;
import projekti.service.PhotoService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import projekti.repository.UserRepository;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PhotoController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PhotoService photoService;
    
    @GetMapping("/profiles/{identifier}/photos")
    public String photos(@PathVariable String identifier, Model model) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();           
        Account account = userRepository.findByUsername(username);                        
        model.addAttribute("myprofile", account.getIdentifier());  
        
        model.addAttribute("profile", identifier);
        
        List<Photo> photos = account.getPhotoAlbum().getPhotos();
        
        model.addAttribute("photos", photos);
        
        return "photos";
    }
            
    @GetMapping("/profiles/{identifier}/photos/add")
    public String addphotos(@PathVariable String identifier, Model model) {
                
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();           
        Account account = userRepository.findByUsername(username);                        
        model.addAttribute("myprofile", account.getIdentifier());  
        
        return "addphoto";
    }
    
    @GetMapping(path = "/profiles/{identifier}/photos/{id}", produces = "image/png")
    @ResponseBody
    public byte[] get(@PathVariable String identifier, @PathVariable Long id) {                
        return photoService.getPhoto(id);
    }    
    
    @PostMapping("/profiles/{identifier}/photos/add")
    public String addPhoto(@RequestParam("file") MultipartFile file, @RequestParam String description, @PathVariable String identifier) {                
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();                   
        Account account = userRepository.findByUsername(username);     
        // if (identifier != username) return "photos";
        
        System.out.println("Description: " + description);
        
        photoService.addPhoto(file, description, account);
        
        return "redirect:/profiles/" + username + "/photos";
    }
    
    @GetMapping("/profiles/{identifier}/selectprofilepicture")
    public String profilePictures(@PathVariable String identifier, Model model) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();           
        Account account = userRepository.findByUsername(username);                        
        model.addAttribute("myprofile", account.getIdentifier());  
        
        model.addAttribute("profile", identifier);
        
        List<Photo> photos = account.getPhotoAlbum().getPhotos();
        
        model.addAttribute("photos", photos);
        
        return "selectprofilepicture";
    }
    
    @PostMapping("/profiles/{identifier}/selectprofilepicture/{id}")
    public String selectProfilePicture(@PathVariable String identifier, @PathVariable Long id) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();           

        System.out.println(identifier);
        System.out.println(id);
        
        Account account = userRepository.findByUsername(username);                        
        
        Photo photo = photoService.getPhotoEntity(id);
        
        account.setProfilePicture(photo);
        
        userRepository.save(account);
        
        return "redirect:/profiles/" + username;
    }
}
