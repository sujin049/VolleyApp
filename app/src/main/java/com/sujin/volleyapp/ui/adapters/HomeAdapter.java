/**
 * HomeAdapter.java
 * <p>
 * Comment
 *
 * @category Global Analytics
 * @package com.sujin.volleyapp.ui.adapters
 * @version 1.0
 * @author Sujin.N
 * @copyright Copyright (C) 2016 Global Analytics. All rights reserved.
 */
package com.sujin.volleyapp.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.sujin.volleyapp.AppConstants;
import com.sujin.volleyapp.AppController;
import com.sujin.volleyapp.R;
import com.sujin.volleyapp.data.DataSet;
import com.sujin.volleyapp.ui.activities.ContentActivity;
import com.sujin.volleyhelper.helper.VolleyController;

import java.util.List;

/**
 * Created by sujun.n on 01/08/17.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private List<DataSet> dataList;
    private Context context;

    public HomeAdapter(Context context) {
        this.dataList = AppController.getInstance().getDataSets();
        this.context = context;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_home_list, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, final int position) {
        DataSet dataSet = dataList.get(position);
        if (dataSet != null) {
            ImageLoader imageLoader = VolleyController.getInstance().getImageLoader();
            holder.networkImageView.setImageUrl(dataSet.getUrls().getRegular(), imageLoader);

            holder.networkImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(AppConstants.EXTRA_ITEM_POSITION, position);
                    ContentActivity.launchContentActivity((Activity) context,
                            AppConstants.FRAGMENT_DETAILS, bundle);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {

        public NetworkImageView networkImageView;

        public HomeViewHolder(View view) {
            super(view);
            this.networkImageView = (NetworkImageView) view.findViewById(R.id.networkImageView);
        }

    }

}
