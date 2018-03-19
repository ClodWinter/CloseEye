package cn.lizhiyu.closeeye.adapter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dyhdyh.adapters.AbstractRecyclerAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.lizhiyu.closeeye.CustomView.TagsView;
import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.model.PairingItemModel;

public class PairingAdapter extends AbstractRecyclerAdapter<PairingItemModel, PairingAdapter.ItemViewHolder>
{
    private ArrayList datas;

    private BitmapRegionDecoder mDecoder;

    private ViewGroup viewGroup;

    public PairingAdapter(ArrayList<PairingItemModel> data) {

        super(data);

        datas = data;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position, PairingItemModel item)
    {

        holder.imageView.setImageResource(item.icon);

        holder.textViewName.setText(item.userName);

        holder.textViewPlace.setText(item.place);

        holder.textViewSign.setText(item.signature);

        for (int i = 0; i < 3; i++)
        {
            TagsView tagsView = new TagsView(viewGroup.getContext(),null);

            tagsView.setId(i+200);

            if (i == 0)
            {
                tagsView.setData(item.sex,item.sex.equals("女")?0:1,"#fbb2c3");
            }else
            {
                tagsView.setData("瞎逼逼" +i,-1,"#d1a384");
            }

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            layoutParams.leftMargin = i==0?3:6;

            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);

            if (holder.tagsView.getChildCount()>0)
            {
                TagsView view = (TagsView) holder.tagsView.getChildAt(holder.tagsView.getChildCount()-1);

                layoutParams.addRule(RelativeLayout.RIGHT_OF,view.getId());

                layoutParams.leftMargin = 6;
            }

            tagsView.setLayoutParams(layoutParams);

            holder.tagsView.addView(tagsView);
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        viewGroup = parent;

        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.follow_pairingitem_layout,null);

        ItemViewHolder holder = new ItemViewHolder(view);

        return holder;
    }


    protected static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;

        TextView textViewName;

        TextView textViewPlace;

        TextView textViewSign;

        RelativeLayout tagsView;

        public ItemViewHolder(View itemView)
        {
            super(itemView);

            imageView = itemView.findViewById(R.id.pairingitem_icon);

            textViewName = itemView.findViewById(R.id.pairingitem_name);

            textViewPlace = itemView.findViewById(R.id.pairingitem_place);

            textViewSign = itemView.findViewById(R.id.pairingitem_signature);

            tagsView = itemView.findViewById(R.id.pairingitem_tags);
        }
    }
}
