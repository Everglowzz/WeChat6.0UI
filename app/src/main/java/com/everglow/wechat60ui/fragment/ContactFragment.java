package com.everglow.wechat60ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.everglow.wechat60ui.R;
import com.everglow.wechat60ui.adapter.ContactAdapter;
import com.everglow.wechat60ui.widget.QuickSearchBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;


/**
 * Created by zhengping on 2017/1/15,16:29.
 */

public class ContactFragment extends Fragment implements QuickSearchBar.OnLetterChangedListener {

    private ArrayList<String> mContacts = new ArrayList<>();

    private HashMap<String, Integer> sectionHashMap = new HashMap<>();
    private RecyclerView mRecycleView;
    private QuickSearchBar mSerachBar;
    private ContactAdapter mAdapter;

    private static final String[] SECTIONS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private TextView mTips;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("ContactFragment.onCreateView");
        View view = View.inflate(getContext(), R.layout.fragment_contact, null);
        mRecycleView = view.findViewById(R.id.recyclerView);
        mSerachBar = view.findViewById(R.id.quickSearchBar);
        mTips = view.findViewById(R.id.tvTips);
        view.findViewById(R.id.recyclerView).setEnabled(false);
        initView();
        initData();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initView() {
        mAdapter = new ContactAdapter(mContacts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
        mSerachBar.setOnLetterChangedListener(this);

    }

    private void initData() {
        for (int i = 0; i < 100; i++) {
            StringBuffer sb = new StringBuffer();
            for (int y = 0; y < new Random().nextInt(10) + 3; y++) {
                sb.append(SECTIONS[new Random().nextInt(SECTIONS.length )]);
            }
            mContacts.add(String.valueOf(sb));
        }
        Collections.sort(mContacts);
        for (int i = 0; i < mContacts.size(); i++) {
            String s = mContacts.get(i);
            String first = s.substring(0, 1).toUpperCase();
            if (!sectionHashMap.containsKey(first)) {
                Log.d("ContactFragment", "first:" + first);
                Log.d("ContactFragment", "i:" + i);
                sectionHashMap.put(first, i);
            }
        }

        mAdapter.refresh(mContacts);
    }


    @Override
    public void onLetterChanged(String letter) {
        mTips.setVisibility(View.VISIBLE);
        mTips.setText(letter);
        //发送隐藏Tip的延时消息，每次发送清空消息
        mHandler.removeMessages(0);
        mHandler.sendEmptyMessageDelayed(0,200);
        //需要判断当前数据是否包含滑中的字母
        if (sectionHashMap.containsKey(letter)) {
            mRecycleView.smoothScrollToPosition(sectionHashMap.get(letter));
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mTips.setVisibility(View.GONE);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler = null;
    }
}
