package cn.lizhiyu.closeeye.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import cn.jpush.android.api.JPushInterface;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.mob.MobSDK;

import java.util.ArrayList;
import java.util.List;

import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.fragment.ChoiceFragment;
import cn.lizhiyu.closeeye.fragment.DiscoveryFragment;
import cn.lizhiyu.closeeye.fragment.FollowPairingFragment;
import cn.lizhiyu.closeeye.fragment.FollowFragment;
import cn.lizhiyu.closeeye.fragment.FollowNearFragment;
import cn.lizhiyu.closeeye.fragment.MineFragment;
import cn.lizhiyu.closeeye.fragment.MovieFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener,ChoiceFragment.OnFragmentInteractionListener
        ,DiscoveryFragment.OnFragmentInteractionListener
        ,FollowFragment.OnFragmentInteractionListener
        ,MineFragment.OnFragmentInteractionListener
        ,FollowNearFragment.OnFragmentInteractionListener
        ,FollowPairingFragment.OnFragmentInteractionListener
        ,MovieFragment.OnFragmentInteractionListener{

    private BottomNavigationBar bottomNav;

    private List listFragments;

    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        MobSDK.init(this);

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


        setContentView(R.layout.activity_main);

        frameLayout = (FrameLayout)findViewById(R.id.home_frameLayout);

        this.createBottomNav();

        this.createFragments();

        this.setDefaultFragment();

        JPushInterface.setDebugMode(true);

        JPushInterface.init(this);
    }


    private void createBottomNav()
    {
        bottomNav = (BottomNavigationBar)findViewById(R.id.home_bottonNav);

        bottomNav.setMode(BottomNavigationBar.MODE_FIXED);

        bottomNav.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        bottomNav.addItem(new BottomNavigationItem(R.mipmap.home,"首页").setActiveColorResource(R.color.f6683d))
                .addItem(new BottomNavigationItem(R.mipmap.place,"地点").setActiveColorResource(R.color.f6683d))
                .addItem(new BottomNavigationItem(R.mipmap.story,"故事").setActiveColorResource(R.color.f6683d))
                .addItem(new BottomNavigationItem(R.mipmap.setting,"个人").setActiveColorResource(R.color.f6683d))
                .setFirstSelectedPosition(0).initialise();

        bottomNav.setTabSelectedListener(this);

    }

    private void createFragments()
    {
        listFragments = new ArrayList<Fragment>();

        ChoiceFragment fragmentChoice = new ChoiceFragment();

        MovieFragment fragmentMovie = new MovieFragment();

        FollowFragment fragmentFollow = new FollowFragment();

        MineFragment fragmentMine = new MineFragment();

        listFragments.add(fragmentChoice);

        listFragments.add(fragmentMovie);

        listFragments.add(fragmentFollow);

        listFragments.add(fragmentMine);
    }



    private void setDefaultFragment()
    {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = (Fragment) listFragments.get(0);

        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.home_frameLayout,fragment);

        transaction.commit();
    }

    @Override
    public void onTabSelected(int position)
    {
        if (position<listFragments.size())
        {
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

            android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();

            Fragment fragment = (Fragment)listFragments.get(position);

            if (fragment.isAdded())
            {
                transaction.replace(R.id.home_frameLayout,fragment);
            }else
            {
                transaction.add(R.id.home_frameLayout,fragment);
            }

            transaction.commitAllowingStateLoss();
        }
    }

    @Override
    public void onTabUnselected(int position)
    {
        if (position<listFragments.size())
        {
            Fragment fragment = (Fragment)listFragments.get(position);

            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

            android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();

            transaction.remove(fragment);

            transaction.commitAllowingStateLoss();
        }
    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
