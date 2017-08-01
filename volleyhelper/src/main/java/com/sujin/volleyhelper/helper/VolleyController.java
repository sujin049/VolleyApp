/**
 * VolleyController.java
 * <p>
 * Comment
 *
 * @category Global Analytics
 * @package com.sujin.volleyhelper
 * @version 1.0
 * @author Sujin.N
 * @copyright Copyright (C) 2016 Global Analytics. All rights reserved.
 */
package com.sujin.volleyhelper.helper;

import android.app.Application;
import android.content.Context;

import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.sujin.volleyhelper.app.AppConstants;
import com.sujin.volleyhelper.utils.Logger;
import com.sujin.volleyhelper.utils.LruBitmapCache;

import java.io.File;

/**
 * Created by sujun.n on 31/07/17.
 */
public class VolleyController extends Application {
    private static final String TAG = VolleyController.class.getSimpleName();

    /**
     * The application instance.
     */
    private static VolleyController sInstance;

    /**
     * The application request queue.
     */
    private RequestQueue mRequestQueue;

    /**
     * The application image loader.
     */
    private ImageLoader mImageLoader;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized VolleyController getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

    }

    /**
     * New request queue.
     *
     * @param context the context
     * @return the request queue
     */
    public static RequestQueue newRequestQueue(Context context) {
        // define cache folder
        File rootCache = context.getExternalCacheDir();
        if (rootCache == null) {
            Logger.e(TAG, "Can't find External Cache Dir, "
                    + "switching to application specific cache directory");
            rootCache = context.getCacheDir();
        }

        File cacheDir = new File(rootCache, AppConstants.DEFAULT_CACHE_DIR);
        cacheDir.mkdirs();

        HttpStack stack = new HurlStack();
        Network network = new BasicNetwork(stack);
        DiskBasedCache diskBasedCache = new DiskBasedCache(cacheDir, AppConstants.DEFAULT_DISK_USAGE_BYTES);
        RequestQueue queue = new RequestQueue(diskBasedCache, network);
        queue.start();
        return queue;
    }

    /**
     * To get the Volley network library's RequestQueue Instance.
     *
     * @return RequestQueue instance.
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * To add the request object in the RequestQueue.
     *
     * @param <T> the generic type
     * @param req the req
     */
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * To get the ImageLoader class instance to load the network image in Image
     * view.
     *
     * @return ImageLoader instance.
     */
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    /**
     * Cancels the particular request
     * @param tag
     */
    public void cancelRequest(String tag) {
        VolleyController.getInstance().getRequestQueue().cancelAll(tag);
    }
}
