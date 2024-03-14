package net.sengimu.brickback.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User {

    @TableId("id")
    private Integer id;
    private String email;
    private String username;
    private String password;
    private String avatar;
    private String register;
    private String lastLogin;
}
