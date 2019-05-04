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
import org.springframework.transaction.annotation.Transactional;

@Controller
public class PhotoController {
    
    @Autowired
    private CustomUserDetailsService userService;
    
    @Autowired
    private PhotoService photoService;
    
    @GetMapping("/profiles/{identifier}/photos")
    public String photos(@PathVariable String identifier, Model model) {
                                
        model.addAttribute("myprofile", userService.getCurrentIdentifier());  
                        
        model.addAttribute("profile", identifier);
        
        Account profile = userService.findByIdentifier(identifier);
        
        List<Photo> photos = profile.getPhotoAlbum().getPhotos();
        
        model.addAttribute("photos", photos);
        
        return "photos";
    }
    
    /*
    @GetMapping("/profiles/{identifier}/photoalbum/{id}")
    public String photo(@PathVariable String identifier, @PathVariable Long id, Model model) {
                                              
        model.addAttribute("myprofile", userService.getCurrentIdentifier());  
                        
        model.addAttribute("profile", identifier);
        
        Account profile = userService.findByIdentifier(identifier);
        
        List<Photo> photos = profile.getPhotoAlbum().getPhotos();  
        
        long next = id; 
        long prev = id;
        
        if (photos.size() > 1) {
            for (int i = 0; i < photos.size(); i++) {            
                if (photos.get(i).getId() == id) {
                    if (i == photos.size() - 1) {
                        next = photos.get(0).getId();
                        prev = photos.get(i - 1).getId();
                    } else if (i == 0) {
                        next = photos.get(i + 1).getId();
                        prev = photos.get(photos.size() - 1).getId();
                    } else {
                        next = photos.get(i + 1).getId();
                        prev = photos.get(i - 1).getId();                    
                    }
                }
            }         
        }
        
        if (id == 0) {            
            Photo photo = photos.get(0);
            model.addAttribute("photo", photo);            
        } else {
            Photo photo = photoService.getPhotoEntity(id);
            model.addAttribute("photo", photo);
        }       
        
        model.addAttribute("next", next);
        model.addAttribute("prev", prev);
        
        System.out.println(next);
        System.out.println(prev);
        
        return "photo";
    }
    */  
    
    @GetMapping("/profiles/{identifier}/photos/add")
    public String addphotos(@PathVariable String identifier, Model model) {                
                               
        model.addAttribute("myprofile", userService.getCurrentIdentifier());  
        
        return "addphoto";
    }
    
    
    @GetMapping(path = "/profiles/{identifier}/photos/{id}", produces = "image/png")
    @ResponseBody
    public byte[] get(@PathVariable String identifier, @PathVariable Long id) {                
        return photoService.getPhoto(id);
    }
    
    @Transactional
    @PostMapping("/profiles/{identifier}/photos/add")
    public String addPhoto(@RequestParam("file") MultipartFile file, @RequestParam String description, @PathVariable String identifier) {                                
        
        Account account = userService.getCurrentUser();             
               
        photoService.addPhoto(file, description, account);
        
        return "redirect:/profiles/" + account.getUsername() + "/photos";
    }
    
    @PostMapping("/profiles/{identifier}/photos/{id}/like")
    public String like(@PathVariable String identifier, @PathVariable Long id) {
                
        String username = userService.getCurrentUsername();
        
        photoService.like(username, id);
        
        return "redirect:/profiles/" + identifier + "/photos";
    }
    
    @PostMapping("/profiles/{identifier}/photos/{id}/comment")
    public String comment(@PathVariable String identifier, @PathVariable Long id, @RequestParam String comment) {
                
        String username = userService.getCurrentUsername();                
        
        photoService.comment(username, id, comment);
        
        return "redirect:/profiles/" + identifier + "/photos";
    }
     
    @PostMapping("/profiles/{identifier}/photos/{id}/delete")
    public String deletePhoto(@PathVariable String identifier, @PathVariable Long id) {                
                                             
        Account account = userService.getCurrentUser();
        
        if (!identifier.equals(account.getIdentifier())) return "redirect:/profiles/" + identifier + "/photos";
        
        photoService.deletePhoto(id);                        
        
        return "redirect:/profiles/" + identifier + "/photos";
    }
    
    @GetMapping("/profiles/{identifier}/selectprofilepicture")
    public String profilePictures(@PathVariable String identifier, Model model) {
                      
        Account account = userService.getCurrentUser();                        
        
        model.addAttribute("myprofile", account.getIdentifier());  
        
        model.addAttribute("profile", identifier);
        
        List<Photo> photos = account.getPhotoAlbum().getPhotos();
        
        model.addAttribute("photos", photos);
        
        return "selectprofilepicture";
    }
    
    @PostMapping("/profiles/{identifier}/selectprofilepicture/{id}")
    public String selectProfilePicture(@PathVariable String identifier, @PathVariable Long id) {                
        
        Account account = userService.getCurrentUser();                        
        
        Photo photo = photoService.getPhotoEntity(id);
        
        account.setProfilePicture(photo);
        
        userService.update(account);
        
        return "redirect:/profiles/" + account.getIdentifier();
    }
}
