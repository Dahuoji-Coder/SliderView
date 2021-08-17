package com.dahuoji.slider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SliderView sliderView = findViewById(R.id.sliderView);
        ViewPager viewPager = findViewById(R.id.viewPager);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new TestFragment());
        fragmentList.add(new TestFragment());
        fragmentList.add(new TestFragment());
        fragmentList.add(new TestFragment());
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(fragmentAdapter);
        //指示器有两种动画效果可选 SliderView.ANIMATION_NORMAL 和 SliderView.ANIMATION_SPREAD
        //sliderView.bindViewPager(viewPager, SliderView.ANIMATION_NORMAL);
        sliderView.bindViewPager(viewPager, SliderView.ANIMATION_SPREAD);
        //fragmentList的数量是4个,所以需要4个标题,要对应
        sliderView.refreshData(new String[]{"标题一", "标题二", "标题三", "title4"}, 50, Color.BLACK);
    }

}