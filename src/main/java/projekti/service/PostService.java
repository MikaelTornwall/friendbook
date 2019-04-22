package projekti.service;

import java.util.*;
import java.time.LocalDateTime;
import projekti.domain.Account;
import projekti.domain.Wall;
import projekti.domain.Post;
import projekti.domain.PostLike;
import projekti.domain.Comment;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import projekti.repository.UserRepository;
import projekti.repository.WallRepository;
import projekti.repository.PostRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projekti.config.DevelopmentSecurityConfiguration;
import projekti.repository.PostLikeRepository;
import projekti.repository.CommentRepository;

@Service
public class PostService {
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private WallRepository wallRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired 
    private PostLikeRepository postLikeRepository;
    
    @Autowired 
    private CommentRepository commentRepository;
    
    public void addPost(String identifier, String username, String content) {
        
        Account owner = userRepository.findByIdentifier(identifier);
        Account poster = userRepository.findByUsername(username);
        
        LocalDateTime date = LocalDateTime.now();
         
        Wall wall = wallRepository.findByOwner(owner);
         
        Post newPost = new Post();
        newPost.setWall(wall);
        newPost.setOwner(poster);
        newPost.setDate(date);
        newPost.setContent(content);    
        newPost.setLikes(0);
        
        postRepository.save(newPost);
    }
    
    public void like(String username, Long id) {
        
        Account account = userRepository.findByUsername(username);                
        
        Post post = postRepository.getOne(id);        
        
        List<PostLike> likers = post.getLikeList();
        
        for (PostLike like : likers) {
            if (like.getLiker().getUsername().equals(username)) return;
        }
                
        PostLike newLike = new PostLike(account);        
        likers.add(newLike);
        post.setLikes(post.getLikes() + 1);      
        
        postLikeRepository.save(newLike);
        postRepository.save(post);                
    }
    
    public void comment(String username, Long id, String comment) {
        
        Account account = userRepository.findByUsername(username);                
        
        Post post = postRepository.getOne(id);
        
        LocalDateTime localDateTime = LocalDateTime.now();
        
        Comment newComment = new Comment(account, comment, localDateTime);
        
        post.getComments().add(newComment);
        
        commentRepository.save(newComment);
        postRepository.save(post);
    }
}
