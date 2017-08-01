/**
 * ContentActivity.java
 * <p>
 * Comment
 *
 * @category Global Analytics
 * @package com.sujin.volleyapp.ui.activities
 * @version 1.0
 * @author Sujin.N
 * @copyright Copyright (C) 2016 Global Analytics. All rights reserved.
 */
package com.sujin.volleyapp.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.sujin.volleyapp.AppConstants;
import com.sujin.volleyapp.R;
import com.sujin.volleyapp.ui.fragments.DetailsFragment;
import com.sujin.volleyapp.ui.fragments.HomeFragment;
import com.sujin.volleyapp.ui.fragments.LoginFragment;

/**
 * Created by sujun.n on 01/08/17.
 */
public class ContentActivity extends BaseActivity {
    public static final String TAG = ContentActivity.class.getSimpleName();

    /**
     * Start the content activity with initial fragment.
     *
     * @param context        the Context refers the current context environment.
     * @param fragmentId     the integer refers the fragment identifier.
     */
    public static void launchContentActivity(Activity context, String fragmentId) {
        launchContentActivity(context, fragmentId, null);
    }

    /**
     * Start the content activity with initial fragment.
     *
     * @param context        the Context refers the current context environment.
     * @param fragmentId     the integer refers the fragment identifier.
     * @param bundle         the bundle refers the extras passed from the triggered place.
     */
    public static void launchContentActivity(Activity context, String fragmentId, Bundle bundle) {
        final Intent navigationIntent = new Intent(context, ContentActivity.class);
        navigationIntent.putExtra(AppConstants.EXTRA_FRAGMENT, fragmentId);
        if (bundle != null) {
            navigationIntent.putExtras(bundle);
        }

        context.startActivity(navigationIntent);
        context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the fragment type
        String fragment = getIntent().getStringExtra(AppConstants.EXTRA_FRAGMENT);

        if (fragment == null) fragment = "";

        // Create and bind the fragment.
        if (getSupportFragmentManager().findFragmentByTag(AppConstants.FRAGMENT_MAIN) == null) {
            Fragment viewFragment = null;
            switch (fragment) {

                case AppConstants.FRAGMENT_HOME:
                    viewFragment = new HomeFragment();
                    break;

                case AppConstants.FRAGMENT_DETAILS:
                    viewFragment = new DetailsFragment();
                    break;

                default:
                    viewFragment = new LoginFragment();
                    break;
            }

            if (viewFragment != null) {
                viewFragment.setArguments(getIntent().getExtras());
                addFragment(viewFragment, AppConstants.FRAGMENT_MAIN, android.R.id.content, false);
            }
        }

    }
}
