package net.sengimu.brickback.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.setting.Setting;
import net.sengimu.brickback.common.PathManager;

public class JwtKeyUtil {

    private static final PathManager PATH_MANAGER = new PathManager();

    public static String getJwtKey() {

        Setting jwtKeySetting = new Setting(FileUtil.touch(PATH_MANAGER.getDirPath() + PATH_MANAGER.getJwtKeyPath()), CharsetUtil.CHARSET_UTF_8, true);

        if (!jwtKeySetting.containsKey("key")) {
            generateJwtKey();
            jwtKeySetting.load();
        }

        return jwtKeySetting.getStr("key");
    }

    public static void generateJwtKey() {

        String jwtKey = IdUtil.simpleUUID();
        Setting jwtKeySetting = new Setting(FileUtil.touch(PATH_MANAGER.getDirPath() + PATH_MANAGER.getJwtKeyPath()), CharsetUtil.CHARSET_UTF_8, true);
        jwtKeySetting.set("key", jwtKey);
        jwtKeySetting.store();
    }
}
