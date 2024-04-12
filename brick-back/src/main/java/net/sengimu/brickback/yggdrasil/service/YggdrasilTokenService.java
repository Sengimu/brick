package net.sengimu.brickback.yggdrasil.service;

import com.github.benmanes.caffeine.cache.Cache;
import jakarta.annotation.Resource;
import net.sengimu.brickback.po.Profile;
import net.sengimu.brickback.service.ProfileService;
import net.sengimu.brickback.yggdrasil.bo.YggdrasilProfileInfo;
import net.sengimu.brickback.yggdrasil.bo.YggdrasilToken;
import net.sengimu.brickback.yggdrasil.bo.YggdrasilUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service
public class YggdrasilTokenService {

    @Autowired
    private YggdrasilUserInfoService userInfoService;

    @Autowired
    private YggdrasilProfileInfoService profileInfoService;

    @Autowired
    private ProfileService profileService;

    @Resource(name = "userHasTokenCache")
    private Cache<String, Object> userHasTokenCache;

    @Resource(name = "tokenCache")
    private Cache<String, Object> tokenCache;

    public void storeYggdrasilToken(int userId, String accessToken, Object token) {
        userHasTokenCache.put(String.valueOf(userId), accessToken);
        tokenCache.put(accessToken, token);
    }

    public YggdrasilToken validateYggdrasilToken(String accessToken) {

        Object value = tokenCache.getIfPresent(accessToken);
        return value != null ? (YggdrasilToken) value : null;
    }

    public Map<String, Object> refreshYggdrasilToken(YggdrasilToken oldYggdrasilToken) throws NoSuchAlgorithmException {

        String uuid = oldYggdrasilToken.getUuid();
        String clientToken = oldYggdrasilToken.getClientToken();

        tokenCache.invalidate(oldYggdrasilToken.getAccessToken());

        Profile profile = profileService.getProfileByUUID(uuid);
        YggdrasilToken newYggdrasilToken = new YggdrasilToken(profile.getUserId(), profile.getUuid(), clientToken);
        storeYggdrasilToken(profile.getUserId(), newYggdrasilToken.getAccessToken(), newYggdrasilToken);

        YggdrasilUserInfo userInfo = userInfoService.getYggdrasilUserInfoInfoById(profile.getUserId());
        YggdrasilProfileInfo profileInfo = profileInfoService.getYggdrasilProfileInfoByProfile(profile);

        Map<String, Object> result = new HashMap<>();
        result.put("accessToken", newYggdrasilToken.getAccessToken());
        result.put("clientToken", newYggdrasilToken.getClientToken());
        result.put("selectedProfile", profileInfo);
        result.put("user", userInfo);

        return result;
    }

    public void invalidateYggdrasilToken(String accessToken) {
        tokenCache.invalidate(accessToken);
    }
}
