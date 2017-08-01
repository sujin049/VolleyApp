/**
 * BaseActivity.java
 * <p>
 * Comment
 *
 * @category Global Analytics
 * @package com.sujin.volleyapp
 * @version 1.0
 * @author Sujin.N
 * @copyright Copyright (C) 2016 Global Analytics. All rights reserved.
 */
package com.sujin.volleyapp.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.sujin.volleyapp.AppConstants;
import com.sujin.volleyapp.R;

/**
 * Created by sujun.n on 31/07/17.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Add fragment.
     *
     * @param fragment     the fragment
     * @param tag          the String refers the tag.
     * @param contentId    the integer refers the content id.
     * @param addBackStack the boolean refers the whether fragment needed be to add back stack or not.
     */
    public void addFragment(@NonNull Fragment fragment, String tag, @NonNull int contentId, boolean addBackStack) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (addBackStack) {
            transaction.addToBackStack(tag);
        }

        // Set custom animation
        if (getSupportFragmentManager().getFragments() != null && !getSupportFragmentManager().getFragments().isEmpty()) {
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        }
        transaction.add(contentId, fragment, TextUtils.isEmpty(tag) ? AppConstants.FRAGMENT_MAIN : tag);
        transaction.commitAllowingStateLoss();
    }
}
