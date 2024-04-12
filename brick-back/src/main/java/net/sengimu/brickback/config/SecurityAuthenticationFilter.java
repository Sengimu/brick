package net.sengimu.brickback.config;

import cn.hutool.json.JSONObject;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sengimu.brickback.po.User;
import net.sengimu.brickback.utils.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        if (token == null) {
            doFilter(request, response, filterChain);
            return;
        }

        token = token.substring("Bearer ".length()).trim();
        JSONObject jsonObject = JwtUtil.parseJwt(token);
        if (jsonObject == null) {
            doFilter(request, response, filterChain);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = new User();
            user.setUsername(jsonObject.getStr("username"));
            user.setEmail(jsonObject.getStr("email"));
            user.setRole(jsonObject.getStr("role"));
            user.setDisabled(jsonObject.getInt("disabled"));

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        doFilter(request, response, filterChain);
    }
}
