package cn.lizhiyu.closeeye.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import cn.lizhiyu.closeeye.CustomView.MdiRecomendView;
import cn.lizhiyu.closeeye.R;

public class MdiRecyclerAdapter extends RecyclerView.Adapter
{
    public MdiRecyclerAdapter()
    {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mdi_recomend_item,null);

        return new RecomendHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        RecomendHolder recomendHolder = (RecomendHolder) holder;

        recomendHolder.imageViewCover.setBackgroundResource(R.mipmap.choiceitem_cover);

        recomendHolder.textViewTitle.setText("真相漩涡" + position);
    }

    @Override
    public int getItemCount()
    {
        return 20;
    }

    public class RecomendHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageViewCover;

        public TextView textViewTitle;

        public RecomendHolder(View itemView)
        {
            super(itemView);

            imageViewCover = itemView.findViewById(R.id.mdi_recommend_item_cover);

            textViewTitle = itemView.findViewById(R.id.mdi_recommend_item_title);
        }
    }
}
