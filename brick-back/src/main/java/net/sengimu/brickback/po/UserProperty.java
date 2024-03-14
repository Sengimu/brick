package net.sengimu.brickback.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_property")
public class UserProperty {

    @TableId("id")
    private Integer id;
    private String name;
    private String value;
    private Integer userId;
}
