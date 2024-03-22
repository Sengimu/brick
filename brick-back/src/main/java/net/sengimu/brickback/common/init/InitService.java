package net.sengimu.brickback.common.init;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import lombok.extern.slf4j.Slf4j;
import net.sengimu.brickback.common.PathManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;

@Slf4j
@Service
public class InitService {

    @Autowired
    private InitMapper initMapper;

    @Autowired
    private PathManager pathManager;

    @Transactional
    public void initData() throws IOException {

        if (initMapper.initBeforeCheckSelectUser() != 0 || initMapper.initBeforeCheckSelectProfile() != 0) {
            return;
        }

        initMapper.initUser();
        initMapper.initUserProperty();
        initMapper.initProfile();
        initMapper.initTexture();

        String testTexturePath = "/a69e403fb50f46278bdfab3571475f43787354e01f86f52221f64974f6cc5c2e.png";
        File testTextureFile = FileUtil.touch(pathManager.getDirPath() + pathManager.getTexturesPath() + testTexturePath);

        InputStream is = new ClassPathResource("default" + testTexturePath).getInputStream();
        FileOutputStream os = new FileOutputStream(testTextureFile);
        IoUtil.copy(is, os);
        is.close();
        os.close();
    }
}