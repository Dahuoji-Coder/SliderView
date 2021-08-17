package com.dahuoji.slider;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import org.w3c.dom.Text;

public class SliderView extends RelativeLayout {
    private Context context;
    private View indicator;
    private LinearLayout titleLayout;
    private int titleSpacing = 50;
    public static final int ANIMATION_NORMAL = 0;
    public static final int ANIMATION_SPREAD = 1;
    private ViewPager viewPager;

    public SliderView(Context context) {
        super(context);
        init(context);
    }

    public SliderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SliderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.slider_view, this, true);
        titleLayout = findViewById(R.id.titleLayout);
        indicator = findViewById(R.id.indicator);
    }

    public void bindViewPager(ViewPager viewPager, int indicatorAnimation) {
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (viewPager.getAdapter() == null || viewPager.getAdapter().getCount() == 1)
                    return;
                if (position + 1 >= titleLayout.getChildCount()) return;
                if (indicatorAnimation == ANIMATION_SPREAD) {
                    if (positionOffset != 0) {
                        if (viewPager.getCurrentItem() > position) {
                            //向左划
                            TextView currentItem = (TextView) titleLayout.getChildAt(viewPager.getCurrentItem());
                            TextView frontItem = (TextView) titleLayout.getChildAt(viewPager.getCurrentItem() - 1);
                            currentItem.setAlpha(0.5f + positionOffset * 0.5f);
                            if (viewPager.getCurrentItem() > 0) {
                                frontItem.setAlpha(0.5f + (1.0f - positionOffset) * 0.5f);
                                float leftMoveTotalLength = frontItem.getMeasuredWidth() + titleSpacing;
                                float rightMoveTotalLength = currentItem.getMeasuredWidth() + titleSpacing;
                                float totalMoveLength = leftMoveTotalLength + rightMoveTotalLength;
                                float move = (1 - positionOffset) * totalMoveLength;
                                float leftMargin = currentItem.getLeft() - move;
                                float rightMargin = titleLayout.getMeasuredWidth() - currentItem.getLeft() - currentItem.getMeasuredWidth();
                                if (leftMargin < frontItem.getLeft()) {
                                    leftMargin = frontItem.getLeft();
                                    rightMargin = titleLayout.getMeasuredWidth() - currentItem.getLeft() - currentItem.getMeasuredWidth() + (frontItem.getLeft() - leftMargin);
                                }
                                RelativeLayout.LayoutParams layoutParamsIndicator = (LayoutParams) indicator.getLayoutParams();
                                layoutParamsIndicator.leftMargin = (int) leftMargin;
                                layoutParamsIndicator.rightMargin = (int) rightMargin;
                                indicator.setLayoutParams(layoutParamsIndicator);
                            }
                        } else {
                            //向右划
                            TextView currentItem = (TextView) titleLayout.getChildAt(viewPager.getCurrentItem());
                            TextView behindItem = (TextView) titleLayout.getChildAt(viewPager.getCurrentItem() + 1);
                            currentItem.setAlpha(1.0f - positionOffset * 0.5f);
                            if (viewPager.getCurrentItem() < titleLayout.getChildCount() - 1) {
                                behindItem.setAlpha(0.5f + positionOffset * 0.5f);
                                float leftMoveTotalLength = currentItem.getMeasuredWidth() + titleSpacing;
                                float rightMoveTotalLength = behindItem.getMeasuredWidth() + titleSpacing;
                                float totalMoveLength = leftMoveTotalLength + rightMoveTotalLength;
                                float move = positionOffset * totalMoveLength;
                                float rightMargin = titleLayout.getMeasuredWidth() - currentItem.getLeft() - currentItem.getMeasuredWidth() - move;
                                float leftMargin = currentItem.getLeft();
                                if (rightMargin < titleLayout.getMeasuredWidth() - behindItem.getLeft() - behindItem.getMeasuredWidth()) {
                                    rightMargin = titleLayout.getMeasuredWidth() - behindItem.getLeft() - behindItem.getMeasuredWidth();
                                    leftMargin = currentItem.getLeft() + (titleLayout.getMeasuredWidth() - behindItem.getLeft() - behindItem.getMeasuredWidth() - rightMargin);
                                }
                                RelativeLayout.LayoutParams layoutParamsIndicator = (LayoutParams) indicator.getLayoutParams();
                                layoutParamsIndicator.leftMargin = (int) leftMargin;
                                layoutParamsIndicator.rightMargin = (int) rightMargin;
                                indicator.setLayoutParams(layoutParamsIndicator);
                            }
                        }
                    }
                } else {
                    int indicatorExtraMargin = 30;
                    float marginLeft, marginRight;
                    float leftMoveTotalLength = titleLayout.getChildAt(position).getMeasuredWidth() + titleSpacing;
                    marginLeft = titleLayout.getChildAt(position).getLeft() + leftMoveTotalLength * positionOffset;
                    float rightMoveTotalLength = titleLayout.getChildAt(position + 1).getMeasuredWidth() + titleSpacing;
                    marginRight = titleLayout.getMeasuredWidth() - titleLayout.getChildAt(position).getLeft() - titleLayout.getChildAt(position).getMeasuredWidth() - rightMoveTotalLength * positionOffset;
                    RelativeLayout.LayoutParams layoutParams = (LayoutParams) indicator.getLayoutParams();
                    layoutParams.leftMargin = (int) marginLeft + indicatorExtraMargin;
                    layoutParams.rightMargin = (int) marginRight + indicatorExtraMargin;
                    indicator.setLayoutParams(layoutParams);
                    if (positionOffset != 0) {
                        if (viewPager.getCurrentItem() > position) {
                            //向左划
                            titleLayout.getChildAt(viewPager.getCurrentItem()).setAlpha(0.5f + positionOffset * 0.5f);
                            if (viewPager.getCurrentItem() > 0) {
                                titleLayout.getChildAt(viewPager.getCurrentItem() - 1).setAlpha(0.5f + (1.0f - positionOffset) * 0.5f);
                            }
                        } else {
                            //向右划
                            titleLayout.getChildAt(viewPager.getCurrentItem()).setAlpha(1.0f - positionOffset * 0.5f);
                            if (viewPager.getCurrentItem() < titleLayout.getChildCount() - 1) {
                                titleLayout.getChildAt(viewPager.getCurrentItem() + 1).setAlpha(0.5f + positionOffset * 0.5f);
                            }
                        }
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (viewPager.getAdapter() == null || viewPager.getAdapter().getCount() == 1)
                    return;
                if (state == ViewPager.SCROLL_STATE_SETTLING) {
                    for (int i = 0; i < titleLayout.getChildCount(); i++) {
                        if (i == viewPager.getCurrentItem()) {
                            titleLayout.getChildAt(i).setAlpha(1.0f);
                        } else {
                            titleLayout.getChildAt(i).setAlpha(0.5f);
                        }
                    }
                }
            }
        });
    }

    public void refreshData(String[] titles, float textSize, int textColorNormal) {
        titleLayout.removeAllViews();
        for (int i = 0; i < titles.length; i++) {
            //标题
            TextView titleView = new TextView(context);
            titleView.setText(titles[i]);
            titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            titleView.setTextColor(textColorNormal);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                layoutParams.leftMargin = titleSpacing;
            } else {
                layoutParams.leftMargin = 0;
            }
            titleView.measure(MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            titleLayout.addView(titleView, layoutParams);
            int finalI = i;
            titleView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(finalI, true);
                }
            });
            if (i == 0) {
                titleView.setAlpha(1f);
            } else {
                titleView.setAlpha(0.5f);
            }
        }
        titleLayout.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) indicator.getLayoutParams();
        layoutParams.rightMargin = titleLayout.getMeasuredWidth() - titleLayout.getChildAt(0).getMeasuredWidth();
        indicator.setLayoutParams(layoutParams);
    }
}
