package net.sengimu.brickback.common.init;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.sengimu.brickback.common.PathManager;
import net.sengimu.brickback.exception.GlobalExceptionHandler;
import net.sengimu.brickback.utils.JwtUtil;
import net.sengimu.brickback.utils.KeyPairUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.util.Locale;

@Slf4j
@Service
public class Init {

    @Autowired
    private InitService initService;

    @PostConstruct
    private void init() {

        boolean flag = false;

        try {
            Locale.setDefault(Locale.ENGLISH);

            initService.initData();

            if (!FileUtil.exist(PathManager.USER_DIR + PathManager.APPLICATION_PATH)) {

                File applicationFile = FileUtil.touch(PathManager.USER_DIR + PathManager.APPLICATION_PATH);
                BufferedInputStream is = FileUtil.getInputStream("default" + PathManager.APPLICATION_PATH);
                BufferedOutputStream os = FileUtil.getOutputStream(applicationFile);
                IoUtil.copy(is, os);
                flag = true;
                is.close();
                os.close();
            }

            if (!FileUtil.exist(PathManager.USER_DIR + PathManager.TEXTURES_PATH)) {
                FileUtil.mkdir(PathManager.USER_DIR + PathManager.TEXTURES_PATH);
            }

            if (!FileUtil.exist(PathManager.USER_DIR + PathManager.KEY_PAIR_PATH)) {
                KeyPairUtil.generateKeyPair();
            }

            if (!FileUtil.exist(PathManager.USER_DIR + PathManager.JWT_KEY_PATH)) {
                JwtUtil.generateJwtSecret();
            }

            if (flag) {
                System.exit(0);
            }
        } catch (Exception e) {
            GlobalExceptionHandler.getExceptionInfo(e);
        }
    }
}
