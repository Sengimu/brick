package net.sengimu.brickback.service;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.sengimu.brickback.po.User;
import net.sengimu.brickback.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserByUsernameAndPassword(String username, String password) {

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("email", username);
        userQueryWrapper.eq("password", DigestUtil.md5Hex(password));
        return userMapper.selectOne(userQueryWrapper);
    }

    public User getUserById(Integer id) {

        return userMapper.selectById(id);
    }
}
