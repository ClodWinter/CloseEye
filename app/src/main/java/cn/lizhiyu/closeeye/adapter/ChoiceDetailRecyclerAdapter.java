package cn.lizhiyu.closeeye.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dyhdyh.adapters.AbstractRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.model.ChoiceItemModel;

public class ChoiceDetailRecyclerAdapter extends AbstractRecyclerAdapter<ChoiceItemModel, ChoiceDetailRecyclerAdapter.ItemViewHolder>
{
    private ArrayList<ChoiceItemModel> datas;

    public ChoiceDetailRecyclerAdapter(ArrayList<ChoiceItemModel> data)
    {
        super(data);

        datas = data;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position, ChoiceItemModel item)
    {

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.choicedetail_item_layout, parent, false));
    }

    protected static class ItemViewHolder extends RecyclerView.ViewHolder {

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

}
