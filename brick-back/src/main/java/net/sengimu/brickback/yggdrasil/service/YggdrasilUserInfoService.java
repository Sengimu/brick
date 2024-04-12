package net.sengimu.brickback.yggdrasil.service;

import com.github.benmanes.caffeine.cache.Cache;
import jakarta.annotation.Resource;
import net.sengimu.brickback.po.User;
import net.sengimu.brickback.po.UserProperty;
import net.sengimu.brickback.service.UserPropertyService;
import net.sengimu.brickback.service.UserService;
import net.sengimu.brickback.yggdrasil.bo.YggdrasilUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YggdrasilUserInfoService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserPropertyService userPropertyService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Resource(name = "loginRateCache")
    private Cache<String, Object> loginRateCache;

    @Resource(name = "userHasTokenCache")
    private Cache<String, Object> userHasTokenCache;

    public YggdrasilUserInfo getYggdrasilUserInfoByUsernameAndPassword(String username, String password) {

        loginRateCache.put(username, "1");

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        User user = (User) authentication.getPrincipal();

        if (user == null) {
            return null;
        }

        List<UserProperty> userProperties = userPropertyService.getUserPropertiesByUserId(user.getId());

        return new YggdrasilUserInfo(user, userProperties);
    }

    public Boolean checkLoginRate(String username) {
        return !(loginRateCache.getIfPresent(username) == null);
    }

    public YggdrasilUserInfo getYggdrasilUserInfoInfoById(int id) {

        User user = userService.getUserById(id);
        List<UserProperty> userProperties = userPropertyService.getUserPropertiesByUserId(user.getId());
        return new YggdrasilUserInfo(user, userProperties);
    }

    public Boolean signOut(String username, String password) {

        loginRateCache.put(username, "1");

        User user = userService.getUserByUsernameAndPassword(username, password);
        if (user == null) {
            return false;
        }

        userHasTokenCache.invalidate(String.valueOf(user.getId()));
        return true;
    }
}
