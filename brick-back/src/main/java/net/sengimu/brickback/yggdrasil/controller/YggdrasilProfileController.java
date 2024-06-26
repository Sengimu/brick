package net.sengimu.brickback.yggdrasil.controller;

import cn.hutool.crypto.digest.DigestUtil;
import net.sengimu.brickback.common.Res;
import net.sengimu.brickback.yggdrasil.bo.YggdrasilProfileInfo;
import net.sengimu.brickback.yggdrasil.service.YggdrasilProfileInfoService;
import net.sengimu.brickback.yggdrasil.service.YggdrasilTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/yggdrasil")
public class YggdrasilProfileController {

    @Autowired
    private YggdrasilProfileInfoService yggdrasilProfileInfoService;

    @Autowired
    private YggdrasilTokenService yggdrasilTokenService;

    @GetMapping("/sessionserver/session/minecraft/profile/{uuid}")
    public Res getYggdrasilProfileInfoByUUID(@PathVariable("uuid") String uuid, @RequestParam(name = "unsigned", required = false) String unsigned) throws NoSuchAlgorithmException {

        YggdrasilProfileInfo profileInfo = yggdrasilProfileInfoService.getYggdrasilProfileInfoByUUID(uuid);
        return profileInfo != null ? Res.ySuccess(profileInfo) : Res.ySuccess(204);
    }

    @PostMapping("/api/profiles/minecraft")
    public Res getYggdrasilProfileInfosByName(@RequestBody List<String> params) throws NoSuchAlgorithmException {

        if (params.isEmpty() || params.size() > 5) {
            return Res.yFail("ForbiddenOperationException", "Params length not allowed.", 403);
        }

        List<YggdrasilProfileInfo> profileInfos = new ArrayList<>();
        for (String profileName : params) {
            profileInfos.add(yggdrasilProfileInfoService.getYggdrasilProfileInfoByName(profileName));
        }

        return Res.ySuccess(profileInfos);
    }

    @PutMapping("/api/user/profile/{uuid}/{textureType}")
    public Res putTextureByUUID(@PathVariable("uuid") String uuid, @PathVariable("textureType") String textureType, @RequestParam("model") String model, @RequestParam("file") MultipartFile file, @RequestHeader(name = "Authorization", required = false) String authorization) throws IOException {

        if (authorization == null || yggdrasilTokenService.validateYggdrasilToken(authorization.split(" ")[1]) == null) {
            return Res.yFail("UnauthorizedException", "Authorization is not exist.", 401);
        }

        if (!"image/png".equals(file.getContentType())) {
            return Res.yFail("ForbiddenOperationException", "File Content-Type must be image/png.", 403);
        }

        if (file.getSize() > 1024 * 100) {
            return Res.yFail("ForbiddenOperationException", "The file is illegal.", 403);
        }

        ImageInputStream iis = ImageIO.createImageInputStream(file.getInputStream());
        Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
        ImageReader reader = readers.next();

        reader.setInput(iis, true);
        int width = reader.getWidth(0);
        int height = reader.getHeight(0);
        reader.dispose();

        if (width % 32 != 0 || width > 512 || height % 32 != 0 || height > 512) {
            return Res.yFail("ForbiddenOperationException", "The file is illegal.", 403);
        }

        model = "slim".equals(model) ? "slim" : "default";

        String hashPath = DigestUtil.sha256Hex(file.getBytes());

        return yggdrasilProfileInfoService.putTextureByUUID(uuid, textureType, model, hashPath, file) ? Res.ySuccess(204) : Res.yFail("ForbiddenOperationException", "The operation occur exception.", 403);
    }

    @DeleteMapping("/api/user/profile/{uuid}/{textureType}")
    public Res deleteTextureByUUID(@PathVariable("uuid") String uuid, @PathVariable("textureType") String textureType, @RequestHeader(name = "Authorization", required = false) String authorization) {

        if (authorization == null || yggdrasilTokenService.validateYggdrasilToken(authorization.split(" ")[1]) == null) {
            return Res.yFail("UnauthorizedException", "Authorization is not exist.", 401);
        }

        yggdrasilProfileInfoService.deleteTextureByUUID(uuid, textureType);
        return Res.ySuccess(204);
    }
}
