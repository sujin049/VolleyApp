/**
 * Logger.java
 * <p>
 * Comment
 *
 * @category Global Analytics
 * @package com.sujin.volleyhelper
 * @version 1.0
 * @author Sujin.N
 * @copyright Copyright (C) 2016 Global Analytics. All rights reserved.
 */
package com.sujin.volleyhelper.utils;

import android.util.Log;

import com.sujin.volleyhelper.BuildConfig;

/**
 * Created by sujun.n on 31/07/17.
 */
public class Logger {

    /**
     * D void.
     *
     * @param tag     the tag
     * @param message the message
     */
    public static void d(final String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
        }
    }

    /**
     * D void.
     *
     * @param tag   the tag
     * @param cause the cause
     */
    public static void d(final String tag, Throwable cause) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, tag, cause);
        }
    }

    /**
     * Long debug log.
     *
     * @param tag
     * @param message
     */
    public static void longDebug(final String tag, String message) {
        if (!BuildConfig.DEBUG) {
            return;
        }
        if (message.length() > 4000) {
            Log.d(tag, message.substring(0, 4000));
            longDebug(tag, message.substring(4000));
        } else
            Log.d(tag, message);
    }


    /**
     * V void.
     *
     * @param tag     the tag
     * @param message the message
     */
    public static void v(final String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, message);
        }
    }

    /**
     * V void.
     *
     * @param tag     the tag
     * @param message the message
     * @param cause   the cause
     */
    public static void v(final String tag, String message, Throwable cause) {
        if (BuildConfig.DEBUG && Log.isLoggable(tag, Log.VERBOSE)) {
            Log.v(tag, message, cause);
        }
    }

    /**
     * I void.
     *
     * @param tag     the tag
     * @param message the message
     */
    public static void i(final String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message);
        }
    }

    /**
     * I void.
     *
     * @param tag   the tag
     * @param cause the cause
     */
    public static void i(final String tag, Throwable cause) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, tag, cause);
        }
    }

    /**
     * Long info log.
     *
     * @param tag
     * @param message
     */
    public static void longInfo(final String tag, String message) {
        if (!BuildConfig.DEBUG) {
            return;
        }
        if (message.length() > 4000) {
            Log.i(tag, message.substring(0, 4000));
            longInfo(tag, message.substring(4000));
        } else
            Log.i(tag, message);
    }

    /**
     * W void.
     *
     * @param tag     the tag
     * @param message the message
     */
    public static void w(final String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, message);
        }
    }

    /**
     * W void.
     *
     * @param tag   the tag
     * @param cause the cause
     */
    public static void w(final String tag, Throwable cause) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, tag, cause);
        }
    }

    /**
     * E void.
     *
     * @param tag     the tag
     * @param message the message
     */
    public static void e(final String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message);
        }
    }

    /**
     * E void.
     *
     * @param tag   the tag
     * @param cause the cause
     */
    public static void e(final String tag, String message, Throwable cause) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message, cause);
        }
    }

}
