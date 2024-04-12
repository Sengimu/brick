package net.sengimu.brickback.common.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class InitService {

    @Autowired
    private InitMapper initMapper;

    @Transactional
    public void initData() {

        if (initMapper.initBeforeCheckSelectUser() != 0 || initMapper.initBeforeCheckSelectProfile() != 0) {
            return;
        }

        initMapper.initUser();
        initMapper.initUserProperty();
        initMapper.initProfile();
        initMapper.initTexture();
    }
}
