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
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.mob.MobSDK;
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
import cn.sharesdk.onekeyshare.OnekeyShare;
import jp.wasabeef.glide.transformations.BlurTransformation;
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

        ImageView imageView = (ImageView)findViewById(R.id.choice_detail_bg);

        Glide.with(this).load(R.mipmap.choicedetail_bg).bitmapTransform(new BlurTransformation(this, 24)).into(imageView);

        recyclerView = (ZYHFRecyclerView) findViewById(R.id.choice_detail_recycler);

        detailRecyclerAdapter = new ChoiceDetailRecyclerAdapter(arrayListRecomend,this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(detailRecyclerAdapter);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

        jzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.choice_detail_videolayer);

        if (model.videoUrls!=null&&model.videoUrls.size()>0)
        {
            String url = (String) model.videoUrls.get(0);

            jzVideoPlayerStandard.setUp("",JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,"");

            Picasso.with(this).load(model.getCoverUrl()).into(jzVideoPlayerStandard.thumbImageView);

            jzVideoPlayerStandard.startButton.performClick();
        }

        jzVideoPlayerStandard.setUp("http://upos-hz-mirrorcos.acgvideo.com/upgcxcode/86/16/38511686/38511686-1-32.flv?um_deadline=1525750162&platform=pc&rate=302600&oi=2018260062&um_sign=75e1ddcac04dabd8d6b25982c2d2cd0a&gen=playurl&os=cos&trid=1d878ee89ab54f1cb5822e0b8d04a9d5",JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,"");

        Picasso.with(this).load(model.getCoverUrl()).into(jzVideoPlayerStandard.thumbImageView);

        jzVideoPlayerStandard.startButton.performClick();

        createHeadView();
    }

    public void createHeadView()
    {
        View headView = LayoutInflater.from(this).inflate(R.layout.choice_recyclerhead_layout,null);

        headView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        ImageView imageViewCollectIcon = headView.findViewById(R.id.detail_headitem_icon);

        Glide.with(this).load(R.mipmap.beauty_0).bitmapTransform(new CropCircleTransformation(this)).crossFade(1000).into(imageViewCollectIcon);

        relativeLayoutTags = headView.findViewById(R.id.choicedetail_head_tagslayout);

        recyclerView.addHeaderView(headView);

        final Button buttonLike = headView.findViewById(R.id.choice_detail_recycler_btnLike);

        buttonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(ChoiceDetailActivity.this,"收藏成功",Toast.LENGTH_LONG).show();
                buttonLike.setBackgroundResource(R.drawable.liked);
            }
        });

        final Button buttonShare = headView.findViewById(R.id.choice_detail_recycler_btnShare);

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OnekeyShare oks = new OnekeyShare();
                //关闭sso授权
                oks.disableSSOWhenAuthorize();

                // title标题，微信、QQ和QQ空间等平台使用
                oks.setTitle("这个新闻真好");
                // titleUrl QQ和QQ空间跳转链接
                oks.setTitleUrl("http://sharesdk.cn");
                // text是分享文本，所有平台都需要这个字段//
                oks.setText("我是分享文本");
                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                // url在微信、微博，Facebook等平台中使用
                oks.setUrl("http://sharesdk.cn");
                // comment是我对这条分享的评论，仅在人人网使用
                oks.setComment("我是测试评论文本");
                // 启动分享GUI
                oks.show(ChoiceDetailActivity.this);
            }
        });
    }

    public void createTags()
    {
        if (arrayListRecomend.size()>=10)
        {
            WindowManager manager = this.getWindowManager();
            DisplayMetrics outMetrics = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(outMetrics);
            int width = outMetrics.widthPixels;
            int height = outMetrics.heightPixels;

            int tagWidth = (width - 60)/3;

            for (int i = 7; i < 10; i++)
            {
                VideoModel model = (VideoModel) arrayListRecomend.get(i);

                ChoiceDetailHeadTagView tagView = new ChoiceDetailHeadTagView(this,null);

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(tagWidth,dpTodx(50));

                tagView.setId(300+i);

                layoutParams.leftMargin = 10;

                if (relativeLayoutTags.getChildCount()>0)
                {
                    ChoiceDetailHeadTagView temp = (ChoiceDetailHeadTagView) relativeLayoutTags.getChildAt(relativeLayoutTags.getChildCount()-1);

                    layoutParams.addRule(RelativeLayout.RIGHT_OF,temp.getId());

                    layoutParams.leftMargin = 20;

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

        params.put("catid","1");

        params.put("pageToken","0");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    baseHttpRequest.sendGetRequest("http://120.76.205.241:8000/video/duowan?", params, ChoiceDetailActivity.this, new BaseHttpRequest.HttpRequestCallBack() {
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
