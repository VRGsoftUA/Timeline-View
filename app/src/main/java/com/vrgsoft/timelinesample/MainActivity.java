package com.vrgsoft.timelinesample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.vrgsoft.yearview.ClickView;
import com.vrgsoft.yearview.OnRowClick;
import com.vrgsoft.yearview.YearLayout;
import com.vrgsoft.yearview.YearModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnRowClick {
    private YearLayout mYearLayout;
    private List<YearModel> mModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mModels = new ArrayList<>();
        for (int i = 1860; i <= 2017; i++) {
            mModels.add(new SomeModel(i, i + new Random().nextInt(10),
                    "https://camo.mybb.com/e01de90be6012adc1b1701dba899491a9348ae79/687474703a2f2f7777772e6a71756572797363726970742e6e65742f696d616765732f53696d706c6573742d526573706f6e736976652d6a51756572792d496d6167652d4c69676874626f782d506c7567696e2d73696d706c652d6c69676874626f782e6a7067", String.valueOf(i)));
        }
        mYearLayout = (YearLayout) findViewById(R.id.year_layout);

        YearLayout.Builder builder = new YearLayout.Builder();
        builder.setYears(mModels)
                .setMaxYear(2017)
                .setMinYear(1860)
                .attachToActivity(this)
                .setOnRowClick(this)
                .setYearBackgroundColor(R.color.line_color)
                .setYearTitleColor(R.color.colorPrimary)
                .setYearRowTextColor(R.color.colorPrimaryDark)
                .create();
        mYearLayout.setBuilder(builder);
    }

    @Override
    public void onClick(int year, ClickView.ItemHolder view) {
        Toast.makeText(this, "Clicked on year " + year, Toast.LENGTH_SHORT).show();
    }
}
