package com.sujin.volleyapp.ui.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sujin.volleyapp.AppConstants;
import com.sujin.volleyapp.R;
import com.sujin.volleyapp.contract.LoginContract;
import com.sujin.volleyapp.presenter.LoginPresenter;
import com.sujin.volleyapp.ui.activities.ContentActivity;

/**
 * Created by sujun.n on 01/08/17.
 */
public class LoginFragment extends ToolbarFragment implements LoginContract.ViewOps {

    // UI references.
    private View mProgressView;
    private View mLoginFormView;

    private LoginPresenter presenter;

    @Override
    protected void initViews() {
        setupToolBar(getString(R.string.title_sign_in), false);

        mLayoutStub.setLayoutResource(R.layout.content_login);
        mLayoutStub.inflate();

        presenter = new LoginPresenter(this);

        Button mEmailSignInButton = (Button) mRootView.findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.attemptLogin();
            }
        });

        ((EditText) mRootView.findViewById(R.id.password)).setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    presenter.attemptLogin();
                }
                return false;
            }
        });

        mLoginFormView = mRootView.findViewById(R.id.login_form);
        mProgressView = mRootView.findViewById(R.id.login_progress);
    }

    @Override
    public void loginSuccess() {
        ContentActivity.launchContentActivity(getActivity(), AppConstants.FRAGMENT_HOME);
    }

    @Override
    public void setProgressView(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

}

