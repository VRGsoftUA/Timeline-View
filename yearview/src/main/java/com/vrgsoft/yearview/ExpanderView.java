package com.vrgsoft.yearview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ExpanderView extends LinearLayout implements View.OnClickListener {
    private List<TextView> mItemsText = new ArrayList<>();
    private Context mContext;
    private ItemHolder mLastItem;
    private OnClickPlus onClickPlusListener;
    private int relocationOffset;
    private int prevYear = 0;
    private List<View> viewList;
    private List<YearModel> mYearModels;
    private HashMap<YearModel, ItemHolder> mYearViews = new HashMap<>();
    private int mMinYear;
    private int mMaxYear;
    private int mYearTextSize = 9;
    private int mDescriptionTextSize = 8;
    private Calendar calendar = Calendar.getInstance();

    private int yeareMax = -1;

    private int lastYear;
    private int lastVisYear;
    private int firstYear;
    private int coordX;
    private
    @ColorRes
    int yearLineColor;
    @ColorRes
    int yearTitleColor;

    public List<YearModel> getCarModel() {
        return mYearModels;
    }

    public void setMaxYear(int maxYear) {
        mMaxYear = maxYear;
    }

    public void setMinYear(int minYear) {
        mMinYear = minYear;
    }

    public void setYearLineColor(@ColorRes int yearLineColor) {
        this.yearLineColor = yearLineColor;
    }

    public void setYearTextSize(int yearTextSize) {
        mYearTextSize = yearTextSize;
    }

    public void setDescriptionTextSize(int descriptionTextSize) {
        mDescriptionTextSize = descriptionTextSize;
    }

    public void setYearTitleColor(int yearTitleColor) {
        this.yearTitleColor = yearTitleColor;
    }

    public void setYearModel(List<YearModel> carModel) {
        this.mYearModels = carModel;
        for (int i = 0; i < carModel.size(); i++) {
            ItemHolder item = new ItemHolder(mContext, this);
            YearModel car = carModel.get(i);
            prevYear = car.start_date();
            int width = ((car.end_date() > calendar.get(Calendar.YEAR)
                    ? calendar.get(Calendar.YEAR) : car.end_date())
                    - car.start_date()) * Utils.getColumnWidth(mContext)
                    + (Utils.getColumnWidth(mContext) - 50);
            int margin = (car.start_date() - mMinYear) * Utils.getColumnWidth(mContext) + 37;
            LayoutParams params = new LayoutParams(width, 40);
            params.setMargins(margin, 15, 0, 0);
            item.view.setLayoutParams(params);
            item.name.setText(car.title());
            item.year.setText(String.format(" ` %s", car.description()));
            item.name.setTextSize(TypedValue.COMPLEX_UNIT_PX, mYearTextSize);
            item.year.setTextSize(TypedValue.COMPLEX_UNIT_PX, mDescriptionTextSize);

            if (yearLineColor != 0) {
                item.yearLine.setBackgroundColor(ContextCompat.getColor(mContext, yearLineColor));
            }
            if (yearTitleColor != 0) {
                item.name.setTextColor(ContextCompat.getColor(mContext, yearTitleColor));
                item.year.setTextColor(ContextCompat.getColor(mContext, yearTitleColor));
            }
            item.name.setGravity(Gravity.CENTER_VERTICAL);
            mYearViews.put(carModel.get(i), item);
        }

    }

    public ExpanderView(Context context) {
        super(context);
        if (!this.isInEditMode()) {
            mContext = context;
            init();
        }
    }

    public ExpanderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!this.isInEditMode()) {
            mContext = context;
            init();
        }
    }

    public ExpanderView(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        if (!this.isInEditMode()) {
            mContext = context;
            init();
        }
    }

    private void init() {
        setOrientation(VERTICAL);
        viewList = new ArrayList<>();
        addItem();
    }

    @Override
    public void onClick(View view) {
        addItem();
    }


    private void addItem() {
        int count = 0;
        if (mYearModels != null) {

            removeAllViews();
            viewList.clear();
            for (int i = 0; i < mYearModels.size(); i++) {
                if ((mYearModels.get(i).start_date() < firstYear && mYearModels.get(i).end_date() > lastVisYear)
                        || (mYearModels.get(i).end_date() > firstYear && mYearModels.get(i).end_date() < lastVisYear)
                        || (mYearModels.get(i).start_date() < firstYear - 1
                        && mYearModels.get(i).end_date() > firstYear - 1 && mYearModels.get(i).start_date() < lastVisYear)
                        || (mYearModels.get(i).start_date() < lastVisYear && mYearModels.get(i).end_date() > lastVisYear)
                        || (mYearModels.get(i).start_date() <= firstYear && mYearModels.get(i).end_date() >= firstYear)
                        || (mYearModels.get(i).start_date() <= lastVisYear + 1 && mYearModels.get(i).end_date() >= lastVisYear)
                        || (mYearModels.get(i).start_date() >= firstYear && mYearModels.get(i).end_date() <= lastVisYear)
                        || (mYearModels.get(i).start_date() >= firstYear && mYearModels.get(i).start_date() < lastVisYear)) {
                    YearModel model = mYearModels.get(i);
                    prevYear = model.start_date();
                    LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 100);


                    if (firstYear >= model.start_date()) {

                        int padd = coordX - (model.start_date() - mMinYear) * Utils.getColumnWidth(mContext);
                        if (padd + 60 > mYearViews.get(mYearModels.get(i)).view.getWidth()) {

                            mYearViews.get(mYearModels.get(i)).name.setPadding(padd - mYearViews.get(mYearModels.get(i)).name.getWidth(), 0, 0, 0);
                        } else {
                            mYearViews.get(mYearModels.get(i)).name.setPadding(padd, 0, 0, 0);
                        }
                    } else {
                        mYearViews.get(mYearModels.get(i)).name.setPadding(10, 0, 0, 0);
                    }


                    params.setMargins(40, 0, 0, 0);
                    mYearViews.get(mYearModels.get(i)).name.setLayoutParams(params);

                    addView(mYearViews.get(mYearModels.get(i)).view, 0);
                    viewList.add(0, mYearViews.get(mYearModels.get(i)).view);
                    lastYear = mYearModels.get(i).start_date();
                    count++;
                    Log.d("COUNT", String.valueOf(count));
                }
                if (mYearModels.get(i).start_date() > lastVisYear + 2) {
                    break;
                }
            }

        }
        Log.d("ExpanderView", "Added");
    }

    public List<TextView> getItems() {
        return mItemsText;
    }

    public String[] getStringFromList() {

        int size = mItemsText.size();
        String[] text = new String[size];
        for (int i = 0; i < size; i++) {
            if (mItemsText.get(i).getText().length() > 0) {
                text[i] = mItemsText.get(i).getText().toString();
            }
        }

        return text;

    }


    public void setOnClickPlusListener(OnClickPlus onClickPlusListener) {
        this.onClickPlusListener = onClickPlusListener;
    }

    public void setPadX(int scrollX) {
        coordX = scrollX;
    }

    public interface OnClickPlus {
        public void onClickPlus(String last);
    }

    private static class ItemHolder {
        protected final View view;
        protected final TextView name;
        protected final TextView year;
        private final LinearLayout yearLine;

        public ItemHolder(Context context, ExpanderView group) {
            //View view = View.inflate(context, R.layout.item_expander_view, group);
            View view = ((Activity) context).getLayoutInflater().inflate(R.layout.item_expander_view, null);
            name = (TextView) view.findViewById(R.id.tx_name);
            year = (TextView) view.findViewById(R.id.tx_year);
            yearLine = (LinearLayout) view.findViewById(R.id.year_line);
            this.view = view;
        }
    }

    public int getMaxCount() {
        return yeareMax;
    }

    public void setMaxCount(int maxCount) {
//        this.yeareMax = maxCount;
//        addItem();

    }

    public void setYears(int first, int last) {
        firstYear = first;
        lastVisYear = last;
        addItem();
    }

    private boolean isOnScreen(View v, Rect scrollBounds) {
        return v.getGlobalVisibleRect(scrollBounds);
    }

    private int getXInWindow(View v) {
        int[] coords = new int[2];
        v.getLocationInWindow(coords);
        return coords[0];
    }
}
