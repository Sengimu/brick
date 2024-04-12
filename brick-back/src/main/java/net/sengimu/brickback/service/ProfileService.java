package net.sengimu.brickback.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.sengimu.brickback.po.Profile;
import net.sengimu.brickback.mapper.ProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    @Autowired
    private ProfileMapper profileMapper;

    public List<Profile> getProfilesByUserId(int userId) {

        QueryWrapper<Profile> profileQueryWrapper = new QueryWrapper<>();
        profileQueryWrapper.eq("user_id", userId);
        return profileMapper.selectList(profileQueryWrapper);
    }

    public Profile getProfileByUUID(String uuid) {

        QueryWrapper<Profile> profileQueryWrapper = new QueryWrapper<>();
        profileQueryWrapper.eq("uuid", uuid);
        return profileMapper.selectOne(profileQueryWrapper);
    }

    public Profile getProfileByName(String profileName) {

        QueryWrapper<Profile> profileQueryWrapper = new QueryWrapper<>();
        profileQueryWrapper.eq("name", profileName);
        return profileMapper.selectOne(profileQueryWrapper);
    }
}
