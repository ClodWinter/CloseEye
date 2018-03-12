package cn.lizhiyu.closeeye.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dyhdyh.adapters.AbstractRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.model.FollowCardItemModel;

public class FollowCardAdapter extends AbstractRecyclerAdapter<FollowCardItemModel, FollowCardAdapter.ItemViewHolder> {

    private ArrayList datas;

    public FollowCardAdapter(ArrayList<FollowCardItemModel> data)
    {
        super(data);
        datas = data;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position, FollowCardItemModel item)
    {
        holder.imageView.setBackgroundResource(R.mipmap.sister);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.followcard_layout, parent, false));
    }

    protected static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;

        public ItemViewHolder(View itemView)
        {
            super(itemView);

            imageView = itemView.findViewById(R.id.followcard_imageview);
        }
    }

}
