package projekti.service;

import java.util.*;
import java.time.LocalDateTime;
import java.io.IOException;
import projekti.domain.Account;
import projekti.domain.Photo;
import projekti.domain.PhotoAlbum;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import projekti.repository.UserRepository;
import projekti.repository.PhotoRepository;
import projekti.repository.PhotoAlbumRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projekti.config.DevelopmentSecurityConfiguration;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PhotoService {
    
    @Autowired
    private PhotoRepository photoRepository;
    
    @Autowired
    private PhotoAlbumRepository photoAlbumRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public void addPhoto(MultipartFile file, String description, Account account) {
        
        Photo newPhoto = new Photo();
        
        PhotoAlbum album = account.getPhotoAlbum();
        
        try {
            newPhoto.setContent(file.getBytes());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
        newPhoto.setDescription(description);
        newPhoto.setLikes(0);
        newPhoto.setPhotoAlbum(album);
        
        photoRepository.save(newPhoto);
    }
    
    public byte[] getPhoto(Long id) {
        return photoRepository.getOne(id).getContent();
    }
}
