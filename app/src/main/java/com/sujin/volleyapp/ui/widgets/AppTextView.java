/**
 * AppTextView.java
 * <p>
 * Comment
 *
 * @category Global Analytics
 * @package com.sujin.volleyapp.ui
 * @version 1.0
 * @author Sujin.N
 * @copyright Copyright (C) 2016 Global Analytics. All rights reserved.
 */
package com.sujin.volleyapp.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.sujin.volleyapp.AppConstants;
import com.sujin.volleyapp.R;

/**
 * Created by sujun.n on 01/08/17.
 */
public class AppTextView extends AppCompatTextView {

    private Context mContext;

    private OnClickListener onClickListener;

    public AppTextView(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
        if (onClickListener != null) {
            this.onClickListener = onClickListener;
        }
    }

    public AppTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public AppTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * Loads the initial resource for the View.
     *
     * @param context  - The Context associated with the view.
     * @param attrs    - The AttributeSet associated with the view.
     * @param defStyle - The style associated with the view.
     */
    public void init(Context context, AttributeSet attrs, int defStyle) {
        if (!isInEditMode()) {
            mContext = context;
            TypedArray typedArray = getContext().getTheme()
                    .obtainStyledAttributes(attrs,
                            R.styleable.AppTextView, defStyle, 0);

            // Update the font
            String fontAssetName = typedArray.getString(R.styleable.AppTextView_fontSource);
            typedArray.recycle();
            if (!TextUtils.isEmpty(fontAssetName)) {
                setTypeFaceFromAsset(fontAssetName);
            }
            // Loads the default font.
            else {
                setTypeFaceFromAsset(AppConstants.FONT_OPEN_SANS_REGULAR);
            }
        }
    }

    /**
     * Set the font type face from the asset.
     *
     * @param fontAssetName the String refers the font asset file name.
     */
    private void setTypeFaceFromAsset(String fontAssetName) {
        Typeface typeFace = Typeface.createFromAsset(mContext
                .getAssets(), fontAssetName);
        setTypeface(typeFace);
    }

}
