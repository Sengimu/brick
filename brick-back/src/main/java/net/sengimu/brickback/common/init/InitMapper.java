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

    @Insert("insert into user(id, email, username, password, role) values (1,'test@126.com', 'test', '$2a$10$4YxaUAjbeIruZypJoatKOuWrdIUSuE7GV7R1V4kF7B4eYaY9KiHZC','ADMIN')")
    void initUser();

    @Insert("insert into user_property values (null, 'preferredLanguage', 'zh_CN', 1)")
    void initUserProperty();

    @Insert("insert into profile values (1,'530fa97a357f3c1994d30c5c65c18fe8', 'test', 'skin,cape',1)")
    void initProfile();

    @Insert("insert into texture values (null, 'SKIN','none', 'default',1)")
    void initTexture();
}
