package net.sengimu.brickback.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("profile")
public class Profile {

    @TableId("id")
    private Integer id;
    private String uuid;
    private String name;
    private String allowUpload;
    private Integer userId;
}
