package net.sengimu.brickback.exception;

import lombok.extern.slf4j.Slf4j;
import net.sengimu.brickback.common.Res;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Res handler(Exception e) {
        log.error(getExceptionInfo(e));
        return Res.wFail("An internal exception occurred in the server, please contact the administrator to handle it.");
    }

    public static String getExceptionInfo(Exception e) {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        e.printStackTrace(ps);
        String info = os.toString();
        try {
            ps.close();
            os.close();
        } catch (IOException ex) {
            log.warn("Global exception get stream close error.");
        }
        return info;
    }
}
