/**
 * DetailsContract.java
 * <p>
 * Comment
 *
 * @category Global Analytics
 * @package com.sujin.volleyapp.contract
 * @version 1.0
 * @author Sujin.N
 * @copyright Copyright (C) 2016 Global Analytics. All rights reserved.
 */
package com.sujin.volleyapp.contract;

import android.graphics.Bitmap;

/**
 * Created by sujun.n on 01/08/17.
 */
public interface DetailsContract {

    interface ViewOps {

        /**
         * Display image using bitmap
         * @param bitmap
         */
        void showImage(Bitmap bitmap);

    }

    interface Operations {

        /**
         * Download image using URL
         * @param url
         */
        void downloadImage(String url);

    }

}
