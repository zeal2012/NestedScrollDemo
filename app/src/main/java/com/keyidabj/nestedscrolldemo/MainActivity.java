package com.keyidabj.nestedscrolldemo;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity_";

    protected LinearLayout ll_container;
    protected NestedParent mScrollView;
    protected ViewPager mViewPager;

    private List<Fragment> fragmentList = new ArrayList<>();
    private MyViewPagerAdapter mAdapter;
    private TextView tv_menu_1;
    private TextView tv_menu_2;


    int heightForScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        heightForScroll = DensityUtil.dip2px(this, 200 + 20);
        TLog.i(TAG, "heightForScroll:" + heightForScroll);

        ll_container = findViewById(R.id.ll_container);

        mScrollView = findViewById(R.id.nested_parent);
        mScrollView.setMaxHeight(heightForScroll);
        mScrollView.setOnScrollListener(myScrollListener);

        tv_menu_1 = findViewById(R.id.tv_menu_1);
        tv_menu_2 = findViewById(R.id.tv_menu_2);
        mViewPager = findViewById(R.id.viewpager);

        for (int i = 0; i < 4; i++) {
            MyFragment fragment = new MyFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }
        mAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mScrollView.removeRecyclerView();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ll_container.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                int containerHeight = ll_container.getMeasuredHeight();
                ll_container.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ViewGroup.LayoutParams layoutParamsPager = mViewPager.getLayoutParams();
                layoutParamsPager.height = containerHeight - DensityUtil.dip2px(MainActivity.this, 50 + 40);;
                mViewPager.setLayoutParams(layoutParamsPager);
                TLog.i(TAG, "containerHeight : " + containerHeight);
            }
        });
    }

    private NestedParent.OnScrollListener myScrollListener = new NestedParent.OnScrollListener() {

        @Override
        public void onScroll(int scrollY) {
            TLog.i(TAG, "scrollY: " + scrollY);
            if (scrollY >= heightForScroll) {
                tv_menu_1.setVisibility(View.GONE);
                tv_menu_2.setVisibility(View.VISIBLE);
            } else {
                tv_menu_1.setVisibility(View.VISIBLE);
                tv_menu_2.setVisibility(View.GONE);
            }
        }

    };

    private class MyViewPagerAdapter extends FragmentStatePagerAdapter {

        public MyViewPagerAdapter(FragmentManager childFragmentManager) {
            super(childFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }


}