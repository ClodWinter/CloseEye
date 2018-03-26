package cn.lizhiyu.closeeye.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.jzvd.JZVideoPlayerStandard;
import cn.lizhiyu.closeeye.Common.Define;
import cn.lizhiyu.closeeye.CustomClass.CommonTool;
import cn.lizhiyu.closeeye.CustomView.ChoiceDetailHeadTagView;
import cn.lizhiyu.closeeye.CustomView.ZYHFRecyclerView;
import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.adapter.ChoiceDetailRecyclerAdapter;
import cn.lizhiyu.closeeye.model.ChoiceItemModel;
import cn.lizhiyu.closeeye.model.VideoModel;
import cn.lizhiyu.closeeye.request.BaseHttpRequest;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ChoiceDetailActivity extends AppCompatActivity
{
    private ZYHFRecyclerView recyclerView;

    private ChoiceDetailRecyclerAdapter detailRecyclerAdapter;

    private ArrayList arrayListRecomend;

    private VideoModel model;

    private RelativeLayout relativeLayoutTags;

    private JZVideoPlayerStandard jzVideoPlayerStandard;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 200:
                {
                    Log.d("lzyssg", "handleMessage: " +arrayListRecomend);

                    detailRecyclerAdapter.notifyDataSetChanged();

                    createTags();

                    break;
                }

                case -1:
                {

                    break;
                }

                default:
            }
        }


    };

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

        requestData();
    }

    public void createView()
    {
        arrayListRecomend = new ArrayList();

        ImageSwitcher imageView = (ImageSwitcher)findViewById(R.id.choice_detail_bg);

        CommonTool.setViewBlur(this,model.getCoverUrl(),R.mipmap.ic_launcher, imageView);

        recyclerView = (ZYHFRecyclerView) findViewById(R.id.choice_detail_recycler);

        detailRecyclerAdapter = new ChoiceDetailRecyclerAdapter(arrayListRecomend,this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        View headView = LayoutInflater.from(this).inflate(R.layout.choice_recyclerhead_layout,null);

        headView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        ImageView imageViewCollectIcon = headView.findViewById(R.id.detail_headitem_icon);

        relativeLayoutTags = headView.findViewById(R.id.choicedetail_head_tagslayout);

        Glide.with(this).load(R.mipmap.beauty_0).bitmapTransform(new CropCircleTransformation(this)).into(imageViewCollectIcon);

        recyclerView.addHeaderView(headView);

        recyclerView.setAdapter(detailRecyclerAdapter);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

        jzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.choice_detail_videolayer);

        if (model.videoUrls!=null&&model.videoUrls.size()>0)
        {
            String url = (String) model.videoUrls.get(0);

            jzVideoPlayerStandard.setUp(url,JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,"");

            Picasso.with(this).load(model.getCoverUrl()).into(jzVideoPlayerStandard.thumbImageView);

            jzVideoPlayerStandard.startButton.performClick();
        }
    }

    public void createTags()
    {
        if (arrayListRecomend.size()>=10)
        {
            for (int i = 7; i < 10; i++)
            {
                VideoModel model = (VideoModel) arrayListRecomend.get(i);

                ChoiceDetailHeadTagView tagView = new ChoiceDetailHeadTagView(this,null);

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(dpTodx(120),dpTodx(50));

                tagView.setId(300+i);

                layoutParams.leftMargin = 10;

                if (relativeLayoutTags.getChildCount()>0)
                {
                    ChoiceDetailHeadTagView temp = (ChoiceDetailHeadTagView) relativeLayoutTags.getChildAt(relativeLayoutTags.getChildCount()-1);

                    layoutParams.addRule(RelativeLayout.RIGHT_OF,temp.getId());

                }

                layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);

                tagView.setLayoutParams(layoutParams);

                tagView.setData(model);

                relativeLayoutTags.addView(tagView);
            }
        }
    }

    public void requestData()
    {
        final BaseHttpRequest baseHttpRequest = new BaseHttpRequest();

        final Map<String,String> params = new HashMap<>();

        params.put("apikey","IdFJsLGzIdTvxCAs362MupngfDRXQACCk71LxXBOSDHbzG9Wv5AHN0qEYhWQh9HV");

        params.put("kw","推荐");

        params.put("pageToken","0");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    baseHttpRequest.sendGetRequest("http://120.76.205.241:8000/video/duowan?", params, new BaseHttpRequest.HttpRequestCallBack() {
                        @Override
                        public void onRespose(String response, int httpTag)
                        {
                            Message message = new Message();

                            message.what = httpTag;

                            arrayListRecomend.clear();

                            if (httpTag == 200)
                            {
                                com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response);

                                com.alibaba.fastjson.JSONArray jsonArray = jsonObject.getJSONArray("data");

                                arrayListRecomend.addAll((ArrayList)JSON.parseArray(JSON.toJSONString(jsonArray),VideoModel.class));
                            }

                            handler.sendMessage(message);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }

    /**
     * 单位dp转单位px
     */
    public int dpTodx(int dp){

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp,getResources().getDisplayMetrics());
    }

    @Override
    public void onBackPressed()
    {
        if (jzVideoPlayerStandard.backPress()) {
            return;
        }

        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

        jzVideoPlayerStandard.releaseAllVideos();
    }
}
