package net.sengimu.brickback.yggdrasil.controller;

import cn.hutool.core.io.FileUtil;
import net.sengimu.brickback.common.PathManager;
import net.sengimu.brickback.common.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OtherController {

    @Autowired
    private PathManager pathManager;

    @GetMapping(value = "/textures/{hash}")
    public Res getTexture(@PathVariable String hash) {

        if (hash.endsWith(".png")) {
            hash = hash.substring(0, hash.lastIndexOf("."));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        byte[] bytes = FileUtil.readBytes(pathManager.getDirPath() + pathManager.getTexturesPath() + "/" + hash + ".png");
        return new Res(bytes, headers, 200);
    }
}