package pl.RK.PAIEVENTREST.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailsImp implements UserDetails {
    private static final long serialVersionUID = 1L;

    private String email;

    private String nick;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;


    public UserDetailsImp(String email, String nick, String password) {
        this.email = email;
        this.nick = nick;
        this.password = password;
        this.authorities = new HashSet<>();
    }

    public static UserDetailsImp build(UserPAI userPAI) {

        return new UserDetailsImp(
                userPAI.getEmail(),
                userPAI.getNick(),
                userPAI.getPassword());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
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
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImp user = (UserDetailsImp) o;
        return Objects.equals(email, user.email);
    }


}
