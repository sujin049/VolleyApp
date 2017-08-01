/**
 * DetailsFragment.java
 * <p>
 * Comment
 *
 * @category Global Analytics
 * @package com.sujin.volleyapp.ui.fragments
 * @version 1.0
 * @author Sujin.N
 * @copyright Copyright (C) 2016 Global Analytics. All rights reserved.
 */
package com.sujin.volleyapp.ui.fragments;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sujin.volleyapp.AppConstants;
import com.sujin.volleyapp.AppController;
import com.sujin.volleyapp.R;
import com.sujin.volleyapp.contract.DetailsContract;
import com.sujin.volleyapp.data.DataSet;
import com.sujin.volleyapp.presenter.DetailsPresenter;

/**
 * Created by sujun.n on 01/08/17.
 */
public class DetailsFragment extends ToolbarFragment implements DetailsContract.ViewOps {

    // UI Reference
    private TextView action;
    private ImageView imageView;

    // Data reference
    private DataSet dataSet;

    private DetailsPresenter presenter;

    @Override
    protected void initViews() {
        setupToolBar(getString(R.string.title_home), true);

        mLayoutStub.setLayoutResource(R.layout.content_detail);
        mLayoutStub.inflate();

        presenter = new DetailsPresenter(this);

        imageView = (ImageView) mRootView.findViewById(R.id.imageView);
        action = (TextView) mRootView.findViewById(R.id.action);

        int itemPosition = getArguments().getInt(AppConstants.EXTRA_ITEM_POSITION);

        dataSet = AppController.getInstance().getDataSets().get(itemPosition);

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action.setText(getString(R.string.downloading));
                String url = dataSet.getUrls().getFull();
                presenter.downloadImage(url);
            }
        });
    }

    @Override
    public void showImage(Bitmap bitmap) {
        action.setVisibility(View.GONE);
        imageView.setImageBitmap(bitmap);
    }
}
