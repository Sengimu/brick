package net.sengimu.brickback.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("texture")
public class Texture {

    @TableId("id")
    private Integer id;
    private String type;
    private String hashPath;
    private String model;
    private Integer profileId;
}
