package ru.bolodurin.socialmedia.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.bolodurin.socialmedia.entities.Post;
import ru.bolodurin.socialmedia.entities.User;
import ru.bolodurin.socialmedia.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void add(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void update(String username, User updatedUser) {
        User user = userRepository.findByUsername(username).orElseThrow();

        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        user.setPosts(updatedUser.getPosts());

        userRepository.save(updatedUser);
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("username not found" + username));
        return new org.springframework.security.core.userdetails
                .User(user.getUsername(), user.getPassword(), mapUserAuthorities(user.getAuthorities()));
    }

    private Collection<? extends GrantedAuthority> mapUserAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());
    }

}
