package com.vrgsoft.yearview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class YearView extends LinearLayout implements View.OnClickListener {

    private Context mContext;
    private List<View> views = new ArrayList<>();
    private int checkedPosition = -1;
    private int mMaxYear;
    private int mMinYear;
    private static final int LAYOUT_WIDTH = 900;
    private List<YearModel> mYearModels;
    private int mRowTextSize = 14;
    private
    @ColorRes
    int mYearRowColor;

    public void setYearRowColor(int yearRowColor) {
        mYearRowColor = yearRowColor;
    }

    public YearView(Context context) {
        super(context);
        if (!this.isInEditMode()) {
            mContext = context;

        }
    }

    public YearView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!this.isInEditMode()) {
            mContext = context;
        }
    }

    public YearView(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        if (!this.isInEditMode()) {
            mContext = context;
        }
    }

    public void setYearModels(List<YearModel> yearModels) {
        mYearModels = yearModels;
    }

    public void init() {
        setOrientation(HORIZONTAL);
        addItem();
    }

    @Override
    public void onClick(View view) {

    }

    public void setRowTextSize(int rowTextSize) {
        mRowTextSize = rowTextSize;
    }

    private void addItem() {
        setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        for (int i = 0; i < (mMaxYear - mMinYear) ; i++) {
            ItemHolder item = new ItemHolder(mContext, this);
            LayoutParams params = new LayoutParams(Utils.getColumnWidth(mContext), ViewGroup.LayoutParams.MATCH_PARENT);
            item.view.setLayoutParams(params);
            item.name.setTextColor(ContextCompat.getColor(mContext, mYearRowColor));
            item.name.setText(mMinYear + i + "");
            item.name.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRowTextSize);
            if (mYearModels.size() != 0 && mYearModels.get(i).image() != null) {
                item.CardView1.setVisibility(VISIBLE);
                if (mContext != null) {
                    try {
                        Picasso.with(mContext).load(mYearModels.get(0).image()).into(item.container1);
                    } catch (IllegalArgumentException e) {
                        Log.e("YearView", "Error");
                    }

                }
            }
            views.add(item.view);
            addView(item.view);
            Log.d("YearView", "Added");
        }

    }

    private static class ItemHolder {
        protected final View view;
        protected final TextView name;
        final ImageView container1;
        final ImageView container2;
        final CardView CardView1;
        final CardView CardView2;

        ItemHolder(Context context, YearView group) {
            //View view = View.inflate(context, R.layout.item_expander_view, group);
            View view = ((Activity) context).getLayoutInflater().inflate(R.layout.year_layout, null);
            container1 = (ImageView) view.findViewById(R.id.image_container1);
            container2 = (ImageView) view.findViewById(R.id.image_container2);
            CardView1 = (CardView) view.findViewById(R.id.image_card1);
            CardView2 = (CardView) view.findViewById(R.id.image_card2);
            name = (TextView) view.findViewById(R.id.tx_year);


            this.view = view;
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.d("MyPos", l + " " + t);
    }

    private boolean isOnScreen(View v, Rect scrollBounds) {
        return v.getGlobalVisibleRect(scrollBounds);
    }

    private int getXInWindow(View v) {
        int[] coords = new int[2];
        v.getLocationInWindow(coords);
        return coords[0];
    }


    public interface ScrolPosition {
        void visiblePostion(int pos);
    }


    public void onDestroy() {

    }


    public void setCheckedPosition(int position) {
        if (checkedPosition != -1) {
            setNormal(checkedPosition);
        }
        this.checkedPosition = position;
        LayoutParams params = new LayoutParams(LAYOUT_WIDTH, ViewGroup.LayoutParams.MATCH_PARENT);
        View view = views.get(checkedPosition);
        view.setLayoutParams(params);

    }

    public void setNormal(int position) {
        LayoutParams params = new LayoutParams(Utils.getColumnWidth(mContext), ViewGroup.LayoutParams.MATCH_PARENT);
        View view = views.get(position);
        view.setLayoutParams(params);
        checkedPosition = -1;
    }

    public void setMaxYear(int maxYear) {
        mMaxYear = maxYear;
    }

    public void setMinYear(int minYear) {
        mMinYear = minYear;
    }
}
