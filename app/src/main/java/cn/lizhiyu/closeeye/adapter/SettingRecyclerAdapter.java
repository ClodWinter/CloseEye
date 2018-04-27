package cn.lizhiyu.closeeye.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.logging.Logger;
import java.util.zip.Inflater;

import cn.lizhiyu.closeeye.R;

public class SettingRecyclerAdapter extends RecyclerView.Adapter
{
    public SettingRecyclerAdapter()
    {
        Log.d("11", "SettingRecyclerAdapter: ");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {

    }

    @Override
    public int getItemViewType(int position)
    {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_recycleritem_layout,null);

        return new ItemViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder
    {
        public ItemViewHolder(View itemView)
        {
            super(itemView);
        }
    }
}
