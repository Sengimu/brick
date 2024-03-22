package net.sengimu.brickback.yggdrasil.service;

import com.github.benmanes.caffeine.cache.Cache;
import jakarta.annotation.Resource;
import net.sengimu.brickback.po.Profile;
import net.sengimu.brickback.service.ProfileService;
import net.sengimu.brickback.yggdrasil.bo.ProfileInfo;
import net.sengimu.brickback.yggdrasil.bo.Token;
import net.sengimu.brickback.yggdrasil.bo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ProfileInfoService profileInfoService;

    @Autowired
    private ProfileService profileService;

    @Resource(name = "userHasTokenCache")
    private Cache<String, Object> userHasTokenCache;

    @Resource(name = "tokenCache")
    private Cache<String, Object> tokenCache;

    public void storeToken(Integer userId, String accessToken, Object token) {
        userHasTokenCache.put(String.valueOf(userId), accessToken);
        tokenCache.put(accessToken, token);
    }

    public Token validateToken(String accessToken) {

        Object value = tokenCache.getIfPresent(accessToken);
        return value != null ? (Token) value : null;
    }

    public Map<String, Object> refreshToken(Token oldToken) throws NoSuchAlgorithmException {

        String uuid = oldToken.getUuid();
        String clientToken = oldToken.getClientToken();

        tokenCache.invalidate(oldToken.getAccessToken());

        Profile profile = profileService.getProfileByUUID(uuid);
        Token newToken = new Token(profile.getUserId(), profile.getUuid(), clientToken);
        storeToken(profile.getUserId(), newToken.getAccessToken(), newToken);

        UserInfo userInfo = userInfoService.getUserInfoInfoById(profile.getUserId());
        ProfileInfo profileInfo = profileInfoService.getProfileInfoByProfile(profile);

        Map<String, Object> result = new HashMap<>();
        result.put("accessToken", newToken.getAccessToken());
        result.put("clientToken", newToken.getClientToken());
        result.put("selectedProfile", profileInfo);
        result.put("user", userInfo);

        return result;
    }

    public void invalidateToken(String accessToken) {
        tokenCache.invalidate(accessToken);
    }
}
