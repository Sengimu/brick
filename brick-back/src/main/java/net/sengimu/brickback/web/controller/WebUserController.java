package net.sengimu.brickback.web.controller;

import net.sengimu.brickback.common.Req;
import net.sengimu.brickback.common.Res;
import net.sengimu.brickback.web.service.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/web")
public class WebUserController {

    @Autowired
    private WebUserService webUserService;

    @PostMapping("/login")
    public Res login(@RequestBody Req params) {
        System.out.println(params);
        String email = params.getStr("email");
        String password = params.getStr("password");
        return Res.wSuccess(webUserService.login(email, password));
    }
}
