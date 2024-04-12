package net.sengimu.brickback.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User implements UserDetails {

    @TableId("id")
    private Integer id;
    private String email;
    private String username;
    private String password;
    private String role;
    private String avatar;
    private String ip;
    private String register;
    private String lastLogin;
    private Integer disabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null) {
            return null;
        }
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return disabled == 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return disabled == 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return disabled == 0;
    }

    @Override
    public boolean isEnabled() {
        return disabled == 0;
    }
}
