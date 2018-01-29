package cn.lizhiyu.closeeye.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

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
        return super.getPosition(item);
    }
}
