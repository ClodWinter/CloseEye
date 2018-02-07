package cn.lizhiyu.closeeye.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dyhdyh.adapters.AbstractRecyclerAdapter;

import java.util.List;

import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.model.MineRecylerModel;

public class MineNumerAdapter extends AbstractRecyclerAdapter<MineRecylerModel, MineNumerAdapter.ItemViewHolder>
{

    public List datas;

    public MineNumerAdapter(List<MineRecylerModel> data)
    {
        super(data);

        datas = data;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position, MineRecylerModel item)
    {
        holder.textViewTop.setText(item.number);

        holder.textViewBottom.setText(item.title);
    }

    @Override
    public int getItemCount()
    {
        return datas == null?0:datas.size();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_minerecyleradapter,parent,false);

        DisplayMetrics dm = parent.getContext().getResources().getDisplayMetrics();

        int with = dm.widthPixels;

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        layoutParams.width = (int) (with/4.0f);

        view.setLayoutParams(layoutParams);

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
