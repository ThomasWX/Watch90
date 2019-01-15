package com.example.common;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Provides logging functions.
 */
public class LogUtil {
    public static final String TAG = "Dialer";
    private static final String SEPARATOR = " - ";

    private LogUtil() {
    }

    /**
     * Log at a verbose level. Verbose logs should generally be filtered out, but may be useful when
     * additional information is needed (e.g. to see how a particular flow evolved). These logs will
     * not generally be available on production builds.
     *
     * @param tag  An identifier to allow searching for related logs. Generally of the form
     *             'Class.method'.
     * @param msg  The message you would like logged, possibly with format arguments.
     * @param args Optional arguments to be used in the formatted string.
     * @see {@link String#format(String, Object...)}
     * @see {@link android.util.Log#v(String, String)}
     */
    public static void v(@NonNull String tag, @Nullable String msg, @Nullable Object... args) {
        println(android.util.Log.VERBOSE, TAG, tag, msg, args);
    }

    /**
     * Log at a debug level. Debug logs should provide known-useful information to aid in
     * troubleshooting or evaluating flow. These logs will not generally be available on production
     * builds.
     *
     * @param tag  An identifier to allow searching for related logs. Generally of the form
     *             'Class.method'
     * @param msg  The message you would like logged, possibly with format arguments
     * @param args Optional arguments to be used in the formatted string
     * @see {@link String#format(String, Object...)}
     * @see {@link android.util.Log#d(String, String)}
     */
    public static void d(@NonNull String tag, @Nullable String msg, @Nullable Object... args) {
        println(android.util.Log.DEBUG, TAG, tag, msg, args);
    }

    /**
     * Log at an info level. Info logs provide information that would be useful to have on production
     * builds for troubleshooting.
     *
     * @param tag  An identifier to allow searching for related logs. Generally of the form
     *             'Class.method'.
     * @param msg  The message you would like logged, possibly with format arguments.
     * @param args Optional arguments to be used in the formatted string.
     * @see {@link String#format(String, Object...)}
     * @see {@link android.util.Log#i(String, String)}
     */
    public static void i(@NonNull String tag, @Nullable String msg, @Nullable Object... args) {
        println(android.util.Log.INFO, TAG, tag, msg, args);
    }

    /**
     * Log entry into a method at the info level.
     *
     * @param tag An identifier to allow searching for related logs. Generally of the form
     *            'Class.method'.
     */
    public static void enterBlock(String tag) {
        println(android.util.Log.INFO, TAG, tag, "enter");
    }

    /**
     * Log at a warn level. Warn logs indicate a possible error (e.g. a default switch branch was hit,
     * or a null object was expected to be non-null), but recovery is possible. This may be used when
     * it is not guaranteed that an indeterminate or bad state was entered, just that something may
     * have gone wrong.
     *
     * @param tag  An identifier to allow searching for related logs. Generally of the form
     *             'Class.method'.
     * @param msg  The message you would like logged, possibly with format arguments.
     * @param args Optional arguments to be used in the formatted string.
     * @see {@link String#format(String, Object...)}
     * @see {@link android.util.Log#w(String, String)}
     */
    public static void w(@NonNull String tag, @Nullable String msg, @Nullable Object... args) {
        println(android.util.Log.WARN, TAG, tag, msg, args);
    }

    /**
     * Log at an error level. Error logs are used when it is known that an error occurred and is
     * possibly fatal. This is used to log information that will be useful for troubleshooting a crash
     * or other severe condition (e.g. error codes, state values, etc.).
     *
     * @param tag  An identifier to allow searching for related logs. Generally of the form
     *             'Class.method'.
     * @param msg  The message you would like logged, possibly with format arguments.
     * @param args Optional arguments to be used in the formatted string.
     * @see {@link String#format(String, Object...)}
     * @see {@link android.util.Log#e(String, String)}
     */
    public static void e(@NonNull String tag, @Nullable String msg, @Nullable Object... args) {
        println(android.util.Log.ERROR, TAG, tag, msg, args);
    }

    /**
     * Log an exception at an error level. Error logs are used when it is known that an error occurred
     * and is possibly fatal. This is used to log information that will be useful for troubleshooting
     * a crash or other severe condition (e.g. error codes, state values, etc.).
     *
     * @param tag       An identifier to allow searching for related logs. Generally of the form
     *                  'Class.method'.
     * @param msg       The message you would like logged.
     * @param throwable The exception to log.
     * @see {@link String#format(String, Object...)}
     * @see {@link android.util.Log#e(String, String)}
     */
    public static void e(@NonNull String tag, @Nullable String msg, @NonNull Throwable throwable) {
        if (!TextUtils.isEmpty(msg)) {
            println(
                    android.util.Log.ERROR,
                    TAG,
                    tag,
                    msg + "\n" + android.util.Log.getStackTraceString(throwable));
        }
    }

    public static boolean isVerboseEnabled() {
        return android.util.Log.isLoggable(TAG, android.util.Log.VERBOSE);
    }

    public static boolean isDebugEnabled() {
        return android.util.Log.isLoggable(TAG, android.util.Log.DEBUG);
    }

    private static void println(
            int level,
            @NonNull String tag,
            @NonNull String localTag,
            @Nullable String msg,
            @Nullable Object... args) {
        // Formatted message is computed lazily if required.
        String formattedMsg;
        // Either null is passed as a single argument or more than one argument is passed.
        boolean hasArgs = args == null || args.length > 0;
        if ((level >= android.util.Log.INFO) || android.util.Log.isLoggable(tag, level)) {
            formattedMsg = localTag;
            if (!TextUtils.isEmpty(msg)) {
                formattedMsg += SEPARATOR + (hasArgs ? String.format(msg, args) : msg);
            }
            android.util.Log.println(level, tag, formattedMsg);
        }
    }
}
