package cn.lizhiyu.closeeye.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import cn.lizhiyu.closeeye.CustomClass.ZYRecyclerItemClickListener;
import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.adapter.SettingRecyclerAdapter;
import cn.lizhiyu.closeeye.model.SettingModel;

public class SettingActivity extends AppCompatActivity {

    private ArrayList arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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

        setContentView(R.layout.activity_setting);

        createView();
    }

    private void createData()
    {
        arrayList = new ArrayList();

        createSettingModel("推送设置","关注推送、每日精选推送、签到提醒等","",1);

        createSettingModel("在2G/3G/4G流量下播放视频","为节省流量费用，建议关闭","",2);

        createSettingModel("绑定手机","","未绑定",3);

        createSettingModel("是否自动播放视频","","",4);

        createSettingModel("给予好评","","",5);

        createSettingModel("检查更新","","",6);

        createSettingModel("清除缓存","","",7);

        createSettingModel("字体大小","","",8);
    }

    private SettingModel createSettingModel(String title, String content, String desc, int tag)
    {
        SettingModel model = new SettingModel();

        model.setTitle(title);

        model.setContent(content);

        model.setDesc(desc);

        model.setTag(tag);

        return model;
    }

    private void createView()
    {
        LinearLayoutManager rLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.setting_recycler);

        SettingRecyclerAdapter settingRecyclerAdapter = new SettingRecyclerAdapter();

        recyclerView.setAdapter(settingRecyclerAdapter);

        recyclerView.setLayoutManager(rLayoutManager);

        recyclerView.addOnItemTouchListener(new ZYRecyclerItemClickListener(this, new ZYRecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position)
            {
                Logger.d("====="+position);
            }

            @Override
            public void onLongClick(View view, int posotion)
            {
                Logger.d("====="+posotion);
            }
        }));
    }
}
