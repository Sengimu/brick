package net.sengimu.brickback.yggdrasil.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import jakarta.servlet.http.HttpServletRequest;
import net.sengimu.brickback.common.PathManager;
import net.sengimu.brickback.po.Profile;
import net.sengimu.brickback.po.Texture;
import net.sengimu.brickback.service.ProfileService;
import net.sengimu.brickback.service.TextureService;
import net.sengimu.brickback.yggdrasil.bo.YggdrasilProfileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class YggdrasilProfileInfoService {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private TextureService textureService;

    @Autowired
    private HttpServletRequest request;

    public List<YggdrasilProfileInfo> getYggdrasilProfileInfosByUserId(int userId) throws NoSuchAlgorithmException {

        String urlPrefix = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/textures/";

        List<Profile> profiles = profileService.getProfilesByUserId(userId);
        List<YggdrasilProfileInfo> yggdrasilProfileInfos = new ArrayList<>();

        for (Profile profile : profiles) {
            List<Texture> textures = textureService.getTexturesByProfileId(profile.getId());
            yggdrasilProfileInfos.add(new YggdrasilProfileInfo(profile, textures, urlPrefix));
        }

        return yggdrasilProfileInfos;
    }

    public YggdrasilProfileInfo getYggdrasilProfileInfoByProfile(Profile profile) throws NoSuchAlgorithmException {

        if (profile == null) {
            return null;
        }

        String urlPrefix = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/textures/";
        List<Texture> textures = textureService.getTexturesByProfileId(profile.getId());
        return new YggdrasilProfileInfo(profile, textures, urlPrefix);
    }

    public YggdrasilProfileInfo getYggdrasilProfileInfoByUUID(String uuid) throws NoSuchAlgorithmException {
        return getYggdrasilProfileInfoByProfile(profileService.getProfileByUUID(uuid));
    }

    public YggdrasilProfileInfo getYggdrasilProfileInfoByName(String profileName) throws NoSuchAlgorithmException {
        return getYggdrasilProfileInfoByProfile(profileService.getProfileByName(profileName));
    }

    public void deleteTextureByUUID(String uuid, String textureType) {

        Profile profile = profileService.getProfileByUUID(uuid);
        textureService.deleteTextureByProfileId(profile.getId(), textureType);
    }

    public Boolean putTextureByUUID(String uuid, String textureType, String model, String hashPath, MultipartFile file) throws IOException {

        Profile profile = profileService.getProfileByUUID(uuid);
        if (!profile.getAllowUpload().contains(textureType)) {
            return false;
        }

        Texture texture = new Texture(null, textureType.toUpperCase(), hashPath, model, profile.getId());
        if (textureService.putTextureByProfileId(texture) < 0) {
            return false;
        }

        File hashFile = FileUtil.touch(PathManager.USER_DIR + PathManager.TEXTURES_PATH + "/" + hashPath + ".png");
        FileOutputStream os = new FileOutputStream(hashFile);
        IoUtil.copy(file.getInputStream(), os);
        return true;
    }
}
