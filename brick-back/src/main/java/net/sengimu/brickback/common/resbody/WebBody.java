package net.sengimu.brickback.common.resbody;

import java.util.HashMap;

public class WebBody extends HashMap<String, Object> {

    public static final String CODE_TAG = "code";
    public static final String MSG_TAG = "msg";
    public static final String DATA_TAG = "data";

    public WebBody(Integer code, String msg) {
        this(code, msg, null);
    }

    public WebBody(Integer code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        super.put(DATA_TAG, data);
    }
}
