package cn.lizhiyu.closeeye.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.model.MineItemModel;

/**
 * Created by lizhiyu on 2017/8/27.
 */

public class MineArrayAdapter extends ArrayAdapter
{
    private List<MineItemModel> listModel;

    private int layoutId;

    public MineArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);

        listModel = objects;

        layoutId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View view = LayoutInflater.from(getContext()).inflate(layoutId,null);

        ImageView imageView = (ImageView)view.findViewById(R.id.mineitem_icon);

        TextView textView = (TextView)view.findViewById(R.id.mineitem_text);

        MineItemModel model = listModel.get(position);

        imageView.setImageResource(model.src);

        textView.setText(model.text);

        return view;
    }

    @Override
    public int getCount()
    {
        return listModel.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return listModel.get(position);
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return super.getPosition(item);
    }
}
