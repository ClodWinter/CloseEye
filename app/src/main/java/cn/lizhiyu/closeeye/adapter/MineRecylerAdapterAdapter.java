package cn.lizhiyu.closeeye.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dyhdyh.adapters.AbstractRecyclerAdapter;

import java.util.List;

import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.model.MineRecylerModel;

public class MineRecylerAdapterAdapter extends AbstractRecyclerAdapter<MineRecylerModel, MineRecylerAdapterAdapter.ItemViewHolder>
{

    public List datas;

    public MineRecylerAdapterAdapter(List<MineRecylerModel> data)
    {
        super(data);

        datas = data;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position, MineRecylerModel item)
    {

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_minerecyleradapter,parent,false);

        ItemViewHolder vh = new ItemViewHolder(view);

        return vh;
    }

    protected static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textViewTop;

        public TextView textViewBottom;

        public ItemViewHolder(View itemView)
        {
            super(itemView);

            textViewTop = (TextView)itemView.findViewById(R.id.minerecyler1_text1);

            textViewBottom = (TextView)itemView.findViewById(R.id.minerecyler1_text2);
        }
    }

}
