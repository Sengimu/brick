package net.sengimu.brickback.common.init;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.sengimu.brickback.common.PathManager;
import net.sengimu.brickback.exception.GlobalExceptionHandler;
import net.sengimu.brickback.utils.JwtKeyUtil;
import net.sengimu.brickback.utils.KeyPairUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@Slf4j
@Service
public class Init {

    @Autowired
    private PathManager pathManager;

    @Autowired
    private InitService initService;

    @PostConstruct
    private void init()  {

        Boolean flag = false;

        try {
            initService.initData();

            if (!FileUtil.exist(pathManager.getDirPath() + pathManager.getApplicationPath())) {

                File applicationFile = FileUtil.touch(pathManager.getDirPath() + pathManager.getApplicationPath());
                InputStream is = new ClassPathResource("default" + pathManager.getApplicationPath()).getInputStream();
                FileOutputStream os = new FileOutputStream(applicationFile);
                IoUtil.copy(is, os);
                flag = true;
                is.close();
                os.close();
            }

            if (!FileUtil.exist(pathManager.getDirPath() + pathManager.getTexturesPath())) {
                FileUtil.mkdir(pathManager.getDirPath() + pathManager.getTexturesPath());
            }

            if (!FileUtil.exist(pathManager.getDirPath() + pathManager.getKeyPairPath())) {
                KeyPairUtil.generateKeyPair();
            }

            if (!FileUtil.exist(pathManager.getDirPath() + pathManager.getJwtKeyPath())) {
                JwtKeyUtil.generateJwtKey();
            }

            if (flag) {
                System.exit(0);
            }
        } catch (Exception e) {
            log.error(GlobalExceptionHandler.getExceptionInfo(e));
        }
    }
}
