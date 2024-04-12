package net.sengimu.brickback.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import net.sengimu.brickback.po.Texture;
import net.sengimu.brickback.mapper.TextureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TextureService {

    @Autowired
    private TextureMapper textureMapper;

    public List<Texture> getTexturesByProfileId(int profileId) {

        QueryWrapper<Texture> textureQueryWrapper = new QueryWrapper<>();
        textureQueryWrapper.eq("profile_id", profileId);
        return textureMapper.selectList(textureQueryWrapper);
    }

    public Integer putTextureByProfileId(Texture texture) {

        QueryWrapper<Texture> textureQueryWrapper = new QueryWrapper<>();
        textureQueryWrapper.eq("profile_id", texture.getProfileId());
        textureQueryWrapper.eq("type", texture.getType());

        if (textureMapper.selectOne(textureQueryWrapper) == null) {
            return textureMapper.insert(texture);
        } else {
            UpdateWrapper<Texture> textureUpdateWrapper = new UpdateWrapper<>();
            textureUpdateWrapper.eq("profile_id", texture.getProfileId());
            textureUpdateWrapper.eq("type", texture.getType());
            return textureMapper.update(texture, textureUpdateWrapper);
        }
    }

    public void deleteTextureByProfileId(Integer profileId, String textureType) {

        QueryWrapper<Texture> textureQueryWrapper = new QueryWrapper<>();
        textureQueryWrapper.eq("profile_id", profileId);
        textureQueryWrapper.eq("type", textureType);
        textureMapper.delete(textureQueryWrapper);
    }
}
