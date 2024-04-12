package net.sengimu.brickback.yggdrasil.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import net.sengimu.brickback.common.PathManager;
import net.sengimu.brickback.common.Res;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class YggdrasilOtherController {

    @GetMapping(value = "/textures/{hash}")
    public Res getTexture(@PathVariable String hash) {

        hash = !hash.endsWith(".png") ? hash + ".png" : hash;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        byte[] bytes;
        try {
            bytes = FileUtil.readBytes(PathManager.USER_DIR + PathManager.TEXTURES_PATH + "/" + hash);
        } catch (IORuntimeException e) {
            bytes = FileUtil.readBytes("default/defaultSkin.png");
        }

        return new Res(bytes, headers, 200);
    }
}
