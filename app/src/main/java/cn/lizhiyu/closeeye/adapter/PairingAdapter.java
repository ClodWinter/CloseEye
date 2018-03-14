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
import android.widget.TextView;

import com.dyhdyh.adapters.AbstractRecyclerAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

        public ItemViewHolder(View itemView)
        {
            super(itemView);

            imageView = itemView.findViewById(R.id.pairingitem_icon);

            textViewName = itemView.findViewById(R.id.pairingitem_name);

            textViewPlace = itemView.findViewById(R.id.pairingitem_place);

            textViewSign = itemView.findViewById(R.id.pairingitem_signature);
        }
    }
}
