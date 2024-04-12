package net.sengimu.brickback.yggdrasil.controller;

import net.sengimu.brickback.common.Req;
import net.sengimu.brickback.common.Res;
import net.sengimu.brickback.yggdrasil.bo.YggdrasilProfileInfo;
import net.sengimu.brickback.yggdrasil.service.YggdrasilSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/yggdrasil")
public class YggdrasilSessionController {

    @Autowired
    private YggdrasilSessionService yggdrasilSessionService;

    @PostMapping("/sessionserver/session/minecraft/join")
    public Res join(@RequestBody Req params) {

        String accessToken = params.getStr("accessToken");
        String selectedProfile = params.getStr("selectedProfile");
        String serverId = params.getStr("serverId");

        return yggdrasilSessionService.join(accessToken, selectedProfile, serverId) ? Res.ySuccess(204) : Res.yFail("ForbiddenOperationException", "Invalid token.", 403);
    }

    @GetMapping("/sessionserver/session/minecraft/hasJoined")
    public Res hasJoined(@RequestParam("username") String username, @RequestParam("serverId") String serverId, @RequestParam(name = "ip", required = false) String ip) throws NoSuchAlgorithmException {

        YggdrasilProfileInfo profileInfo = yggdrasilSessionService.hasJoined(username, serverId, ip);
        return profileInfo != null ? Res.ySuccess(profileInfo) : Res.ySuccess(204);
    }
}
