/**
 * LruBitmapCache.java
 * <p>
 * This class maintain the image cache of this application.
 *
 * @category Global Analytics
 * @package com.globalanalytics.oyeloanindia.helper
 * @version 1.0
 * @author Sujin.N
 * @copyright Copyright (C) 2016 Global Analytics. All rights reserved.
 */
package com.sujin.volleyhelper.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * LruBitmapCache.java
 * <p/>
 * This class maintain the image cache of this application.
 */
public class LruBitmapCache extends LruCache<String, Bitmap> implements
        ImageLoader.ImageCache {
    public LruBitmapCache() {
        this(getDefaultLruCacheSize());
    }

    public LruBitmapCache(int sizeInKiloBytes) {
        super(sizeInKiloBytes);
    }

    public static int getDefaultLruCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        return maxMemory / 8;
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}

