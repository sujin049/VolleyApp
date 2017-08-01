/**
 * VolleyRequestCompletedListener.java
 * <p/>
 * A call back listener for listening the VolleyRequestHelper.OnRequestCompletedListener events.
 *
 * @category Global Analytics
 * @package com.globalanalytics.lendingstream.listener
 * @version 1.0
 * @author Rajkumar.N
 * @copyright Copyright (C) 2016 Global Analytics. All rights reserved.
 */
package com.sujin.volleyhelper.helper;

import android.graphics.Bitmap;

import com.android.volley.NetworkResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A call back listener for listening the VolleyRequestHelper.OnRequestCompletedListener events.
 */
public abstract class VolleyRequestCompletedListener implements VolleyRequestHelper.OnRequestCompletedListener {

    public VolleyRequestCompletedListener() {

    }

    @Override
    public void onRequestCompleted(String requestName, boolean status, JSONObject response, String errorMessage) {

    }

    @Override
    public void onRequestCompleted(String requestName, boolean status, JSONArray response, String errorMessage) {

    }

    @Override
    public void onRequestCompleted(String requestName, boolean status, String response, String errorMessage) {

    }

    @Override
    public void onRequestCompleted(String requestName, boolean status, Bitmap response, String errorMessage) {

    }

    @Override
    public void onRequestCompleted(String requestName, NetworkResponse response) {

    }

    @Override
    public void onNoNetwork(String requestName) {

    }

}
