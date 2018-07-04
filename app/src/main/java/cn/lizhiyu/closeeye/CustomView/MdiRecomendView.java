package cn.lizhiyu.closeeye.CustomView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import cn.lizhiyu.closeeye.CustomClass.RecyclerItemDecoration;
import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.adapter.MdiRecyclerAdapter;

public class MdiRecomendView extends RelativeLayout
{
    private ZYHFRecyclerView recyclerView;

    public MdiRecomendView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.mdi_recomend,this);

        recyclerView = findViewById(R.id.mdi_recomend_recycler);

        MdiRecyclerAdapter adapter = new MdiRecyclerAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.addItemDecoration(new RecyclerItemDecoration(26));

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(layoutManager);
    }
}
