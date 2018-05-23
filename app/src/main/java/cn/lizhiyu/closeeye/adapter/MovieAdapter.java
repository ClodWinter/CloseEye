package cn.lizhiyu.closeeye.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cn.lizhiyu.closeeye.R;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter
{
    private ArrayList datas;

    private Context context;


    public MovieAdapter(ArrayList list, Context contextSupper)
    {
        datas = list;

        context = contextSupper;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movieitem_layout,null);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        ItemViewHolder itemViewHolder = (ItemViewHolder)holder;

        Glide.with(context).load("").bitmapTransform(new RoundedCornersTransformation(context,15,0,RoundedCornersTransformation.CornerType.ALL)).into(itemViewHolder.imageViewBg);
    }

    @Override
    public int getItemCount()
    {
        return datas.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageViewBg;

        public ImageView imageViewCover;

        public TextView textViewTitle;

        public TextView textViewDate;

        public TextView textViewDoubanScore;

        public TextView textViewIMDbScore;

        public ItemViewHolder(View itemView)
        {
            super(itemView);

            imageViewBg = itemView.findViewById(R.id.movieitem_bg);

            imageViewCover = itemView.findViewById(R.id.movieitem_cover);

            textViewTitle = itemView.findViewById(R.id.movieitem_title);

            textViewDate = itemView.findViewById(R.id.movieitem_date);

            textViewDoubanScore = itemView.findViewById(R.id.movieitem_douban_score);

            textViewIMDbScore = itemView.findViewById(R.id.movieitem_imdb_score);
        }
    }
}
