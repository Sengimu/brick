package net.sengimu.brickback.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.setting.Setting;
import net.sengimu.brickback.common.PathManager;

import java.util.Date;
import java.util.Map;

public class JwtUtil {

    public static String createJwt(Map<String, ?> payloads) {

        return JWT.create()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15))
                .addPayloads(payloads)
                .setKey(getJwtSecret().getBytes())
                .sign();
    }

    public static JSONObject parseJwt(String token) {

        JWT jwt = JWT.of(token).setKey(getJwtSecret().getBytes());
        if (!jwt.validate(0)) {
            return null;
        }

        return jwt.getPayloads();
    }

    private static String getJwtSecret() {

        Setting jwtKeySetting = new Setting(FileUtil.touch(PathManager.USER_DIR + PathManager.JWT_KEY_PATH), CharsetUtil.CHARSET_UTF_8, true);

        if (!jwtKeySetting.containsKey("key")) {
            generateJwtSecret();
            jwtKeySetting.load();
        }

        return jwtKeySetting.getStr("key");
    }

    public static void generateJwtSecret() {

        String jwtKey = IdUtil.simpleUUID();
        Setting jwtKeySetting = new Setting(FileUtil.touch(PathManager.USER_DIR + PathManager.JWT_KEY_PATH), CharsetUtil.CHARSET_UTF_8, true);
        jwtKeySetting.set("key", jwtKey);
        jwtKeySetting.store();
    }
}
