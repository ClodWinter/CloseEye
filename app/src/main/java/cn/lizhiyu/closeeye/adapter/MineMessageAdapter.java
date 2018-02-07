package cn.lizhiyu.closeeye.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyhdyh.adapters.AbstractRecyclerAdapter;

import java.util.List;

import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.model.MineMessageModel;

public class MineMessageAdapter extends AbstractRecyclerAdapter<MineMessageModel, MineMessageAdapter.ItemViewHolder>
{
    private List datas;

    public MineMessageAdapter(List data) {

        super(data);

        datas = data;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position, MineMessageModel item)
    {
        holder.messageIcon.setImageResource(item.src);

        holder.textViewTitle.setText(item.title);
    }

    @Override
    public int getItemCount()
    {
        return datas == null?0:datas.size();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mine_messageitem_layout,parent,false);

        DisplayMetrics dm = parent.getContext().getResources().getDisplayMetrics();

        int with = dm.widthPixels;

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        layoutParams.width = (int) (with/4.0f);

        view.setLayoutParams(layoutParams);

        ItemViewHolder vh = new ItemViewHolder(view);

        return vh;
    }

    protected static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView messageIcon;

        public TextView textViewTitle;

        public ItemViewHolder(View itemView)
        {
            super(itemView);

            messageIcon = (ImageView)itemView.findViewById(R.id.mine_head_messageIcon);

            textViewTitle = (TextView)itemView.findViewById(R.id.mine_head_messageTitle);
        }
    }

}
