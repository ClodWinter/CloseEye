package cn.lizhiyu.closeeye.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.Inflater;

import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.model.SettingModel;

public class SettingRecyclerAdapter extends RecyclerView.Adapter
{
    private ArrayList datas;

    public SettingRecyclerAdapter(ArrayList list)
    {
        datas = list;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

        SettingModel model = (SettingModel) datas.get(position);

        if (model.getTag() == 1 || model.getTag() == 4)
        {
            itemViewHolder.aSwitch.setVisibility(View.VISIBLE);

            itemViewHolder.imageViewIcon.setVisibility(View.GONE);
        }
        else
        {
            itemViewHolder.aSwitch.setVisibility(View.GONE);
        }

        if (model.getContent().length()==0)
        {
            itemViewHolder.textViewContent.setVisibility(View.GONE);
        }
        else
        {
            itemViewHolder.textViewContent.setText(model.getContent());
        }

        if (model.getDesc().length() == 0)
        {
            itemViewHolder.textViewDesc.setVisibility(View.GONE);
        }
        else
        {
            itemViewHolder.textViewDesc.setText(model.getDesc());
        }

        itemViewHolder.textViewTitle.setText(model.getTitle());

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
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_recycleritem_layout,null);

        return new ItemViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewTitle;

        TextView textViewContent;

        TextView textViewDesc;

        Switch aSwitch;

        ImageView imageViewIcon;

        public ItemViewHolder(View itemView)
        {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.setting_item_title);

            textViewContent = itemView.findViewById(R.id.setting_item_content);

            textViewDesc = itemView.findViewById(R.id.setting_desc);

            aSwitch = itemView.findViewById(R.id.setting_switch);

            imageViewIcon = itemView.findViewById(R.id.setting_rightIcon);
        }
    }
}
