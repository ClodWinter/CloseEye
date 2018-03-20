package cn.lizhiyu.closeeye.CustomView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import cn.lizhiyu.closeeye.adapter.ZYHFRecyclerAdapter;

/**
 * Created by king on 2018/3/20.
 */

public class ZYHFRecyclerView extends RecyclerView
{
    private ArrayList<View> mHeaderViewInfos=new ArrayList<>();

    private ArrayList<View> mFooterViewInfos=new ArrayList<>();

    private Adapter mAdapter;

    public ZYHFRecyclerView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void addHeaderView(View v) {
        mHeaderViewInfos.add(v);
    }

    public void addFooterView(View v) {
        mFooterViewInfos.add(v);
    }



    @Override
    public void setAdapter(Adapter adapter)
    {
        mAdapter = new ZYHFRecyclerAdapter(mHeaderViewInfos, mFooterViewInfos, adapter);

        super.setAdapter(mAdapter);
    }
}
