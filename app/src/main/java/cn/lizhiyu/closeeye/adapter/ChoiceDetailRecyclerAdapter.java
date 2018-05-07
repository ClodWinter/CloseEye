package cn.lizhiyu.closeeye.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dyhdyh.adapters.AbstractRecyclerAdapter;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.model.ChoiceItemModel;
import cn.lizhiyu.closeeye.model.VideoModel;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ChoiceDetailRecyclerAdapter extends AbstractRecyclerAdapter<VideoModel, ChoiceDetailRecyclerAdapter.ItemViewHolder>
{
    private ArrayList<VideoModel> datas;

    private Context context;

    public ChoiceDetailRecyclerAdapter(ArrayList<VideoModel> data, Context contextSupper)
    {
        super(data);

        datas = data;

        context = contextSupper;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position, VideoModel item)
    {
        Glide.with(context).load(item.coverUrl).bitmapTransform(new RoundedCornersTransformation(context,10,0,RoundedCornersTransformation.CornerType.ALL)).into(holder.imageView);

        holder.textViewTitle.setText(item.title);

        holder.textViewPlayTime.setText(item.durationMin);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.choicedetail_item_layout, parent, false));
    }

    protected static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageView;

        public TextView textViewPlayTime;

        public TextView textViewTitle;

        public TextView textViewTag;

        public Button buttonShare;

        public ItemViewHolder(View itemView)
        {
            super(itemView);

            imageView = itemView.findViewById(R.id.choicedetail_item_icon);

            textViewPlayTime = itemView.findViewById(R.id.choicedetail_item_playTime);

            textViewTitle = itemView.findViewById(R.id.choicedetail_item_title);

            textViewTag = itemView.findViewWithTag(R.id.choicedetail_item_tag);

            buttonShare =itemView.findViewById(R.id.choicedetail_item_shareBtn);
        }
    }

}
