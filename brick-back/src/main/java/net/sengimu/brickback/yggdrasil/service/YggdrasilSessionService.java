package net.sengimu.brickback.yggdrasil.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.benmanes.caffeine.cache.Cache;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import net.sengimu.brickback.po.Profile;
import net.sengimu.brickback.service.ProfileService;
import net.sengimu.brickback.yggdrasil.bo.YggdrasilProfileInfo;
import net.sengimu.brickback.yggdrasil.bo.YggdrasilToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Service
public class YggdrasilSessionService {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private YggdrasilProfileInfoService profileInfoService;

    @Autowired
    private HttpServletRequest request;

    @Resource(name = "tokenCache")
    private Cache<String, Object> tokenCache;

    @Resource(name = "serverIdCache")
    private Cache<String, Object> serverIdCache;

    public Boolean join(String accessToken, String selectedProfile, String serverId) {

        Object ifPresent = tokenCache.getIfPresent(accessToken);
        if (ifPresent == null) {
            return false;
        }

        YggdrasilToken yggdrasilToken = (YggdrasilToken) ifPresent;
        if (!yggdrasilToken.getUuid().equals(selectedProfile)) {
            return false;
        }

        serverIdCache.put(serverId, Map.of("accessToken", accessToken, "ip", request.getRemoteAddr()));
        return true;
    }

    public YggdrasilProfileInfo hasJoined(String username, String serverId, String ip) throws NoSuchAlgorithmException {

        JSONObject serverIdObj = JSONUtil.parseObj(serverIdCache.getIfPresent(serverId));
        String accessToken = serverIdObj.getStr("accessToken");
        YggdrasilToken yggdrasilToken = (YggdrasilToken) tokenCache.getIfPresent(accessToken);

        if (yggdrasilToken == null) {
            return null;
        }

        Profile profile = profileService.getProfileByUUID(yggdrasilToken.getUuid());
        if (!username.equals(profile.getName())) {
            return null;
        }

        return profileInfoService.getYggdrasilProfileInfoByProfile(profile);
    }
}
