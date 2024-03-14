package net.sengimu.brickback.common;

import net.sengimu.brickback.common.resbody.WebBody;
import net.sengimu.brickback.common.resbody.YggdrasilFailBody;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;

public class Res extends ResponseEntity<Object> {

    public Res(Object body, MultiValueMap<String, String> headers, int rawStatus) {
        super(body, headers, rawStatus);
    }

    //yggdrasil
    public static Res ySuccess(Integer httpStatus) {
        return ySuccess(null, httpStatus);
    }

    public static Res ySuccess(Object body) {
        return ySuccess(body, 200);
    }

    public static Res ySuccess(Object body, Integer httpStatus) {
        return yRes(body, httpStatus);
    }

    public static Res yFail(String error, String errorMessage, Integer httpStatus) {
        return yFail(error, errorMessage, null, httpStatus);
    }

    public static Res yFail(String error, String errorMessage, String cause, Integer httpStatus) {
        return yRes(new YggdrasilFailBody(error, errorMessage, cause), httpStatus);
    }

    private static Res yRes(Object body, Integer httpStatus) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new Res(body, headers, httpStatus);
    }

    //web
    public static Res wSuccess() {
        return wSuccess("success");
    }

    public static Res wSuccess(String msg) {
        return wSuccess(msg, null);
    }

    public static Res wSuccess(Object data) {
        return wSuccess("success", data);
    }

    public static Res wSuccess(String msg, Object data) {
        return wRes(1, msg, data);
    }

    public static Res wFail() {
        return wFail("fail");
    }

    public static Res wFail(String msg) {
        return wRes(-1, msg, null);
    }

    private static Res wRes(Integer code, String msg, Object data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new Res(new WebBody(code, msg, data), headers, 200);
    }
}
