/**
 * ToolbarFragment.java
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

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewStubCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sujin.volleyapp.R;

/**
 * Created by sujun.n on 01/08/17.
 */
public abstract class ToolbarFragment extends BaseFragment {

    // UI Widgets
    protected ViewStubCompat mLayoutStub;

    /**
     * Initializes the views and other local members in this context.
     */
    protected abstract void initViews();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_content, container, false);
        mToolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);

        // Setup the content of the fragment
        mLayoutStub = (ViewStubCompat) mRootView.findViewById(R.id.layoutStub);

        initViews();

        return mRootView;
    }

}
