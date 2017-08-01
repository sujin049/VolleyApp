/**
 * AppController.java
 * <p>
 * Comment
 *
 * @category Global Analytics
 * @package com.sujin.volleyapp
 * @version 1.0
 * @author Sujin.N
 * @copyright Copyright (C) 2016 Global Analytics. All rights reserved.
 */
package com.sujin.volleyapp;

import com.sujin.volleyapp.data.DataSet;
import com.sujin.volleyhelper.helper.VolleyController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sujun.n on 31/07/17.
 */
public class AppController extends VolleyController {

    private static AppController sInstance;

    /**
     * Server config is the object holding the response of the server request
     */
    private List<DataSet> dataSets;

    @Override
    public void onCreate() {
        super.onCreate();

        dataSets = new ArrayList<>();

        sInstance = this;
    }

    /**
     * Returns the singleton instance for application
     * @return
     */
    public static synchronized AppController getInstance() {
        return sInstance;
    }

    /**
     * Sets the server config
     * @param dataSets
     */
    public void setDataSets(List<DataSet> dataSets) {
        this.dataSets = dataSets;
    }

    /**
     * Returns the server config
     * @return
     */
    public List<DataSet> getDataSets() {
        return dataSets;
    }

}
