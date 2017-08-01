/**
 * LoginPresenter.java
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sujin.volleyapp.AppConstants;
import com.sujin.volleyapp.AppController;
import com.sujin.volleyapp.contract.LoginContract;
import com.sujin.volleyapp.data.DataSet;
import com.sujin.volleyhelper.helper.VolleyRequestCompletedListener;
import com.sujin.volleyhelper.helper.VolleyRequestHelper;

import java.util.List;

/**
 * Created by sujun.n on 01/08/17.
 */
public class LoginPresenter implements LoginContract.Operations {

    private LoginContract.ViewOps viewOps;

    public LoginPresenter(LoginContract.ViewOps viewOps) {
        this.viewOps = viewOps;
    }

    @Override
    public void attemptLogin() {
        viewOps.setProgressView(true);

        VolleyRequestHelper requestHelper = new VolleyRequestHelper(new VolleyRequestCompletedListener() {
            @Override
            public void onRequestCompleted(String requestName, boolean status, String response, String errorMessage) {
                viewOps.setProgressView(false);

                TypeToken<List<DataSet>> typeToken = new TypeToken<List<DataSet>>() {};
                List<DataSet> dataSets = new Gson().fromJson(response, typeToken.getType());

                AppController.getInstance().setDataSets(dataSets);

                viewOps.loginSuccess();
            }
        });

        requestHelper.simpleGetRequest("Config", AppConstants.CONFIG_URL, VolleyRequestHelper.RequestType.STRING);
    }
}
