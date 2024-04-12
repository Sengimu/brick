package net.sengimu.brickback.web.service;

import net.sengimu.brickback.po.User;
import net.sengimu.brickback.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebUserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    public String login(String email, String password) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        User user = (User) authentication.getPrincipal();

        Map<String, Object> payloads = new HashMap<>();
        payloads.put("username", user.getUsername());
        payloads.put("email", user.getEmail());
        payloads.put("role", user.getRole());
        payloads.put("disabled", user.getDisabled());
        return JwtUtil.createJwt(payloads);
    }
}
