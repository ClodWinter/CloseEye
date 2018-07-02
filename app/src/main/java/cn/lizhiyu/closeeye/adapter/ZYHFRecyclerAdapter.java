package cn.lizhiyu.closeeye.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.lizhiyu.closeeye.R;

/**
 * Created by king on 2018/3/20.
 */

public class ZYHFRecyclerAdapter extends RecyclerView.Adapter
{
    public static final int SHOW_HEADER=1;
    public static final int SHOW_FOOTER=2;
    private ArrayList<View> mHeaderViewInfos;
    private ArrayList<View> mFooterViewInfos;
    private RecyclerView.Adapter mAdapter;

    public ZYHFRecyclerAdapter(ArrayList<View> haderViewInfos, ArrayList<View> footerViewInfos, RecyclerView.Adapter mAdapter)
    {
        if (haderViewInfos == null) {
            mHeaderViewInfos = new ArrayList<View>();
        } else {
            mHeaderViewInfos = haderViewInfos;
        }

        if (footerViewInfos == null) {
            mFooterViewInfos = new ArrayList<View>();
        } else {
            mFooterViewInfos = footerViewInfos;
        }
        this.mAdapter=mAdapter;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemViewType(int position)
    {
        //header
        int numHeaders = mHeaderViewInfos.size();
        if (position < numHeaders) {
            return SHOW_HEADER;
        }
        // Adapter
        final int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItemViewType(adjPosition);
            }
        }
        // Footer (off-limits positions will throw an IndexOutOfBoundsException)
        return SHOW_FOOTER;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(viewType==SHOW_HEADER && mHeaderViewInfos.size()>0)
        {
            return new ItemViewHolder(mHeaderViewInfos.get(0));
        }

        if(viewType==SHOW_FOOTER && mFooterViewInfos.size()>0){
            return new ItemViewHolder(mFooterViewInfos.get(0));
        }

        return mAdapter.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        //也要划分三个区域
        int numHeaders = mHeaderViewInfos.size();
        if (position < numHeaders) {//是头部
            return;
        }
        //adapter body
        final int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                mAdapter.onBindViewHolder(holder, adjPosition);
                return;
            }
        }
        //footer
    }

    @Override
    public int getItemCount()
    {
        if (mAdapter != null)
        {
            return mHeaderViewInfos.size() + mFooterViewInfos.size() + mAdapter.getItemCount();
        } else {
            return mHeaderViewInfos.size() + mFooterViewInfos.size();
        }
    }

    protected static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        public ItemViewHolder(View itemView)
        {
            super(itemView);
        }
    }
}
