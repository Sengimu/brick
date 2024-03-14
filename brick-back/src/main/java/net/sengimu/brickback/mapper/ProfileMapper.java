package net.sengimu.brickback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.sengimu.brickback.po.Profile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProfileMapper extends BaseMapper<Profile> {
}
