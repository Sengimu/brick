package net.sengimu.brickback.common.init;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface InitMapper {

    @Select("select count(*) from user where id = '1'")
    int initBeforeCheckSelectUser();

    @Select("select count(*) from profile where id = '1'")
    int initBeforeCheckSelectProfile();

    @Insert("insert into user values (1,'test@126.com', 'test', '93327f2856df1105a1318895ac44e684',null,null,'946656000000','1710056286')")
    void initUser();

    @Insert("insert into user_property values (null, 'preferredLanguage', 'zh_CN', 1)")
    void initUserProperty();

    @Insert("insert into profile values (1,'530fa97a357f3c1994d30c5c65c18fe8', 'test', 'skin,cape',1)")
    void initProfile();

    @Insert("insert into texture values (null, 'SKIN','a69e403fb50f46278bdfab3571475f43787354e01f86f52221f64974f6cc5c2e', 'default',1)")
    void initTexture();
}
