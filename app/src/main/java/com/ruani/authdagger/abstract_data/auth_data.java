package com.ruani.authdagger.abstract_data;

public final class auth_data {
    public static enum AuthValue {
        COMPLETE_SIGN,
        COMPLETE_REGISTER,
        COMPLETE_RESTORE,
        ERROR_CONNECTION,
        ERROR_ALREADY_EMAIL,
        ERROR_USER_DATA,
        ERROR_AUTH_SERVICE,
        ERROR_RESTORE
    }
    public static enum AuthAction {
        SIGNIN,
        REGISTER,
        RESTORE
    }
    public static enum AuthButton {
        BUTTON_REGISTER,
        BUTTON_RESTORE,
        BUTTON_FINGER
    }
}
