package projekti.service;

import java.util.*;
import java.time.LocalDateTime;
import projekti.domain.Account;
import projekti.domain.Friend;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import projekti.repository.UserRepository;
import projekti.repository.FriendRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projekti.config.DevelopmentSecurityConfiguration;

@Service
public class FriendService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private FriendRepository friendRepository;        
    
    public void sendFriendRequest(Account firstAccount, Account secondAccount) {
        
        LocalDateTime date = LocalDateTime.now();
                
        Friend secondFriend = new Friend(secondAccount, firstAccount, date, false);
                
        if (!secondAccount.getFriendSet().contains(secondFriend)) {
            secondAccount.getFriendSet().add(secondFriend);
        }
        
                
        friendRepository.save(secondFriend);
                
        userRepository.save(secondAccount);               
    }
    
    public void acceptFriendRequest(String username, String identifier) {
        
        Account owner = userRepository.findByUsername(username);                                
        Account person = userRepository.findByIdentifier(identifier);
        
        this.sendFriendRequest(owner, person);
        
        List<Friend> firstFriendList = friendRepository.findByOwner(owner);
        List<Friend> secondFriendList = friendRepository.findByOwner(person);
        
        for (Friend friend : firstFriendList) {
            if (friend.getOwner().getUsername().equals(username) && friend.getPerson().getUsername().equals(identifier)) {
                friend.setActive(true);
                friendRepository.save(friend);
            }
        }
        
        for (Friend friend : secondFriendList) {
            if (friend.getOwner().getUsername().equals(identifier) && friend.getPerson().getUsername().equals(username)) {
                friend.setActive(true);
                friendRepository.save(friend);
            }
        }                           
    }
    
    public void declineFriendRequest(String username, String identifier) {
        
        Account owner = userRepository.findByUsername(username);                                        
        
        List<Friend> firstFriendList = friendRepository.findByOwner(owner);        
        Iterator iteraattori = firstFriendList.iterator();
        
        while (iteraattori.hasNext()) {
            Friend friend = (Friend) iteraattori.next();
            
            if (friend.getOwner().getUsername().equals(username) && friend.getPerson().getUsername().equals(identifier)) {                
                friend.setOwner(null);
                friend.setPerson(null);
                friendRepository.save(friend);
                iteraattori.remove();
            }
        }                                           
    }
    
    public List<Friend> getFriendRequests(String identifier) {
        
        Account account = userRepository.findByIdentifier(identifier);
        
        List<Friend> friends = account.getFriendSet();
        
        List<Friend> friendAccounts = new ArrayList<>();
        
        for (Friend friend : friends) {
            if (!friend.isActive() && friend.getOwner().equals(account)) {                
                friendAccounts.add(friend);                
            }
        }
        
        return friendAccounts;
    }
  
    public List<Friend> getFriends(String identifier) {
        
        Account account = userRepository.findByIdentifier(identifier);
        
        List<Friend> friends = account.getFriendSet();
        
        List<Friend> friendAccounts = new ArrayList<>();
        
        for (Friend friend : friends) {
            if (friend.isActive() && !friendAccounts.contains(friend)) friendAccounts.add(friend);
        }
        
        return friendAccounts;
    }
        
}
