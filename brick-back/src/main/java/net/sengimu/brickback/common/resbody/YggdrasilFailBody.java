package net.sengimu.brickback.common.resbody;

import java.util.HashMap;

public class YggdrasilFailBody extends HashMap<String, Object> {

    public static final String ERROR_TAG = "error";
    public static final String ERROR_MESSAGE_TAG = "errorMessage";
    public static final String CAUSE_TAG = "cause";

    public YggdrasilFailBody(String error, String errorMessage) {
        this(error, errorMessage, null);
    }

    public YggdrasilFailBody(String error, String errorMessage, String cause) {
        super.put(ERROR_TAG, error);
        super.put(ERROR_MESSAGE_TAG, errorMessage);
        super.put(CAUSE_TAG, cause);
    }
}
