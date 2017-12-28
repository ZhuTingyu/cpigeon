package com.cpigeon.app.sign;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.view.materialcalendarview.CalendarDay;
import com.cpigeon.app.view.materialcalendarview.EventDecorator;
import com.cpigeon.app.view.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Zhu TingYu on 2017/12/27.
 */

public class SignFragment extends BaseMVPFragment {

    MaterialCalendarView calendarView;
    String[] weekFormat = {"一","二","三","四","五","六","七"};

    ImageView topGif;
    ImageView topImg;
    RelativeLayout rlTop;

    GridView gridView;

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected boolean isCanDettach() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_sign_layout;
    }

    @Override
    public void finishCreateView(Bundle state) {

        setTitle("签到");

        topGif = findViewById(R.id.top_gif);
        topImg = findViewById(R.id.top_img);
        rlTop = findViewById(R.id.rl_top);
        calendarView = findViewById(R.id.calendar);

        topGif.setImageResource(R.drawable.sign_top_anim);
        AnimationDrawable animationDrawable = (AnimationDrawable) topGif.getDrawable();
        animationDrawable.start();


        calendarView.setShowOtherDates(MaterialCalendarView.SHOW_NONE);

        Calendar instance = Calendar.getInstance();
        calendarView.setSelectedDate(instance.getTime());
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);

        calendarView.setHeaderTextAppearance(R.style.CalendarText);
        calendarView.setDateTextAppearance(R.style.CalendarText);
        calendarView.setWeekDayTextAppearance(R.style.CalendarText);
        calendarView.setWeekDayLabels(weekFormat);

        calendarView.setTopTitleColor(getResources().getColor(R.color.colorPrimary));
        calendarView.dissTopButtom();
        Calendar instance1 = Calendar.getInstance();
        instance1.set(instance1.get(Calendar.YEAR), Calendar.JANUARY, 1);

        Calendar instance2 = Calendar.getInstance();
        instance2.set(instance2.get(Calendar.YEAR), Calendar.DECEMBER, 31);

        calendarView.state().edit()
                .setMinimumDate(instance1.getTime())
                .setMaximumDate(instance2.getTime())
                .commit();

        bindData();

        rlTop.setOnClickListener(v -> {
            topGif.setVisibility(View.GONE);
            topImg.setVisibility(View.VISIBLE);
        });

    }

    private void bindData() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        ArrayList<CalendarDay> dates = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            CalendarDay day = CalendarDay.from(calendar);
            dates.add(day);
            calendar.add(Calendar.DATE, 5);
        }
        calendarView.addDecorator(new EventDecorator(Color.RED, dates));
    }

    private void initBottomImg(){

    }

}
