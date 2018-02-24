package cn.lizhiyu.closeeye.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.dyhdyh.adapters.databinding.DataBindingListAdapter;
import com.dyhdyh.adapters.databinding.holder.DataBindingListHolder;

import java.util.List;

import cn.lizhiyu.closeeye.R;

public class DiscArrayAdapter extends ArrayAdapter
{

    public DiscArrayAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @Override
    public int getCount()
    {
        return 10;
    }

    @Nullable
    @Override
    public Object getItem(int position)
    {
        return super.getItem(position);
    }

    @Override
    public int getPosition(@Nullable Object item)
    {
        return super.getPosition(item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.disc_list_item,null);

        return view;
    }
}
