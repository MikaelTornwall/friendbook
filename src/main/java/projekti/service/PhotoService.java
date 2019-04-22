package projekti.service;

import java.util.*;
import java.time.LocalDateTime;
import java.io.IOException;
import projekti.domain.Account;
import projekti.domain.Photo;
import projekti.domain.PhotoAlbum;
import projekti.domain.PostLike;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import projekti.repository.UserRepository;
import projekti.repository.PhotoRepository;
import projekti.repository.PhotoAlbumRepository;
import projekti.repository.PostLikeRepository;
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
    
    @Autowired
    private PostLikeRepository postLikeRepository;
    
    public void addPhoto(MultipartFile file, String description, Account account) {                
        
        PhotoAlbum album = account.getPhotoAlbum();
        
        if (album.getPhotos().size() > 10) return;
        
        Photo newPhoto = new Photo();
        
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
    
    public void like(String username, Long id) {
        
        Account account = userRepository.findByUsername(username);                
        
        Photo photo = photoRepository.getOne(id);        
        
        List<PostLike> likers = photo.getLikeList();
        
        for (PostLike like : likers) {
            if (like.getLiker().getUsername().equals(username)) return;
        }             
        
        PostLike newLike = new PostLike(account);        
        likers.add(newLike);
        photo.setLikes(photo.getLikes() + 1);      
        
        postLikeRepository.save(newLike);
        photoRepository.save(photo);                                        
    }
    
    public void deletePhoto(Long id) {      
        
        Photo photo = photoRepository.getOne(id);                        
                
        photo.setLikeList(new ArrayList<>());
        
        photoRepository.save(photo);
        
        photoRepository.deleteById(id);        
    }
    
    public byte[] getPhoto(Long id) {
        return photoRepository.getOne(id).getContent();
    }
    
    public Photo getPhotoEntity(Long id) {
        return photoRepository.getOne(id);
    }
}
