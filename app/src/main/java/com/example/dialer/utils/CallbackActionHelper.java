package com.example.dialer.utils;

import android.provider.CallLog.Calls;
import android.support.annotation.IntDef;
import android.text.TextUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CallbackActionHelper {
    /**
     * Specifies the action a user can take to make a callback.
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({CallbackAction.NONE, CallbackAction.IMS_VIDEO, CallbackAction.DUO, CallbackAction.VOICE})
    public @interface CallbackAction {
        int NONE = 0;
        int IMS_VIDEO = 1;
        int DUO = 2;
        int VOICE = 3;
    }

    /**
     * Returns the {@link CallbackAction} that can be associated with a call.
     *
     * @param number The phone number in column {@link android.provider.CallLog.Calls#NUMBER}.
     * @param features Value of features in column {@link android.provider.CallLog.Calls#FEATURES}.
     * @param phoneAccountComponentName Account name in column {@link
     *     android.provider.CallLog.Calls#PHONE_ACCOUNT_COMPONENT_NAME}.
     * @return One of the values in {@link CallbackAction}
     */
    public static @CallbackAction int getCallbackAction(
            String number, int features, String phoneAccountComponentName) {
        return getCallbackAction(number, features);
    }

    /**
     * Returns the {@link CallbackAction} that can be associated with a call.
     *
     * @param number The phone number in column {@link android.provider.CallLog.Calls#NUMBER}.
     * @param features Value of features in column {@link android.provider.CallLog.Calls#FEATURES}.
     * @return One of the values in {@link CallbackAction}
     */
    public static @CallbackAction int getCallbackAction(
            String number, int features) {
        if (TextUtils.isEmpty(number)) {
            return CallbackAction.NONE;
        }

        boolean isVideoCall = (features & Calls.FEATURES_VIDEO) == Calls.FEATURES_VIDEO;
        if (isVideoCall) {
            return CallbackAction.IMS_VIDEO;
        }

        return CallbackAction.VOICE;
    }


}
