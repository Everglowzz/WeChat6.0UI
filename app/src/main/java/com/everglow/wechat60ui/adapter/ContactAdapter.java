package com.everglow.wechat60ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.everglow.wechat60ui.R;

import java.util.List;


/**
 * Created by zhengping on 2017/1/16,15:23.
 *
 * 1、将复用缓存的工作隐藏起来了
 * 2、强大的LayoutManager
 * 3、item没有分割线
 *      a、在item的布局中加入分割线
 *      b、recyclerView.addItemDecoration();
 * 4、没有item的点击事件
 */

public class ContactAdapter extends RecyclerView.Adapter {

    private List<String> contacts;
    
    public void refresh(List<String> contacts){
        this.contacts = contacts;
        notifyDataSetChanged();
    }
    
    public ContactAdapter(List<String> contacts) {
        this.contacts = contacts;
    }

    //获取每一个条目的ViewHolder对象
    //在创建ViewHolder对象的时候，需要执行每一个item所需要展示的View对象
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建每一个item的布局view对象
        View view = View.inflate(parent.getContext(), R.layout.item_contact, null);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    //将某一个条目的ViewHolder中的控件进行数据的设置
    //holder就是onCreateViewHolder的返回值
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String contactName = contacts.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.tvContactName.setText(contactName);
        String firstLetter = contactName.substring(0, 1).toUpperCase();
        myViewHolder.tvLetter.setText(firstLetter);


        //动态显示字母
        if(position == 0) {
            myViewHolder.tvLetter.setVisibility(View.VISIBLE);
        } else {
            int prePosition = position - 1;
            String preContact = contacts.get(prePosition);
            String preLetter = preContact.substring(0, 1).toUpperCase();
            if(firstLetter.equals(preLetter)) {
                myViewHolder.tvLetter.setVisibility(View.GONE);
            } else {
                myViewHolder.tvLetter.setVisibility(View.VISIBLE);
            }
        }

        myViewHolder.itemView.setTag(contactName);
        myViewHolder.itemView.setOnClickListener(onClickListener);
        myViewHolder.itemView.setOnLongClickListener(onLongClickListener);

    }

    //将点击事件传递出去
    private View.OnClickListener onClickListener;
    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private View.OnLongClickListener onLongClickListener;

    public void setOnItemLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    static class MyViewHolder extends  RecyclerView.ViewHolder {

        TextView tvLetter;
        TextView tvContactName;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvLetter = (TextView) itemView.findViewById(R.id.tvLetter);
            tvContactName = (TextView) itemView.findViewById(R.id.tvContactName);
        }
    }
}
