package net.sengimu.brickback.yggdrasil.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import jakarta.servlet.http.HttpServletRequest;
import net.sengimu.brickback.common.PathManager;
import net.sengimu.brickback.po.Profile;
import net.sengimu.brickback.po.Texture;
import net.sengimu.brickback.service.ProfileService;
import net.sengimu.brickback.service.TextureService;
import net.sengimu.brickback.yggdrasil.bo.ProfileInfo;
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
public class ProfileInfoService {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private TextureService textureService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private PathManager pathManager;

    public List<ProfileInfo> getProfileInfosByUserId(Integer userId) throws NoSuchAlgorithmException {

        String urlPrefix = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/textures/";

        List<Profile> profiles = profileService.getProfilesByUserId(userId);
        List<ProfileInfo> profileInfos = new ArrayList<>();

        for (Profile profile : profiles) {
            List<Texture> textures = textureService.getTexturesByProfileId(profile.getId());
            profileInfos.add(new ProfileInfo(profile, textures, urlPrefix));
        }

        return profileInfos;
    }

    public ProfileInfo getProfileInfoByProfile(Profile profile) throws NoSuchAlgorithmException {

        if (profile == null) {
            return null;
        }

        String urlPrefix = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/textures/";
        List<Texture> textures = textureService.getTexturesByProfileId(profile.getId());
        return new ProfileInfo(profile, textures, urlPrefix);
    }

    public ProfileInfo getProfileInfoByUUID(String uuid) throws NoSuchAlgorithmException {
        return getProfileInfoByProfile(profileService.getProfileByUUID(uuid));
    }

    public ProfileInfo getProfileInfoByName(String profileName) throws NoSuchAlgorithmException {
        return getProfileInfoByProfile(profileService.getProfileByName(profileName));
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

        File hashFile = FileUtil.touch(pathManager.getDirPath() + pathManager.getTexturesPath() + "/" + hashPath + ".png");
        FileOutputStream os = new FileOutputStream(hashFile);
        IoUtil.copy(file.getInputStream(), os);
        return true;
    }
}
