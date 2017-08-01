/**
 * BaseFragment.java
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
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sujin.volleyapp.R;
import com.sujin.volleyapp.ui.activities.BaseActivity;

/**
 * Created by sujun.n on 01/08/17.
 */
public class BaseFragment extends Fragment {

    private static final String TAG = BaseFragment.class.getSimpleName();

    protected View mRootView;
    protected Toolbar mToolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFragmentConfiguration();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getBaseActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Setup the toolbar and its menu.
     */
    public void setupToolBar(String title, boolean isHomeUpEnabled) {
        if (mToolbar != null) {
            mToolbar.setTitle("");
            ((TextView) mToolbar.findViewById(R.id.txtTitle)).setText(title);
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(isHomeUpEnabled);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        }
    }

    /**
     * Show the info toast.
     * @param msg
     * @return Snackbar
     */
    public Snackbar showInfoToast(String msg) {
        Snackbar snackbar = Snackbar.make(getBaseActivity().findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        return snackbar;
    }

    /**
     * Setup the fragment configuration.
     */
    public void setupFragmentConfiguration() {
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    // Fragment Transactions

    /**
     * Gets the base activity.
     *
     * @return the BaseActivity
     */
    public BaseActivity getBaseActivity() {
        if (getActivity() != null) {
            return (BaseActivity) getActivity();
        }

        return null;
    }

}
