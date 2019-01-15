package com.example.common;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.MODIFY_PHONE_STATE;
import static android.Manifest.permission.READ_CALL_LOG;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_PHONE_STATE;

import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.WRITE_CALL_LOG;
import static android.Manifest.permission.WRITE_CONTACTS;


import android.Manifest.permission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PermissionsUtil {
    private static final String PERMISSION_PREFERENCE = "dialer_permissions";
    public static final List<String> phoneGroupPermissions =
            Collections.unmodifiableList(
                    Arrays.asList(
                            READ_CALL_LOG,
                            WRITE_CALL_LOG,
                            READ_PHONE_STATE,
                            MODIFY_PHONE_STATE,
                            SEND_SMS,
                            CALL_PHONE));

    public static final List<String> contactsGroupPermissions =
            Collections.unmodifiableList(Arrays.asList(READ_CONTACTS, WRITE_CONTACTS));

    public static final List<String> locationGroupPermissions =
            Collections.unmodifiableList(Arrays.asList(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION));

    public static boolean hasPhonePermissions(Context context) {
        return hasPermission(context, permission.CALL_PHONE);
    }

    public static boolean hasPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasReadPhoneStatePermissions(Context context) {
        return hasPermission(context, permission.READ_PHONE_STATE);
    }
    public static boolean hasModifyPhoneStatePermission(Context context) {
        return hasPermission(context, permission.MODIFY_PHONE_STATE);
    }

    public static boolean hasContactsReadPermissions(Context context) {
        return hasPermission(context, permission.READ_CONTACTS);
    }

    public static boolean hasLocationPermissions(Context context) {
        return hasPermission(context, permission.ACCESS_FINE_LOCATION);
    }

    public static boolean hasCameraPermissions(Context context) {
        return hasPermission(context, permission.CAMERA);
    }

    public static boolean hasCallLogReadPermissions(Context context) {
        return hasPermission(context, permission.READ_CALL_LOG);
    }

    public static boolean hasCallLogWritePermissions(Context context) {
        return hasPermission(context, permission.WRITE_CALL_LOG);
    }

    /**
     * Returns a list of permissions currently not granted to the application from the supplied list.
     *
     * @param context         - The Application context.
     * @param permissionsList - A list of permissions to check if the current application has been
     *                        granted.
     * @return An array of permissions that are currently DENIED to the application; a subset of
     * permissionsList.
     */
    @NonNull
    public static String[] getPermissionsCurrentlyDenied(
            @NonNull Context context, @NonNull List<String> permissionsList) {
        List<String> permissionsCurrentlyDenied = new ArrayList<>();
        for (String permission : permissionsList) {
            if (!hasPermission(context, permission)) {
                permissionsCurrentlyDenied.add(permission);
            }
        }
        return permissionsCurrentlyDenied.toArray(new String[permissionsCurrentlyDenied.size()]);
    }

    /**
     * Checks {@link android.content.SharedPreferences} if a permission has been requested before.
     *
     * <p>It is important to note that this method only works if you call {@link
     * PermissionsUtil#permissionRequested(Context, String)} in {@link
     * android.app.Activity#onRequestPermissionsResult(int, String[], int[])}.
     */
    public static boolean isFirstRequest(Context context, String permission) {
        return context
                .getSharedPreferences(PERMISSION_PREFERENCE, Context.MODE_PRIVATE)
                .getBoolean(permission, true);
    }

    /**
     * Records in {@link android.content.SharedPreferences} that the specified permission has been
     * requested at least once.
     *
     * <p>This method should be called in {@link android.app.Activity#onRequestPermissionsResult(int,
     * String[], int[])}.
     */
    public static void permissionRequested(Context context, String permission) {
        context
                .getSharedPreferences(PERMISSION_PREFERENCE, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(permission, false)
                .apply();
    }

}
