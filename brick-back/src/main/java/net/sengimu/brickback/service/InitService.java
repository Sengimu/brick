package net.sengimu.brickback.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.sengimu.brickback.common.PathManager;
import net.sengimu.brickback.mapper.InitMapper;
import net.sengimu.brickback.utils.JwtKeyUtil;
import net.sengimu.brickback.utils.KeyPairUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@Slf4j
@Service
public class InitService {

    @Autowired
    private InitMapper initMapper;

    @Autowired
    private PathManager pathManager;

    @Autowired
    private PlatformTransactionManager manager;

    @PostConstruct
    private void initConfig() {

        Boolean flag = false;

        if (!FileUtil.exist(pathManager.getDirPath() + pathManager.getApplicationPath())) {

            File applicationFile = FileUtil.touch(pathManager.getDirPath() + pathManager.getApplicationPath());
            try {
                InputStream is = new ClassPathResource("default" + pathManager.getApplicationPath()).getInputStream();
                FileOutputStream os = new FileOutputStream(applicationFile);
                IoUtil.copy(is, os);
                flag = true;
                is.close();
                os.close();
            } catch (Exception e) {
                log.error("Target path is not exist!");
            }
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
    }

    @PostConstruct
    private void initData() {

        if (initMapper.initBeforeCheckSelectUser() != 0 || initMapper.initBeforeCheckSelectProfile() != 0) {
            return;
        }

        TransactionTemplate template = new TransactionTemplate(manager);

        try {
            template.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {

                    initMapper.initUser();
                    initMapper.initUserProperty();
                    initMapper.initProfile();
                    initMapper.initTexture();
                    try {
                        String testTexturePath = "/a69e403fb50f46278bdfab3571475f43787354e01f86f52221f64974f6cc5c2e.png";
                        File testTextureFile = FileUtil.touch(pathManager.getDirPath() + pathManager.getTexturesPath() + testTexturePath);

                        InputStream is = new ClassPathResource("default" + testTexturePath).getInputStream();
                        FileOutputStream os = new FileOutputStream(testTextureFile);
                        IoUtil.copy(is, os);
                        is.close();
                        os.close();
                    } catch (Exception e) {
                        log.error("Target path is not exist!");
                    }
                }
            });
        } catch (Exception e) {
            log.error("Init error! Please delete the conflict data.");
        }
    }
}
