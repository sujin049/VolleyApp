/**
 * Utils.java
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

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by sujun.n on 31/07/17.
 */
public class Utils {

    /**
     * Returns the Internet Connection Available Status
     *
     * @param context - Context environment passed by this parameter
     * @return true if the Internet Connection is Available and false otherwise
     */
    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            return info != null && info.isConnected();
        }
        return false;
    }

}
