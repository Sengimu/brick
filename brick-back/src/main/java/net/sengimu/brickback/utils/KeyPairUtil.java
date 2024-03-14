package net.sengimu.brickback.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.setting.Setting;
import net.sengimu.brickback.common.PathManager;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class KeyPairUtil {

    private static final PathManager PATH_MANAGER = new PathManager();

    public static String getPrivateKeyBase64() {

        Setting keyPairSetting = new Setting(FileUtil.touch(PATH_MANAGER.getDirPath() + PATH_MANAGER.getKeyPairPath()), CharsetUtil.CHARSET_UTF_8, true);

        if (!keyPairSetting.containsKey("privateKey")) {
            generateKeyPair();
            keyPairSetting.load();
        }

        return keyPairSetting.getStr("privateKey");
    }

    public static String getPublicKeyBase64() {

        Setting keyPairSetting = new Setting(FileUtil.touch(PATH_MANAGER.getDirPath() + PATH_MANAGER.getKeyPairPath()), CharsetUtil.CHARSET_UTF_8, true);

        if (!keyPairSetting.containsKey("publicKey")) {
            generateKeyPair();
            keyPairSetting.load();
        }

        return keyPairSetting.getStr("publicKey");
    }

    public static void generateKeyPair() {

        KeyPairGenerator gen = null;
        try {
            gen = KeyPairGenerator.getInstance("RSA");
        } catch (Exception ignored) {

        }
        assert gen != null;
        gen.initialize(4096);
        KeyPair pair = gen.generateKeyPair();

        String privateKey = Base64.encode(pair.getPrivate().getEncoded());
        String publicKey = Base64.encode(pair.getPublic().getEncoded());

        Setting keyPairSetting = new Setting(FileUtil.touch(PATH_MANAGER.getDirPath() + PATH_MANAGER.getKeyPairPath()), CharsetUtil.CHARSET_UTF_8, true);
        keyPairSetting.set("publicKey", publicKey);
        keyPairSetting.set("privateKey", privateKey);
        keyPairSetting.store();
    }
}
