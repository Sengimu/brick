package net.sengimu.brickback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.sengimu.brickback.po.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}