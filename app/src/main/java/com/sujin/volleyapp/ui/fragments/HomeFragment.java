/**
 * HomeFragment.java
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

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sujin.volleyapp.R;
import com.sujin.volleyapp.ui.adapters.HomeAdapter;

/**
 * Created by sujun.n on 01/08/17.
 */
public class HomeFragment extends ToolbarFragment {

    @Override
    protected void initViews() {
        setupToolBar(getString(R.string.title_home), true);

        mLayoutStub.setLayoutResource(R.layout.content_home);
        mLayoutStub.inflate();

        RecyclerView recyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(new HomeAdapter(getActivity()));

    }
}
