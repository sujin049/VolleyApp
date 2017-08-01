/**
 * VolleyRequestHelper.java
 * <p/>
 * A rest client helper based on volley tool.
 *
 * @category Global Analytics
 * @package com.globalanalytics.oyeloanindia.helper
 * @version 1.0
 * @author Sujin.N
 * @copyright Copyright (C) 2016 Global Analytics. All rights reserved.
 */
package com.sujin.volleyhelper.helper;

/**
 * VolleyRequestHelper.java
 * <p/>
 * This class maintain the volley requests.
 */

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.sujin.volleyhelper.app.AppConstants;
import com.sujin.volleyhelper.utils.Logger;
import com.sujin.volleyhelper.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * A helper class to executes the web service using the volley. Supported
 * Methods: GET and POST. By default it will be executed on POST method.
 */
public class VolleyRequestHelper {

    private static final String TAG = VolleyRequestHelper.class.getSimpleName();

    private OnRequestCompletedListener mRequestCompletedListener;

    /**
     * Used to call web service and get response as JSON using post method.
     *
     * @param callback - The callback reference.
     */
    public VolleyRequestHelper(OnRequestCompletedListener callback) {
        mRequestCompletedListener = callback;
    }

    /**
     * Gets the volley error message.
     *
     * @param error the VolleyError object.
     * @return the String refers the volley error message
     */
    public static String getVolleyErrorMessage(VolleyError error) {
        // Error Message
        String errorResponse = "";
        if (error instanceof NoConnectionError
                || error instanceof TimeoutError
                || error instanceof NetworkError) {
            errorResponse = "Unable to reach server";
        } else {
            try {
                VolleyError responseError = new VolleyError(
                        new String(error.networkResponse.data));
                errorResponse = responseError.getMessage();
            } catch (Exception e) {
                Logger.e(TAG, e.getMessage(), e);
            }
        }
        return errorResponse;
    }

    /**
     * Get request to retrieve response from server
     * @param requestName
     * @param webserviceUrl
     * @param requestType
     */
    public void simpleGetRequest(final String requestName, final String webserviceUrl, RequestType requestType) {
        Log.d("Sujin", "Request type is " + requestType.name());

        // Add the post properties
        RequestProperties requestProperties = new RequestProperties();

        requestProperties.setRequestName(requestName);
        requestProperties.setRequestUrl(webserviceUrl);
        requestProperties.setMethod(Request.Method.GET);

        // Execute the request according to its request type.
        executeRequest(requestProperties, requestType);
    }

    /**
     * Get request to retrieve response from server
     * @param requestName
     * @param webserviceUrl
     * @param requestType
     */
    public void simpleGetImage(final String requestName, final String webserviceUrl, RequestType requestType) {
        Log.d("Sujin", "Request type is " + requestType.name());

        // Add the post properties
        RequestProperties requestProperties = new RequestProperties();

        requestProperties.setRequestName(requestName);
        requestProperties.setRequestUrl(webserviceUrl);

        // Execute the request according to its request type.
        executeRequest(requestProperties, requestType);
    }


    /***
     * Execute the API request.
     *
     * @param requestProperties the RequestProperties holds the request contents.
     * @param type              the RequestType.
     */
    public void executeRequest(RequestProperties requestProperties, RequestType type) {
        // Check the network connection and skip the request if the network is not available.
        if (!Utils.isInternetAvailable(VolleyController.getInstance())) {
            mRequestCompletedListener.onNoNetwork(requestProperties.getRequestName());
            return;
        }

        Logger.d(TAG, requestProperties.getRequestName() + " url : " + requestProperties.getRequestUrl());

        switch (type) {
            case JSON:
                requestJsonObject(requestProperties, false);
                break;
            case JSON_ARRAY:
                requestJsonArray(requestProperties, false);
                break;
            case STRING:
                requestString(requestProperties, false);

            case IMAGE:
                requestImage(requestProperties);
                break;
        }
    }


    /**
     * Request JSON Object from the Web API.
     *
     * @param requestProperties the <b>RequestProperties</b> represents the request content and other properties for the request including headers, params.
     * @param getCache          the boolean indicates whether cache can enable/disable.
     */
    private synchronized void requestJsonObject(final RequestProperties requestProperties, final boolean getCache) {

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(requestProperties.getMethod(),
                requestProperties.getRequestUrl(), requestProperties.getRequestJson(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.longDebug(TAG, requestProperties.getRequestName() + " Json Response: "
                                + response.toString());

                        mRequestCompletedListener.onRequestCompleted(
                                requestProperties.getRequestName(), true, response, null);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                JSONObject responseJson = new JSONObject();
                if (getCache) {
                    final Cache cache = VolleyController
                            .getInstance().getRequestQueue().getCache();
                    final Entry entry = cache.get(requestProperties.getRequestUrl());
                    if (entry != null) {
                        try {
                            final String data = new String(entry.data,
                                    "UTF-8");
                            Logger.d(TAG, requestProperties.getRequestName()
                                    + " Cached Data: " + data);
                            responseJson = new JSONObject(data);
                            mRequestCompletedListener
                                    .onRequestCompleted(requestProperties.getRequestName(),
                                            true, responseJson, null);
                            return;
                        } catch (UnsupportedEncodingException
                                | JSONException e) {
                            Logger.e(TAG, e.getMessage(), e);
                        }
                    } else {
                        Logger.e(TAG, requestProperties.getRequestName()
                                + " Cache does not exist");
                    }
                }


                // Error Message
                String errorResponse = getVolleyErrorMessage(error);
                Logger.e(TAG, requestProperties.getRequestName()
                        + " Error Message: " + errorResponse);
                // Return Default Error Response
                try {
                    responseJson = new JSONObject(AppConstants.MSG_SERVER_UNREACHABLE);
                } catch (JSONException e) {
                    Logger.e(TAG, e.getMessage(), e);
                }
                mRequestCompletedListener
                        .onRequestCompleted(requestProperties.getRequestName(), false,
                                responseJson, errorResponse);
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                final Map<String, String> params = requestProperties.getParams();
                if (params != null) {
                    return params;
                }
                return super.getParams();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = requestProperties.getHeaders();
                if (headers != null) {
                    return headers;
                }
                return super.getHeaders();
            }

            @Override
            public Priority getPriority() {
                final Priority priority = requestProperties.getPriority();
                if (priority != null) {
                    return priority;
                }
                return super.getPriority();
            }

            @Override
            public String getBodyContentType() {
                final String contentType = requestProperties.getContentType();
                if (contentType != null) {
                    return contentType;
                }
                return super.getBodyContentType();
            }

            @Override
            public byte[] getBody() {
                final byte[] body = requestProperties.getBody();
                if (body != null) {
                    return body;
                }
                return super.getBody();
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                mRequestCompletedListener.onRequestCompleted(requestProperties.getRequestName(), response);
                // Process and updates the session from the response header.
                return super.parseNetworkResponse(response);
            }
        };
        jsonRequest.setTag(requestProperties.getRequestName());
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(requestProperties.getSocketTimeout(), DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding JSON Object request to request queue
        VolleyController.getInstance().addToRequestQueue(jsonRequest);
    }

    /**
     * Request JSON Array from the Web API.
     *
     * @param requestProperties the <b>RequestProperties</b> represents the request content and other properties for the request including headers, params.
     * @param getCache          the boolean indicates whether cache can enable/disable.
     */
    private synchronized void requestJsonArray(final RequestProperties requestProperties, final boolean getCache) {

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                requestProperties.getRequestUrl(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Logger.longDebug(TAG, requestProperties.getRequestName() + " Json Array Response: "
                        + response.toString());
                mRequestCompletedListener.onRequestCompleted(
                        requestProperties.getRequestName(), true, response, null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                JSONArray responseJson = new JSONArray();
                if (getCache) {
                    final Cache cache = VolleyController
                            .getInstance().getRequestQueue().getCache();
                    final Entry entry = cache.get(requestProperties.getRequestUrl());
                    if (entry != null) {
                        try {
                            final String data = new String(entry.data,
                                    "UTF-8");
                            Logger.d(TAG, requestProperties.getRequestName()
                                    + " Cached Data: " + data);
                            responseJson = new JSONArray(data);
                            mRequestCompletedListener
                                    .onRequestCompleted(requestProperties.getRequestName(),
                                            true, responseJson, null);
                            return;
                        } catch (UnsupportedEncodingException
                                | JSONException e) {
                            Logger.e(TAG, e.getMessage(), e);
                        }
                    } else {
                        Logger.e(TAG, requestProperties.getRequestName()
                                + " Cache does not exist");
                    }
                }
                // Error Message
                String errorResponse = getVolleyErrorMessage(error);
                Logger.e(TAG, requestProperties.getRequestName()
                        + " Error Message: " + errorResponse);
                mRequestCompletedListener
                        .onRequestCompleted(requestProperties.getRequestName(), false,
                                responseJson, errorResponse);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                final Map<String, String> params = requestProperties.getParams();
                if (params != null) {
                    return params;
                }
                return super.getParams();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                @SuppressWarnings("unchecked")
                final Map<String, String> headers = requestProperties.getHeaders();
                if (headers != null) {
                    return headers;
                }
                return super.getHeaders();
            }

            @Override
            public Priority getPriority() {
                final Priority priority = requestProperties.getPriority();
                if (priority != null) {
                    return priority;
                }
                return super.getPriority();
            }

            @Override
            public String getBodyContentType() {
                final String contentType = requestProperties.getContentType();
                if (contentType != null) {
                    return contentType;
                }
                return super.getBodyContentType();
            }

            @Override
            public byte[] getBody() {
                final byte[] body = requestProperties.getBody();
                if (body != null) {
                    return body;
                }
                return super.getBody();
            }

            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                mRequestCompletedListener.onRequestCompleted(requestProperties.getRequestName(), response);
                // Process and updates the session from the response header.
                return super.parseNetworkResponse(response);
            }
        };
        jsonArrayRequest.setTag(requestProperties.getRequestName());
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(requestProperties.getSocketTimeout(), DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding JsonArray request to request queue
        VolleyController.getInstance().addToRequestQueue(jsonArrayRequest);
    }

    /**
     * Request String response from the Web API.
     *
     * @param requestProperties the <b>RequestProperties</b> represents the request content and other properties for the request including headers, params.
     * @param getCache          the boolean indicates whether cache can enable/disable.
     */
    private synchronized void requestString(final RequestProperties requestProperties, final boolean getCache) {

        final StringRequest stringRequest = new StringRequest(requestProperties.getMethod(),
                requestProperties.getRequestUrl(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Logger.longDebug(TAG, requestProperties.getRequestName() + " String Response Success: "
                        + response);

                mRequestCompletedListener.onRequestCompleted(
                        requestProperties.getRequestName(), true, response, null);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                String response = null;
                if (getCache) {
                    final Cache cache = VolleyController
                            .getInstance().getRequestQueue().getCache();
                    final Entry entry = cache.get(requestProperties.getRequestUrl());
                    if (entry != null) {
                        try {
                            response = new String(entry.data, "UTF-8");
                            Logger.d(TAG, requestProperties.getRequestName()
                                    + " Cached Data: " + response);
                            mRequestCompletedListener
                                    .onRequestCompleted(requestProperties.getRequestName(),
                                            true, response, null);
                            return;
                        } catch (UnsupportedEncodingException e) {
                            Logger.e(TAG, e.getMessage(), e);
                        }
                    } else {
                        Logger.e(TAG, requestProperties.getRequestName()
                                + " Cache does not exist");
                    }
                }
                // Error Message
                String errorResponse = getVolleyErrorMessage(error);
                Logger.e(TAG, requestProperties.getRequestName()
                        + " Error Message: " + errorResponse);
                mRequestCompletedListener.onRequestCompleted(
                        requestProperties.getRequestName(), false, response, errorResponse);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                final Map<String, String> params = requestProperties.getParams();
                if (params != null) {
                    return params;
                }
                return super.getParams();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = requestProperties.getHeaders();
                if (headers != null) {
                    return headers;
                }
                return super.getHeaders();
            }

            @Override
            public Priority getPriority() {
                final Priority priority = requestProperties.getPriority();
                if (priority != null) {
                    return priority;
                }
                return super.getPriority();
            }

            @Override
            public String getBodyContentType() {
                final String contentType = requestProperties.getContentType();
                if (contentType != null) {
                    return contentType;
                }
                return super.getBodyContentType();
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                final byte[] body = requestProperties.getBody();
                if (body != null) {
                    return body;
                }
                return super.getBody();
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mRequestCompletedListener.onRequestCompleted(requestProperties.getRequestName(), response);
                // Process and updates the session from the response header.
                return super.parseNetworkResponse(response);
            }
        };
        stringRequest.setTag(requestProperties.getRequestName());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(requestProperties.getSocketTimeout(), DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding String request to request queue
        VolleyController.getInstance().addToRequestQueue(stringRequest);
    }

    /**
     * Request bitmap response from the Web API.
     *
     * @param requestProperties the <b>RequestProperties</b> represents the request content and other properties for the request including headers, params.
     */
    private synchronized void requestImage(final RequestProperties requestProperties) {

        ImageLoader imageLoader = VolleyController.getInstance().getImageLoader();
        // If you are using normal ImageView
        imageLoader.get(requestProperties.requestUrl, new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                // Error Message
                String errorResponse = getVolleyErrorMessage(error);
                Logger.e(TAG, requestProperties.getRequestName()
                        + " Error Message: " + errorResponse);
                mRequestCompletedListener.onRequestCompleted(
                        requestProperties.getRequestName(), false, errorResponse, errorResponse);
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    mRequestCompletedListener.onRequestCompleted(requestProperties.getRequestName(),
                            true, response.getBitmap(), null);
                }
            }
        });
    }

    /**
     * The Enum represents the request type such as Json, JsonArray, String.
     */
    public enum RequestType {
        JSON, JSON_ARRAY, STRING, IMAGE
    }

    /**
     * A callback interface indicates the status about the completion of HTTP
     * request.
     */
    public interface OnRequestCompletedListener {

        /**
         * Called when the JSON Object request has been completed.
         *
         * @param requestName  the String refers the request name
         * @param status       the status of the request either success or failure
         * @param response     the JSON Object returns from WebService Response. It may                     be null if request failed.
         * @param errorMessage the String refers the error message when request failed to                     get the response
         */
        void onRequestCompleted(String requestName, boolean status,
                                JSONObject response, String errorMessage);

        /**
         * Called when the JSON Array request has been completed.
         *
         * @param requestName  the String refers the request name
         * @param status       the status of the request either success or failure
         * @param response     the JSON Array returns from WebService Response. It may be                     null if request failed.
         * @param errorMessage the String refers the error message when request failed to                     get the response
         */
        void onRequestCompleted(String requestName, boolean status,
                                JSONArray response, String errorMessage);

        /**
         * Called when the String request has been completed.
         *
         * @param requestName  the String refers the request name
         * @param status       the status of the request either success or failure
         * @param response     the String response returns from the Webservice. It may be                     null if request failed.
         * @param errorMessage the String refers the error message when request failed to                     get the response
         */
        void onRequestCompleted(String requestName, boolean status,
                                String response, String errorMessage);

        /**
         * Called when the JSON Object request has been completed.
         *
         * @param requestName  the String refers the request name
         * @param status       the status of the request either success or failure
         * @param response     the JSON Object returns from WebService Response. It may                     be null if request failed.
         * @param errorMessage the String refers the error message when request failed to                     get the response
         */
        void onRequestCompleted(String requestName, boolean status,
                                Bitmap response, String errorMessage);

        /**
         * Called when the request has been completed.
         *
         * @param requestName the String refers the request name.
         * @param response    the NetworkResponse refers the response retrieved from the server.
         */
        void onRequestCompleted(String requestName, NetworkResponse response);


        /**
         * Called when the network connect is not available.
         *
         * @param requestName the String refers the request name.
         */
        void onNoNetwork(String requestName);

    }

    /**
     * A model that represents a volley request content and its properties.
     */
    public static class RequestProperties {
        private String requestName;
        private String requestUrl;
        private JSONObject requestJson;
        private Map<String, String> params = new HashMap<>();
        private Map<String, String> headers = new HashMap<>();
        private byte[] body;
        private String contentType;
        private Request.Priority priority = Request.Priority.HIGH;
        private int method = Request.Method.POST;
        private int socketTimeout = AppConstants.SOCKET_TIMEOUT_MS;

        /**
         * Instantiates a new Request properties.
         */
        public RequestProperties() {
        }

        /**
         * Gets request name.
         *
         * @return the request name
         */
        public String getRequestName() {
            return requestName;
        }

        /**
         * Sets request name.
         *
         * @param requestName the request name
         */
        public void setRequestName(String requestName) {
            this.requestName = requestName;
        }

        /**
         * Gets request url.
         *
         * @return the request url
         */
        public String getRequestUrl() {
            return requestUrl;
        }

        /**
         * Sets request url.
         *
         * @param requestUrl the request url
         */
        public void setRequestUrl(String requestUrl) {
            this.requestUrl = requestUrl;
        }

        public JSONObject getRequestJson() {
            return requestJson;
        }

        public void setRequestJson(JSONObject requestJson) {
            this.requestJson = requestJson;
        }

        /**
         * Gets the params for the request.
         *
         * @return the Map refers http parameters.
         */
        public Map<String, String> getParams() {
            return params;
        }

        /**
         * Set params refers the http query params will be injected to the request.
         *
         * @param params the Map<String, String> refers the query parameters in the key and values formats.
         */
        public void setParams(Map<String, String> params) {
            this.params = params;
        }

        /**
         * Gets the headers for the request.
         *
         * @return the Map refers the headers.
         */
        public Map<String, String> getHeaders() {
            return headers;
        }

        /**
         * Set headers refers the headers will be injected to the request.
         *
         * @param headers the Map<String, String> refers the header contents in the key and values formats.
         */
        public void setHeaders(Map<String, String> headers) {
            this.headers = headers;
        }

        /**
         * Add a header to the header map.
         *
         * @param headerName  the String refers the header name.
         * @param headerValue the String refers the header value.
         */
        public void addHeader(String headerName, String headerValue) {
            if (headers == null) {
                headers = new HashMap<>();
            }
            // Add the header only the key and value not empty.
            if (!TextUtils.isEmpty(headerName) && !TextUtils.isEmpty(headerValue)) {
                headers.put(headerName, headerValue);
            }
        }

        /**
         * Gets body content type.
         *
         * @return the String refers the body content type of the request.
         */
        public String getContentType() {
            return contentType;
        }

        /**
         * Sets the body content type for the request.
         *
         * @param contentType the String refers the body content type.
         */
        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        /**
         * Get the body content for the request.
         *
         * @return the body content in byte array.
         */
        public byte[] getBody() {
            return body;
        }

        /**
         * Sets the body content for the request.
         *
         * @param body the byte[] refers the body content for the reuqest.
         */
        public void setBody(byte[] body) {
            this.body = body;
        }

        /**
         * Gets the volley request priority. Refers {@link com.android.volley.Request.Priority}
         *
         * @return the priority
         */
        public Request.Priority getPriority() {
            return priority;
        }

        /**
         * Sets the volley request priority. Refers {@link com.android.volley.Request.Priority}
         *
         * @param priority the priority
         */
        public void setPriority(Request.Priority priority) {
            this.priority = priority;
        }

        /**
         * Gets the http method. Refers {@link com.android.volley.Request.Method}
         *
         * @return the method of the request.
         */
        public int getMethod() {
            return method;
        }

        /**
         * Sets the http method from the volley Request.Method enum. Refers {@link com.android.volley.Request.Method}
         *
         * @param method the method
         */
        public void setMethod(int method) {
            this.method = method;
        }

        public int getSocketTimeout() {
            return socketTimeout;
        }

        public void setSocketTimeout(int socketTimeout) {
            this.socketTimeout = socketTimeout;
        }
    }
}
