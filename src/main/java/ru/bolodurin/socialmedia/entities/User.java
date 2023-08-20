package ru.bolodurin.socialmedia.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {         //в этом же юзере делать поля логики?
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    //    hashPassword();
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Post> posts;

    public User() {
        this.posts = new ArrayList<>();
    }

//    @ManyToMany
//    private List<User> subscriptions;

//    FRIENDS??

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "users_authorities",
//            joinColumns = @JoinColumn(name = "username"),
//            inverseJoinColumns = @JoinColumn(name = "authority_id"))
//    private List<Authority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
