package net.sengimu.brickback.yggdrasil.controller;

import cn.hutool.json.JSONUtil;
import net.sengimu.brickback.common.Req;
import net.sengimu.brickback.common.Res;
import net.sengimu.brickback.yggdrasil.bo.ProfileInfo;
import net.sengimu.brickback.yggdrasil.bo.Token;
import net.sengimu.brickback.yggdrasil.bo.UserInfo;
import net.sengimu.brickback.yggdrasil.service.ProfileInfoService;
import net.sengimu.brickback.yggdrasil.service.TokenService;
import net.sengimu.brickback.yggdrasil.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/yggdrasil")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ProfileInfoService profileInfoService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/authserver/authenticate")
    public Res authenticate(@RequestBody Req params) throws NoSuchAlgorithmException {

        String username = params.getStr("username");
        String password = params.getStr("password");
        String clientToken = params.getStr("clientToken");
        Boolean requestUser = params.getBool("requestUser");
        Map<String, Object> result = new HashMap<>();

        if (userInfoService.checkLoginRate(username)) {
            return Res.yFail("ForbiddenOperationException", "Invalid credentials. Invalid username or password.", "Frequent login.", 403);
        }

        UserInfo userInfo = userInfoService.getUserInfoByUsernameAndPassword(username, password);
        if (userInfo == null) {
            return Res.yFail("ForbiddenOperationException", "Invalid credentials. Invalid username or password.", "User is not exist or the wrong password.", 403);
        }
        if (Optional.ofNullable(requestUser).orElse(false)) {
            result.put("user", userInfo);
        }

        List<ProfileInfo> profileInfos = profileInfoService.getProfileInfosByUserId(Integer.parseInt(userInfo.getId()));
        if (profileInfos.isEmpty()) {
            return Res.yFail("ForbiddenOperationException", "Invalid credentials. Invalid username or password.", "User has not profile.", 403);
        }
        result.put("selectedProfile", profileInfos.get(0));
        result.put("availableProfiles", profileInfos);

        Token token = new Token(Integer.parseInt(userInfo.getId()), profileInfos.get(0).getId());
        tokenService.storeToken(Integer.parseInt(userInfo.getId()), token.getAccessToken(), token);
        if (null == clientToken) {
            result.put("clientToken", token.getClientToken());
        } else {
            result.put("clientToken", clientToken);
        }
        result.put("accessToken", token.getAccessToken());

        return Res.ySuccess(result);
    }

    @PostMapping("/authserver/refresh")
    public Res refresh(@RequestBody Req params) throws NoSuchAlgorithmException {

        String accessToken = params.getStr("accessToken");
        String selectedProfile = params.getStr("selectedProfile");

        Token oldToken = tokenService.validateToken(accessToken);
        if (oldToken == null) {
            return Res.yFail("ForbiddenOperationException", "Invalid token.", 403);
        }

        if (selectedProfile != null) {
            oldToken.setUuid(JSONUtil.parseObj(selectedProfile).getStr("id"));
        }

        return Res.ySuccess(tokenService.refreshToken(oldToken));
    }

    @PostMapping("/authserver/validate")
    public Res validate(@RequestBody Req params) {

        String accessToken = params.getStr("accessToken");

        return tokenService.validateToken(accessToken) != null ? Res.ySuccess(204) : Res.yFail("ForbiddenOperationException", "Invalid token.", 403);
    }

    @PostMapping("/authserver/invalidate")
    public Res invalidate(@RequestBody Req params) {

        String accessToken = params.getStr("accessToken");

        tokenService.invalidateToken(accessToken);
        return Res.ySuccess(204);
    }

    @PostMapping("/authserver/signout")
    public Res signOut(@RequestBody Req params) {

        String username = params.getStr("username");
        String password = params.getStr("password");

        if (userInfoService.checkLoginRate(username)) {
            return Res.yFail("ForbiddenOperationException", "Invalid credentials. Invalid username or password.", "Frequent sign out.", 403);
        }

        return userInfoService.signOut(username, password) ? Res.ySuccess(204) : Res.yFail("ForbiddenOperationException", "Invalid credentials. Invalid username or password.", 403);
    }
}
