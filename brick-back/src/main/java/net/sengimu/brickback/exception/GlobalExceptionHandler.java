package net.sengimu.brickback.exception;

import cn.hutool.core.io.FileUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.sengimu.brickback.common.Res;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoResourceFoundException.class})
    public Object handlerNoResourceFound(NoResourceFoundException e) {
        if (FileUtil.exist("static/index.html")) {
            return "/index.html";
        } else {
            return Res.wFail(-202, "Not found.", 404);
        }
    }

    @ResponseBody
    @ExceptionHandler({InternalAuthenticationServiceException.class, BadCredentialsException.class})
    public Res handlerAuthentication(Exception e, HttpServletRequest request) {
        if (request.getServletPath().startsWith("/api/yggdrasil")) {
            return Res.yFail("ForbiddenOperationException", "Invalid credentials. Invalid username or password.", "User is not exist or the wrong password.", 403);
        } else {
            return Res.wFail(-103, "User is not exist or Invalid username or password.", 200);
        }
    }

    @ResponseBody
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Res handlerRequestMethodNotSupported(Exception e) {
        return Res.wFail(-201, "Request method is not supported.", 405);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Res defaultHandler(Exception e) {
        getExceptionInfo(e);
        return Res.wFail("An internal exception occurred in the server, please contact the administrator to handle it.");
    }

    public static void getExceptionInfo(Exception e) {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        e.printStackTrace(ps);
        log.error(os.toString());
        try {
            ps.close();
            os.close();
        } catch (IOException ex) {
            log.warn("Global exception get stream close error.");
        }
    }
}
