package com.ruani.authdagger.abstract_data;

public final class auth_data {
    public static enum AuthValue {
        COMPLETE,
        ERROR_USERDATA,
        ERROR_CONNECTION,
        ERROR
    }
    public static enum AuthAction {
        SIGNIN,
        REGISTER,
        RESTORE
    }
    public static enum FingerValue {
        FINGER_ERROR,
        FINGER_COMPLETE
    }
}
