/**
 * DetailsPresenter.java
 * <p>
 * Comment
 *
 * @category Global Analytics
 * @package com.sujin.volleyapp.presenter
 * @version 1.0
 * @author Sujin.N
 * @copyright Copyright (C) 2016 Global Analytics. All rights reserved.
 */
package com.sujin.volleyapp.presenter;

import android.graphics.Bitmap;

import com.sujin.volleyapp.contract.DetailsContract;
import com.sujin.volleyhelper.helper.VolleyRequestCompletedListener;
import com.sujin.volleyhelper.helper.VolleyRequestHelper;

/**
 * Created by sujun.n on 01/08/17.
 */
public class DetailsPresenter implements DetailsContract.Operations {
    private static final String REQUEST_TAG = "Image Download";

    private DetailsContract.ViewOps viewOps;

    public DetailsPresenter(DetailsContract.ViewOps viewOps) {
        this.viewOps = viewOps;
    }

    @Override
    public void downloadImage(String url) {
        VolleyRequestHelper requestHelper = new VolleyRequestHelper(new VolleyRequestCompletedListener() {
            @Override
            public void onRequestCompleted(String requestName, boolean status, Bitmap response, String errorMessage) {
                viewOps.showImage(response);
            }
        });

        requestHelper.simpleGetImage(REQUEST_TAG, url, VolleyRequestHelper.RequestType.IMAGE);
    }
}
