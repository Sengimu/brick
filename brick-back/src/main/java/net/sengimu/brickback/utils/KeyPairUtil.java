package net.sengimu.brickback.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.setting.Setting;
import net.sengimu.brickback.common.PathManager;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class KeyPairUtil {

    public static String getPrivateKeyBase64() throws NoSuchAlgorithmException {

        Setting keyPairSetting = new Setting(FileUtil.touch(PathManager.USER_DIR + PathManager.KEY_PAIR_PATH), CharsetUtil.CHARSET_UTF_8, true);

        if (!keyPairSetting.containsKey("privateKey")) {
            generateKeyPair();
            keyPairSetting.load();
        }

        return keyPairSetting.getStr("privateKey");
    }

    public static String getPublicKeyBase64() throws NoSuchAlgorithmException {

        Setting keyPairSetting = new Setting(FileUtil.touch(PathManager.USER_DIR + PathManager.KEY_PAIR_PATH), CharsetUtil.CHARSET_UTF_8, true);

        if (!keyPairSetting.containsKey("publicKey")) {
            generateKeyPair();
            keyPairSetting.load();
        }

        return keyPairSetting.getStr("publicKey");
    }

    public static void generateKeyPair() throws NoSuchAlgorithmException {

        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");

        assert gen != null;
        gen.initialize(4096);
        KeyPair pair = gen.generateKeyPair();

        String privateKey = Base64.encode(pair.getPrivate().getEncoded());
        String publicKey = Base64.encode(pair.getPublic().getEncoded());

        Setting keyPairSetting = new Setting(FileUtil.touch(PathManager.USER_DIR + PathManager.KEY_PAIR_PATH), CharsetUtil.CHARSET_UTF_8, true);
        keyPairSetting.set("publicKey", publicKey);
        keyPairSetting.set("privateKey", privateKey);
        keyPairSetting.store();
    }
}
