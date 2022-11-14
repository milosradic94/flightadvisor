package com.losmilos.flightadvisor.model.persistance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UserEntity implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Getter(onMethod = @__(@Override))
    @Column(nullable = false)
    private String username;

    @Getter(onMethod = @__(@Override))
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    @Transient
    @Getter(onMethod = @__(@Override))
    private Collection<? extends GrantedAuthority> authorities;

    public static UserEntity build(UserEntity userEntity) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(userEntity.getRole().getTitle().name()));

        return new UserEntity(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getUsername(), userEntity.getPassword(), userEntity.getRole(), authorities);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserEntity user = (UserEntity) o;

        return Objects.equals(id, user.id);
    }
}