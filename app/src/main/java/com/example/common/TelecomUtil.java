package com.example.common;

import android.content.Context;
import android.net.Uri;
import android.provider.CallLog;
import android.telecom.TelecomManager;

public class TelecomUtil {
    private static final String TAG = "TelecomUtil";
    public static Uri getCallLogUri() {
        return CallLog.Calls.CONTENT_URI;
    }

    private static TelecomManager getTelecomManager(Context context) {
        return (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
    }

    public static void cancelMissedCallsNotification(Context context) {
        if (PermissionsUtil.hasModifyPhoneStatePermission(context)) {
            try {
                getTelecomManager(context).cancelMissedCallsNotification();
            } catch (SecurityException e) {
                LogUtil.w(TAG, "TelecomManager.cancelMissedCalls called without permission.");
            }
        }
    }
}
