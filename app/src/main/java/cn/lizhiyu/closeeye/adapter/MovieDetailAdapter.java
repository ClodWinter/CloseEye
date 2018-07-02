package cn.lizhiyu.closeeye.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.model.MovieDetailItemModel;

public class MovieDetailAdapter extends RecyclerView.Adapter
{
    private ArrayList datas;

    public MovieDetailAdapter(ArrayList arrayList)
    {
        datas = arrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == 1)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mdi_director_layout,null);

            return new DirectorHolder(view);
        }
        else if(viewType == 2)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mdi_desc_layout,null);

            return new DescHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        int type = getItemViewType(position);

        MovieDetailItemModel model = (MovieDetailItemModel) datas.get(position);

        if (type == 1)
        {
            DirectorHolder holderDire = (DirectorHolder) holder;

            holderDire.textViewDate.setText(model.movieItemModel.pressDate);

            if (model.movieItemModel.writers.size()>0)
            {
                JSONObject jsonObjectName = (JSONObject) model.movieItemModel.writers.get(0);

                String name = jsonObjectName.getString("name");

                holderDire.textViewName.setText(name);
            }

            if (model.movieItemModel.actors.size()>0)
            {
                String roles = "";

                for (int i = 0; i < model.movieItemModel.actors.size() ; i++)
                {
                    JSONObject jsonObject = (JSONObject) model.movieItemModel.actors.get(i);

                    String name = jsonObject.getString("name");

                    roles = roles + name + "/";
                }

                holderDire.textViewRoles.setText(roles);
            }

            holderDire.textViewTime.setText(model.movieItemModel.durationMin + " 分钟");

        }
        else if (type == 2)
        {
            DescHolder holderDesc = (DescHolder) holder;

            holderDesc.textViewDesc.setText(model.movieItemModel.description);
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        MovieDetailItemModel model = (MovieDetailItemModel) datas.get(position);

        return model.type;
    }

    @Override
    public int getItemCount()
    {
        return datas.size();
    }

    public class DirectorHolder extends RecyclerView.ViewHolder
    {
        public TextView textViewDate;

        public TextView textViewTime;

        public TextView textViewName;

        public TextView textViewRoles;

        public DirectorHolder(View itemView)
        {
            super(itemView);

            textViewDate = itemView.findViewById(R.id.mdi_director_date);

            textViewTime = itemView.findViewById(R.id.mdi_director_time);

            textViewName = itemView.findViewById(R.id.mdi_director_name);

            textViewRoles = itemView.findViewById(R.id.mdi_director_roles);
        }
    }

    public class DescHolder extends RecyclerView.ViewHolder
    {
        public TextView textViewTitle;

        public TextView textViewDesc;

        public DescHolder(View itemView)
        {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.mdi_desc_title);

            textViewDesc = itemView.findViewById(R.id.mdi_desc_content);
        }
    }

}
