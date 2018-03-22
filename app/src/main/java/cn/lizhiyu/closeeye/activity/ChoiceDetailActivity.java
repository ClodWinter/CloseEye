package cn.lizhiyu.closeeye.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import cn.lizhiyu.closeeye.CustomClass.CommonTool;
import cn.lizhiyu.closeeye.CustomView.ZYHFRecyclerView;
import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.adapter.ChoiceDetailRecyclerAdapter;
import cn.lizhiyu.closeeye.model.ChoiceItemModel;
import cn.lizhiyu.closeeye.model.VideoModel;

public class ChoiceDetailActivity extends AppCompatActivity
{
    private ZYHFRecyclerView recyclerView;

    private ChoiceDetailRecyclerAdapter detailRecyclerAdapter;

    private ArrayList arrayListRecomend = new ArrayList();

    private VideoModel model;

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

        Intent intent = getIntent();

        Bundle bundle = intent.getBundleExtra("intentData");

        model = (VideoModel) bundle.getSerializable("model");

        createView();
    }

    public void createView()
    {
        ImageSwitcher imageView = (ImageSwitcher)findViewById(R.id.choice_detail_bg);

        CommonTool.setViewBlur(this,model.getCoverUrl(),R.mipmap.ic_launcher, imageView);

        recyclerView = (ZYHFRecyclerView) findViewById(R.id.choice_detail_recycler);

        for (int i = 0; i < 10; i++)
        {
            ChoiceItemModel model = new ChoiceItemModel();

            arrayListRecomend.add(model);
        }

        detailRecyclerAdapter = new ChoiceDetailRecyclerAdapter(arrayListRecomend);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        View headView = LayoutInflater.from(this).inflate(R.layout.choice_recyclerhead_layout,null);

        headView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        recyclerView.addHeaderView(headView);

        recyclerView.setAdapter(detailRecyclerAdapter);

        recyclerView.setLayoutManager(layoutManager);
    }
}
