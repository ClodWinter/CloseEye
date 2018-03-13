package cn.lizhiyu.closeeye.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dyhdyh.adapters.AbstractRecyclerAdapter;

import java.util.List;

import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.model.PairingItemModel;

public class PairingAdapter extends AbstractRecyclerAdapter<PairingItemModel, PairingAdapter.ItemViewHolder> {

    public PairingAdapter(List<PairingItemModel> data) {

        super(data);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position, PairingItemModel item)
    {

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.follow_pairingitem_layout,null);

        ItemViewHolder holder = new ItemViewHolder(view);

        return holder;
    }

    protected static class ItemViewHolder extends RecyclerView.ViewHolder
    {

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

}
