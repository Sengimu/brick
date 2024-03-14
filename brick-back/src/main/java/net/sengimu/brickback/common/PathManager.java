package net.sengimu.brickback.common;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PathManager {

    private String dirPath;
    private String applicationPath;
    private String keyPairPath;
    private String jwtKeyPath;
    private String texturesPath;

    public PathManager() {
        this.dirPath = System.getProperty("user.dir");
        this.applicationPath = "/config/application.yml";
        this.keyPairPath = "/config/key-pair.setting";
        this.jwtKeyPath = "/config/jwt-key.setting";
        this.texturesPath = "/textures";
    }
}
