package com.everglow.wechat60ui.fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by EverGlow on 2018/8/24 15:32
 */

public    class TabFragment extends Fragment   {
    
    private String mTilte = "default";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         mTilte = (String) getArguments().get("mTitle");
        TextView textView = new TextView(getContext());
        textView.setTextSize(14f);
        textView.setTextColor(Color.parseColor("#ff333333"));
        textView.setText(mTilte);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
