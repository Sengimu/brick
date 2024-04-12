package net.sengimu.brickback.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.sengimu.brickback.mapper.UserMapper;
import net.sengimu.brickback.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userMapper.selectOne(new QueryWrapper<User>().eq("email", email));
    }
}
