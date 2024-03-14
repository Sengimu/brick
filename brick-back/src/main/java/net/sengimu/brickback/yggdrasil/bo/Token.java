package net.sengimu.brickback.yggdrasil.bo;

import cn.hutool.core.util.IdUtil;
import cn.hutool.jwt.JWT;
import lombok.Data;
import net.sengimu.brickback.utils.JwtKeyUtil;

import java.util.Date;

@Data
public class Token {

    private String accessToken;
    private String clientToken;
    private String uuid;
    private long createTime;

    public Token(Integer userId, String uuid) {
        this(userId,uuid,null);
    }

    public Token(Integer userId, String uuid, String clientToken) {

        this.accessToken = JWT.create()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15))
                .setPayload("userId", userId)
                .setKey(JwtKeyUtil.getJwtKey().getBytes())
                .sign();

        this.clientToken = clientToken != null ? clientToken : IdUtil.simpleUUID();
        this.uuid = uuid;
        this.createTime = System.currentTimeMillis();
    }
}
