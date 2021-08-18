# SliderView
给ViewPager加一个带滑动指示器的标题

先看效果（有两种滑动效果）：

<img width="300px" src="https://github.com/Dahuoji-Coder/SliderView/blob/main/slider_animation_normal.gif?raw=true" />
<img width="300px" src="https://github.com/Dahuoji-Coder/SliderView/blob/main/slider_animation_spread.gif?raw=true" />

布局代码：
```java
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <com.dahuoji.slider.SliderView
        android:id="@+id/sliderView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

    <androidx.viewpager.widget.ViewPager
        android:id="@+id/viewPager"
        android:layout_width="match_parent"
        android:layout_below="@id/sliderView"
        android:layout_height="match_parent" />

</RelativeLayout>
```
Activity中的逻辑：
```java
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
        sliderView.bindViewPager(viewPager, SliderView.ANIMATION_NORMAL);
        //sliderView.bindViewPager(viewPager, SliderView.ANIMATION_SPREAD);
        //fragmentList的数量是4个,所以需要4个标题,要对应
        sliderView.refreshData(new String[]{"标题一", "标题二", "标题三", "title4"}, 50, Color.BLACK);
    }

}
```
具体到文字颜色，指示器外观等的调整可以自行去`SliderView`中修改。



