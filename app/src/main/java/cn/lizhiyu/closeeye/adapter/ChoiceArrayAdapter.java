package cn.lizhiyu.closeeye.adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.model.VideoModel;

import static android.support.v4.view.PagerAdapter.POSITION_NONE;

/**
 * Created by lizhiyu on 2017/5/3.
 */

public class ChoiceArrayAdapter extends ArrayAdapter
{
    private List listData;

    private int layoutId;

    public ChoiceArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects)
    {
        super(context, resource, objects);

        listData = objects;

        layoutId = resource;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View view = LayoutInflater.from(getContext()).inflate(layoutId,null);

        ImageView imageView = (ImageView)view.findViewById(R.id.choiceitem_cover);

        VideoModel model = (VideoModel)listData.get(position);

        Picasso.with(getContext()).load(model.getCoverUrl()).into(imageView);

        TextView textViewTitle = (TextView)view.findViewById(R.id.choiceitem_text);

        textViewTitle.setText(model.getTitle());

        TextView textViewDesc = (TextView)view.findViewById(R.id.choiceitem_desctext);

        textViewDesc.setText(model.getPosterScreenName()+" "+"/"+" "+model.getDurationMin());

        return view;
    }

    @Override
    public int getCount()
    {
        return listData.size();
    }

    @Nullable
    @Override
    public Object getItem(int position)
    {
        return listData.get(position);
    }

    @Override
    public int getPosition(@Nullable Object item)
    {
        return POSITION_NONE;
    }
}
