package com.vrgsoft.yearview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ClickView extends LinearLayout implements View.OnClickListener {

    private Context mContext;
    private List<ItemHolder> views = new ArrayList<>();
    private ScrollPosition position;
    private OnRowClick onRowClick;
    private int checkedPosition = -1;
    private int mYearsCount;
    private int mMinYear;
    private static final int ID_DEFAULT = 999;
    private static final int LAYOUT_WIDTH = 900;

    public void setYearsCount(int yearsCount) {
        mYearsCount = yearsCount;
    }

    public void setMinYear(int minYear) {
        mMinYear = minYear;
    }

    public ClickView(Context context) {
        super(context);
        if (!this.isInEditMode()) {
            mContext = context;

        }
    }

    public ClickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!this.isInEditMode()) {
            mContext = context;
        }
    }

    public ClickView(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        if (!this.isInEditMode()) {
            mContext = context;
        }
    }

    public void init() {
        setOrientation(HORIZONTAL);
        addItem();
    }

    @Override
    public void onClick(View view) {

    }


    private void addItem() {
        setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        for (int i = 0; i < mYearsCount; i++) {
            final ItemHolder item = new ItemHolder(mContext, this);
            LayoutParams params = new LayoutParams(Utils.getColumnWidth(mContext), ViewGroup.LayoutParams.MATCH_PARENT);
            item.view.setLayoutParams(params);
            final int finalI = i;
            item.popUp.setId(ID_DEFAULT + i);
            item.view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRowClick != null) {
                        onRowClick.onClick(mMinYear + finalI, item);
                    }
                }
            });
            views.add(item);
            addView(item.view);
            Log.d("ClickView","Added");

        }

    }

    public class ItemHolder {
        protected final View view;
        public FrameLayout popUp;


        public ItemHolder(Context context, ClickView group) {
            //View view = View.inflate(context, R.layout.item_expander_view, group);
            view = ((Activity) context).getLayoutInflater().inflate(R.layout.click_layout, null);
            popUp = (FrameLayout) view.findViewById(R.id.llRoot);


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


    public interface ScrollPosition {
        void visiblePostion(int pos);
    }

    public void setOnRowClick(OnRowClick onRowClick) {
        this.onRowClick = onRowClick;
    }


    public void setCheckedPosition(int position) {
        if (checkedPosition != -1) {
            setNormal(checkedPosition);
        }
        this.checkedPosition = position;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LAYOUT_WIDTH, ViewGroup.LayoutParams.MATCH_PARENT);
        View view = views.get(checkedPosition).view;
        view.setLayoutParams(params);

    }

    public void setNormal(int position) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Utils.getColumnWidth(mContext),
                ViewGroup.LayoutParams.MATCH_PARENT);
        ItemHolder holder = views.get(position);
        holder.view.setLayoutParams(params);
        holder.popUp.removeAllViews();
        checkedPosition = -1;
    }

    public ItemHolder getItemHolderByPos(int i) {
        return views.get(i);
    }

}
