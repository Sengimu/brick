package net.sengimu.brickback.common;

import net.sengimu.brickback.common.resbody.WebBody;
import net.sengimu.brickback.common.resbody.YggdrasilFailBody;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;

public class Res extends ResponseEntity<Object> {

    public Res(Object body, MultiValueMap<String, String> headers, int rawStatus) {
        super(body, headers, rawStatus);
    }

    // yggdrasil
    public static Res ySuccess(int httpStatus) {
        return ySuccess(null, httpStatus);
    }

    public static Res ySuccess(Object body) {
        return ySuccess(body, 200);
    }

    public static Res ySuccess(Object body, int httpStatus) {
        return yRes(body, httpStatus);
    }

    public static Res yFail(String error, String errorMessage, int httpStatus) {
        return yFail(error, errorMessage, null, httpStatus);
    }

    public static Res yFail(String error, String errorMessage, String cause, int status) {
        return yRes(new YggdrasilFailBody(error, errorMessage, cause), status);
    }

    private static Res yRes(Object body, int status) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new Res(body, headers, status);
    }

    // web
    public static Res wSuccess() {
        return wSuccess(null);
    }

    public static Res wSuccess(Object data) {
        return wSuccess("success", data);
    }

    public static Res wSuccess(String msg, Object data) {
        return wSuccess(msg, data, 200);
    }

    public static Res wSuccess(String msg, Object data, int status) {
        return wRes(1, msg, data, status);
    }

    public static Res wFail() {
        return wFail("fail");
    }

    public static Res wFail(String msg) {
        return wFail(-1, msg, 200);
    }

    public static Res wFail(int code, String msg, int status) {
        return wRes(code, msg, null, status);
    }

    private static Res wRes(int code, String msg, Object data, int status) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new Res(new WebBody(code, msg, data), headers, status);
    }
}
