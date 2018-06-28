package cn.lizhiyu.closeeye.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;

import cn.jzvd.JZVideoPlayerStandard;
import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.model.MovieItemModel;

public class MovieDetailActivity extends AppCompatActivity {

    public MovieItemModel movieItemModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
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

        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();

        Bundle bundle = intent.getBundleExtra("intentData");

        movieItemModel = (MovieItemModel) bundle.getSerializable("model");

        createView();

    }

    public void createView()
    {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.moviedetail_toolbar);

        toolbar.setTitle(movieItemModel.title);

        toolbar.setNavigationIcon(R.mipmap.pingpp_back);

        setSupportActionBar(toolbar);

        JZVideoPlayerStandard jzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.moviedetail_player);

        String url = null;

        if (movieItemModel.videoUrls != null && movieItemModel.videoUrls.size()>0)
        {
            url = (String) movieItemModel.videoUrls.get(0);
        }

        if (url != null && url.length()>0)
        {
            jzVideoPlayerStandard.setUp(url,JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,"");

            Picasso.with(this).load(movieItemModel.getCoverUrl()).into(jzVideoPlayerStandard.thumbImageView);

            jzVideoPlayerStandard.startButton.performClick();
        }
    }
}
