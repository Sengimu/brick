package net.sengimu.brickback.yggdrasil.bo;

import cn.hutool.core.util.IdUtil;
import lombok.Data;
import net.sengimu.brickback.utils.JwtUtil;

import java.util.Map;

@Data
public class YggdrasilToken {

    private String accessToken;
    private String clientToken;
    private String uuid;
    private long createTime;

    public YggdrasilToken(Integer userId, String uuid) {
        this(userId, uuid, null);
    }

    public YggdrasilToken(Integer userId, String uuid, String clientToken) {

        this.accessToken = JwtUtil.createJwt(Map.of("userId", userId));
        this.clientToken = clientToken != null ? clientToken : IdUtil.simpleUUID();
        this.uuid = uuid;
        this.createTime = System.currentTimeMillis();
    }
}
