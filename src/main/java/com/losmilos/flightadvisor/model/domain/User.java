package com.losmilos.flightadvisor.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter @Setter
public class User implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;

    @Getter(onMethod = @__(@Override))
    private String username;

    @Getter(onMethod = @__(@Override))
    @JsonIgnore
    private String password;

    private Role role;

    @Getter(onMethod = @__(@Override))
    private Collection<? extends GrantedAuthority> authorities;

    public static User build(User user) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getRole().name()));

        return new User(user.getId(), user.getUsername(), user.getPassword(), user.getRole(), authorities);
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
