package com.everglow.wechat60ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.everglow.wechat60ui.fragment.TabFragment;
import com.everglow.wechat60ui.widget.ChangeColorIcon;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private List<Fragment> mTabs = new ArrayList<Fragment>();
    private String[] mTitles = new String[]{"First Fragment !", "Second Fragment !", "Third Fragment !", "Fourth Fragment !"};
    private List<ChangeColorIcon> mTabIndicators = new ArrayList<ChangeColorIcon>();
    private ViewPager mViewPager;
    private FragmentPagerAdapter mFragmentPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDatas();
        initEvent();
    }

    private void initEvent() {

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("MainActivity", "position:" + position);
                Log.d("MainActivity", "positionOffset:" + positionOffset);
                //当positionOffset 等于0.0的时候 position+1 会出现越界
                if (positionOffset > 0) {
                    ChangeColorIcon left = mTabIndicators.get(position);
                    ChangeColorIcon rirht = mTabIndicators.get(position + 1);
                    //左边随着positionOffset 变大 越来越暗
                    left.setAlpha(1 - positionOffset);
                    rirht.setAlpha(positionOffset);
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        mViewPager = findViewById(R.id.viewpager);
        ChangeColorIcon one = findViewById(R.id.id_indicator_one);
        ChangeColorIcon two = findViewById(R.id.id_indicator_two);
        ChangeColorIcon three = findViewById(R.id.id_indicator_three);
        ChangeColorIcon four = findViewById(R.id.id_indicator_four);

        mTabIndicators.add(one);
        mTabIndicators.add(two);
        mTabIndicators.add(three);
        mTabIndicators.add(four);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);

        one.setAlpha(1.0f);


    }

    private void initDatas() {

        for (String s : mTitles) {
            TabFragment fragment = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putString("mTitle", s);
            fragment.setArguments(bundle);
            mTabs.add(fragment);
        }

        mFragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mTabs.get(position);
            }

            @Override
            public int getCount() {
                return mTabs.size();
            }
        };
        mViewPager.setAdapter(mFragmentPagerAdapter);

    }


    @Override
    public void onClick(View v) {
        TabClick(v);
    }

    /**
     * Tab点击事件
     */
    private void TabClick(View v) {
        resetOtherTabs();
        switch (v.getId()) {
            case R.id.id_indicator_one:

                mViewPager.setCurrentItem(0, false);
                mTabIndicators.get(0).setAlpha(1.0f);

                break;
            case R.id.id_indicator_two:
                mViewPager.setCurrentItem(1, false);
                mTabIndicators.get(1).setAlpha(1.0f);
                break;
            case R.id.id_indicator_three:
                mViewPager.setCurrentItem(2, false);
                mTabIndicators.get(2).setAlpha(1.0f);
                break;
            case R.id.id_indicator_four:
                mViewPager.setCurrentItem(3, false);
                mTabIndicators.get(3).setAlpha(1.0f);
                break;
        }
    }

    /**
     * 清空所有Tab颜色
     */
    private void resetOtherTabs() {
        for (ChangeColorIcon tab : mTabIndicators) {
            tab.setAlpha(0f);
        }
    }
}
