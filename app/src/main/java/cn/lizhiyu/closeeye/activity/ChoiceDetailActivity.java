package cn.lizhiyu.closeeye.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

import cn.lizhiyu.closeeye.CustomView.ZYHFRecyclerView;
import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.adapter.ChoiceDetailRecyclerAdapter;
import cn.lizhiyu.closeeye.model.ChoiceItemModel;

public class ChoiceDetailActivity extends AppCompatActivity
{
    private ZYHFRecyclerView recyclerView;

    private ChoiceDetailRecyclerAdapter detailRecyclerAdapter;

    private ArrayList arrayListRecomend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //设置状态栏为透明
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_choice_detail);

        createView();
    }

    public void createView()
    {
        recyclerView = (ZYHFRecyclerView) findViewById(R.id.choice_detail_recycler);

        for (int i = 0; i < 10; i++)
        {
            ChoiceItemModel model = new ChoiceItemModel();

            arrayListRecomend.add(model);
        }

        detailRecyclerAdapter = new ChoiceDetailRecyclerAdapter(arrayListRecomend);

        RecyclerView.LayoutManager layoutManager = new RecyclerView.LayoutManager() {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return null;
            }
        };

        recyclerView.setAdapter(detailRecyclerAdapter);

        recyclerView.setLayoutManager(layoutManager);
    }
}
