package net.sengimu.brickback.yggdrasil.controller;

import jakarta.servlet.http.HttpServletRequest;
import net.sengimu.brickback.common.Res;
import net.sengimu.brickback.utils.KeyPairUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/yggdrasil")
public class ExtendController {

    @Autowired
    private HttpServletRequest request;

    @Value("${server-info.serverName}")
    private String serverName;

    @Value("${server-info.implementationName}")
    private String implementationName;

    @Value("${server-info.implementationVersion}")
    private String implementationVersion;

    @GetMapping
    public Res mainInfo() {

        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String url2 = request.getServerName();

        Map<String, Object> result = new HashMap<>();

        Map<String, Object> meta = new HashMap<>();
        meta.put("serverName", serverName);
        meta.put("implementationName", implementationName);
        meta.put("implementationVersion", implementationVersion);
        meta.put("links", Map.of("homepage", url, "register", url + "/register"));

        result.put("meta", meta);
        result.put("skinDomains", List.of(url, url2));
        result.put("signaturePublickey", "-----BEGIN PUBLIC KEY-----\n" +
                KeyPairUtil.getPublicKeyBase64() +
                "\n-----END PUBLIC KEY-----\n");

        return Res.ySuccess(result);
    }

    @GetMapping("/")
    public Res mainInfoWithSlash() {
        return mainInfo();
    }
}
