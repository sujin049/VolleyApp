/**
 * LoginContract.java
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

/**
 * Created by sujun.n on 01/08/17.
 */
public interface LoginContract {

    interface ViewOps {

        /**
         * Show or hide progressbar
         * @param state
         */
        void setProgressView(boolean state);

        /**
         * Action on success login
         */
        void loginSuccess();

    }

    interface Operations {

        /**
         * Attempt to login
         */
        void attemptLogin();

    }

}
