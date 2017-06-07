package com.vrgsoft.yearview;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class YearLayout extends LinearLayout {
    private YearView mYearView;
    private ClickView mClickView;
    private ExpanderView mExpanderView;
    private HorizontalScrollView mScrollView;
    private Context mContext;
    private Activity mActivity;
    private int pos;

    public YearLayout(Context context) {
        super(context);
        mContext = context;
    }

    public YearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initAttributes(attrs);
    }

    private void initAttributes(AttributeSet attributeSet) {
        TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(attributeSet
                , R.styleable.YearLayout, 0, 0);
        try {
            Builder.mRowTextSize = (int) typedArray.getDimension(R.styleable.YearLayout_row_text_size, (float) 14);
            Builder.mDescriptionTextSize = (int) typedArray.getDimension(R.styleable.YearLayout_description_text_size, (float) 8);
            Builder.mYearTextSize = (int) typedArray.getDimension(R.styleable.YearLayout_year_text_size, (float) 9);
            Builder.mMinYear = typedArray.getInteger(R.styleable.YearLayout_min_year, 0);
            Builder.mMaxYear = typedArray.getInteger(R.styleable.YearLayout_max_year, 0);
            Builder.mYearTextRowColor = typedArray.getColor(R.styleable.YearLayout_year_row_text_color,
                    ContextCompat.getColor(mContext, android.R.color.black));
            Builder.mYearBackgroundColor = typedArray.getColor(R.styleable.YearLayout_year_background_color
                    , ContextCompat.getColor(mContext, android.R.color.darker_gray));
            Builder.mYearTitleColor = typedArray.getColor(R.styleable.YearLayout_year_title_color
                    , ContextCompat.getColor(mContext, android.R.color.black));
        } finally {
            typedArray.recycle();
        }
    }

    private void initViews(final Context context, final Builder builder) {
        for (int i = builder.getMinYear(); i <= builder.getMaxYear(); i++) {
            builder.getYearsInts().add(i);
        }
        mActivity = builder.getActivity();
        LayoutInflater.from(context).inflate(R.layout.year_view, this, true);

        mExpanderView = (ExpanderView) findViewById(R.id.view);
        mClickView = (ClickView) findViewById(R.id.click);
        mYearView = (YearView) findViewById(R.id.year);

        mExpanderView.setMinYear(builder.getMinYear());
        mExpanderView.setMaxYear(builder.getMaxYear());
        mExpanderView.setYearLineColor(builder.getYearBackgroundColor());
        mExpanderView.setYearTitleColor(builder.getYearTitleColor());
        mExpanderView.setDescriptionTextSize(builder.getDescriptionTextSize());
        mExpanderView.setYearTextSize(builder.getYearTextSize());
        mExpanderView.setYearModel(builder.getYears());
        mExpanderView.setYears(builder.getMinYear(), builder.getMinYear() + Utils.getXInWindow(builder.getActivity())
                / Utils.getColumnWidth(mContext));


        mClickView.setMinYear(builder.getMinYear());
        mClickView.setYearsCount(builder.getMaxYear() - builder.getMinYear());
        mClickView.setOnRowClick(builder.getOnRowClick());
        mClickView.init();


        mYearView.setMaxYear(builder.getMaxYear());
        mYearView.setMinYear(builder.getMinYear());
        mYearView.setRowTextSize(builder.getRowTextSize());
        mYearView.setYearModels(builder.getYears());
        mYearView.setYearRowColor(builder.getYearTextRowColor());
        mYearView.init();


        mScrollView = (HorizontalScrollView) findViewById(R.id.scrollView);
        mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (mScrollView != null) {
                    int scrollX = mScrollView.getScrollX();
                    mExpanderView.setPadX(scrollX);
                    pos = scrollX / Utils.getColumnWidth(context);
                    if (pos > 0 && pos < builder.getYears().size() - getXInWindow() / Utils.getColumnWidth(context)) {
                        mExpanderView.setYears(builder.getYearsInts().get(pos),
                                builder.getYearsInts().get(pos+getXInWindow() / (Utils.getColumnWidth(context))));
                        Log.d("POS", pos + getXInWindow() / (Utils.getColumnWidth(context)) + "");
                    }
                    if (pos == 0) {
                        mExpanderView.setYears(builder.getMinYear(), builder.getMinYear()
                                + getXInWindow() / (Utils.getColumnWidth(context)));
                    }
                }
            }
        });

    }

    private int getXInWindow() {
        Display display = mActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;

    }

    public void setBuilder(Builder builder) {
        initViews(mContext, builder);
    }

    public void setCheckedPosition(int position) {
        mYearView.setCheckedPosition(position);
        mClickView.setCheckedPosition(pos);
    }

    public void setNormal(int yearPosition) {
        mYearView.setNormal(yearPosition);
        mClickView.setNormal(yearPosition);
    }

    public static class Builder {
        private List<YearModel> mYears;
        private Activity mActivity;
        private List<Integer> mYearsInts = new ArrayList<>();
        private static int mMinYear;
        private static int mMaxYear;
        private OnRowClick mOnRowClick;
        private static int mYearBackgroundColor;
        private static int mYearTitleColor;
        private static int mYearTextRowColor;
        private static int mYearTextSize;
        private static int mDescriptionTextSize;
        private static int mRowTextSize;

        public Builder setYearTextSize(int yearTextSize) {
            mYearTextSize = yearTextSize;
            return this;
        }

        public Builder setDescriptionTextSize(int descriptionTextSize) {
            mDescriptionTextSize = descriptionTextSize;
            return this;
        }

        public Builder setYearTitleColor(@ColorRes int yearColor) {
            mYearTitleColor = yearColor;
            return this;
        }

        public Builder setYearRowTextColor(@ColorRes int yearRowTextColor) {
            mYearTextRowColor = yearRowTextColor;
            return this;
        }

        public Builder setYearBackgroundColor(@ColorRes int yearBackgroundColor) {
            mYearBackgroundColor = yearBackgroundColor;
            return this;
        }

        public Builder setYears(List<YearModel> years) {
            mYears = years;
            return this;
        }

        public Builder setMinYear(int minYear) {
            mMinYear = minYear;
            return this;
        }

        public Builder setMaxYear(int maxYear) {
            mMaxYear = maxYear;
            return this;
        }

        public Builder attachToActivity(Activity activity) {
            mActivity = activity;
            return this;
        }

        public Builder setOnRowClick(OnRowClick onRowClick) {
            mOnRowClick = onRowClick;
            return this;
        }

        public Builder setRowTextSize(int rowTextSize) {
            mRowTextSize = rowTextSize;
            return this;
        }

        public Builder create() {
            Collections.sort(mYears, new Comparator<YearModel>() {
                @Override
                public int compare(YearModel carModel, YearModel t1) {

                    if ((carModel.start_date() < t1.start_date()) && (carModel.end_date() < t1.end_date())) {
                        return -1;
                    } else if ((carModel.start_date() > t1.start_date()) && (carModel.end_date() > t1.end_date())) {
                        return 1;
                    } else {
                        return 0;
                    }

                }
            });
            if (mMaxYear < mMinYear) {
                throw new IllegalStateException("Max year cannot be less then min year");
            }
            return this;
        }

        List<YearModel> getYears() {
            return mYears;
        }

        Activity getActivity() {
            return mActivity;
        }

        List<Integer> getYearsInts() {
            return mYearsInts;
        }

        int getMinYear() {
            return mMinYear;
        }

        int getMaxYear() {
            return mMaxYear;
        }

        OnRowClick getOnRowClick() {
            return mOnRowClick;
        }

        int getYearBackgroundColor() {
            return mYearBackgroundColor;
        }

        int getYearTitleColor() {
            return mYearTitleColor;
        }

        int getYearTextRowColor() {
            return mYearTextRowColor;
        }

        int getYearTextSize() {
            return mYearTextSize;
        }

        int getDescriptionTextSize() {
            return mDescriptionTextSize;
        }

        int getRowTextSize() {
            return mRowTextSize;
        }
    }
}
