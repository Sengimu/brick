package net.sengimu.brickback.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.sengimu.brickback.po.UserProperty;
import net.sengimu.brickback.mapper.UserPropertyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPropertyService {

    @Autowired
    private UserPropertyMapper userPropertyMapper;

    public List<UserProperty> getUserPropertiesByUserId(int userId) {

        QueryWrapper<UserProperty> userPropertyQueryWrapper = new QueryWrapper<>();
        userPropertyQueryWrapper.eq("user_id", userId);
        return userPropertyMapper.selectList(userPropertyQueryWrapper);
    }
}
