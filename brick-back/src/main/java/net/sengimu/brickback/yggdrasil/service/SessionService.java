package net.sengimu.brickback.yggdrasil.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.benmanes.caffeine.cache.Cache;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import net.sengimu.brickback.po.Profile;
import net.sengimu.brickback.service.ProfileService;
import net.sengimu.brickback.yggdrasil.bo.ProfileInfo;
import net.sengimu.brickback.yggdrasil.bo.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SessionService {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileInfoService profileInfoService;

    @Autowired
    private HttpServletRequest request;

    @Resource(name = "tokenCache")
    private Cache<String, Object> tokenCache;

    @Resource(name = "serverIdCache")
    private Cache<String, Object> serverIdCache;

    public Boolean join(String accessToken, String selectedProfile, String serverId) {

        Object ifPresent = tokenCache.getIfPresent(accessToken);
        if (null == ifPresent) {
            return false;
        }

        Token token = (Token) ifPresent;
        if (!token.getUuid().equals(selectedProfile)) {
            return false;
        }

        serverIdCache.put(serverId, Map.of("accessToken", accessToken, "ip", request.getRemoteAddr()));
        return true;
    }

    public ProfileInfo hasJoined(String username, String serverId, String ip) {

        JSONObject serverIdObj = JSONUtil.parseObj(serverIdCache.getIfPresent(serverId));
        String accessToken = serverIdObj.getStr("accessToken");
        Token token = (Token) tokenCache.getIfPresent(accessToken);

        if (token == null) {
            return null;
        }

        Profile profile = profileService.getProfileByUUID(token.getUuid());
        if (!username.equals(profile.getName())) {
            return null;
        }

        return profileInfoService.getProfileInfoByProfile(profile);
    }
}
