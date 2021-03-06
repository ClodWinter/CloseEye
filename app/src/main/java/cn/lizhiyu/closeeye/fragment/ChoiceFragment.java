package cn.lizhiyu.closeeye.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.comm.constants.Constants;
import com.qq.e.comm.util.AdError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import cn.jiguang.net.HttpRequest;
import cn.lizhiyu.closeeye.Common.ACache;
import cn.lizhiyu.closeeye.Common.Define;
import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.ViewPager.AutoBannerViewPager;
import cn.lizhiyu.closeeye.activity.ChoiceDetailActivity;
import cn.lizhiyu.closeeye.activity.MainActivity;
import cn.lizhiyu.closeeye.adapter.AutoBannerPagerAdapter;
import cn.lizhiyu.closeeye.adapter.ChoiceArrayAdapter;
import cn.lizhiyu.closeeye.model.ChoiceItemModel;
import cn.lizhiyu.closeeye.model.VideoModel;
import cn.lizhiyu.closeeye.request.BaseHttpRequest;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChoiceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChoiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChoiceFragment extends Fragment implements NativeExpressAD.NativeExpressADListener{

    private View rootView;

    private View loadMoreView;

    private NestedScrollView nestedScrollView;

    private int page = 1;

    private boolean isLoadmore = false;

    private boolean hasNext;

    private ArrayList arrayChoice;

    private SwipeRefreshLayout swipeRefreshLayout;

    private ChoiceArrayAdapter choiceArrayAdapter;

    private OnFragmentInteractionListener mListener;

    private ListView listView;

    private View emptyView;

    private ArrayList arrayBanner;

    private NativeExpressAD nativeExpressAD;

    private NativeExpressADView nativeExpressADView;

    private AutoBannerPagerAdapter autoBannerPagerAdapter;

    private AutoBannerViewPager autoBannerViewPager;

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
                    choiceArrayAdapter.notifyDataSetChanged();

                    createBannerAd();

                    break;
                }

                case -1:
                {
                    Toast.makeText(getActivity(),"请求失败,请稍后重试.",Toast.LENGTH_LONG).show();

                    break;
                }

                default:

            }

            refreshBanner();

            swipeRefreshLayout.setRefreshing(false);

            if (isLoadmore)
            {
                loadComplete(false);
            }
        }
    };

    private LinearLayout indictorLayout;

    public ChoiceFragment()
    {
    }

    // TODO: Rename and change types and number of parameters
    public static ChoiceFragment newInstance(List list)
    {
        ChoiceFragment fragment = new ChoiceFragment();

        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList("ChoiceArray", (ArrayList<? extends Parcelable>) list);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
        {
            Bundle bundle = getArguments();

            arrayChoice = bundle.getParcelableArrayList("ChoiceArray");
        }
    }

    public void requestChoiceData(int page)
    {
        final BaseHttpRequest request = new BaseHttpRequest();

        final Map<String,String> param = new HashMap<>();

        param.put("apikey",Define.apiKey);

        param.put("catid","1");

        param.put("pageToken",""+page);

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    request.sendGetRequest(Define.ChoiceRequestUrl, param, getActivity(),new BaseHttpRequest.HttpRequestCallBack() {
                        @Override
                        public void onRespose(String response, int httpTag)
                        {
                            Message message = new Message();

                            if (httpTag == 200)
                            {
                                com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response);

                                com.alibaba.fastjson.JSONArray jsonArray = jsonObject.getJSONArray("data");

                                hasNext = jsonObject.getBooleanValue("hasNext");

                                if (!isLoadmore)
                                {
                                    arrayChoice.clear();

                                    arrayBanner.clear();
                                }

                                arrayChoice.addAll((ArrayList)JSON.parseArray(JSON.toJSONString(jsonArray),VideoModel.class));

                                ACache aCache = ACache.get(getActivity());

                                aCache.put(Define.ChoiceRequestUrl,arrayChoice);

                                message.what = 200;

                            }
                            else
                            {
                                message.what = -1;

                                hasNext = false;
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

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView == null)
        {
            arrayChoice = new ArrayList();

            arrayBanner = new ArrayList();

            ACache aCache = ACache.get(getActivity());

            ArrayList temp = (ArrayList) aCache.getAsObject(Define.ChoiceRequestUrl);

            if (temp!=null)
            {
//                arrayChoice.addAll(temp);
            }

            rootView = (View)inflater.inflate(R.layout.fragment_choice,container,false);

            emptyView = (View)inflater.inflate(R.layout.empty_loading_layout,null);

            nestedScrollView = (NestedScrollView)rootView.findViewById(R.id.choice_scrollview);

            nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY > oldScrollY) {
//                        Log.i("lzyssg", "Scroll DOWN");
                    }
                    if (scrollY < oldScrollY) {
//                        Log.i("lzyssg", "Scroll UP");
                    }

                    if (scrollY == 0) {
//                        Log.i("lzyssg", "TOP SCROLL");
                    }

                    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
//                        Log.i("lzyssg", "BOTTOM SCROLL");
                        //表示此时需要显示刷新视图界面进行新数据的加载(要等滑动停止)
                        if(!isLoadmore)
                        {
                            //不处于加载状态的话对其进行加载
                            isLoadmore = true;
                            //设置刷新界面可见
                            loadMoreView.setVisibility(View.VISIBLE);

                            page ++;

                            requestChoiceData(page);
                        }
                    }
                }
            });


            listView = (ListView)rootView.findViewById(R.id.choice_listView);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {

                    VideoModel model = (VideoModel) arrayChoice.get(i);

                    Intent intent = new Intent(getActivity(), ChoiceDetailActivity.class);

                    Bundle bundle = new Bundle();

                    bundle.putSerializable("model", model);

                    intent.putExtra("intentData",bundle);

                    startActivity(intent);

                    getActivity().overridePendingTransition(R.xml.choice_animation_in,R.xml.choice_animation_none);
                }
            });

            choiceArrayAdapter= new ChoiceArrayAdapter(getActivity(),R.layout.choiceitem_layout,arrayChoice);

            listView.setAdapter(choiceArrayAdapter);

            autoBannerPagerAdapter = new AutoBannerPagerAdapter(getActivity().getApplicationContext());

            autoBannerPagerAdapter.updateDatas(arrayBanner);

            autoBannerViewPager = (AutoBannerViewPager)rootView.findViewById(R.id.choice_bannerAuto);

            autoBannerViewPager.setAdapter(autoBannerPagerAdapter);

            autoBannerViewPager.setShowTime(5000);

            indictorLayout = (LinearLayout) rootView.findViewById(R.id.banner_indicator_layout);

            swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.choice_swipe);

            swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

            swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);

            swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.yello);

            swipeRefreshLayout.setProgressViewEndTarget(true,200);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh()
                {
                    isLoadmore = false;

                    page = 1;

                    requestChoiceData(page);
                }
            });

            AppBarLayout appBarLayout = (AppBarLayout)rootView.findViewById(R.id.choice_appbarlayout);

            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset)
                {
                    if (verticalOffset >= 0)
                    {
                        swipeRefreshLayout.setEnabled(true);
                    }
                    else {
                        swipeRefreshLayout.setEnabled(false);
                    }
                }
            });

            loadMoreView = (View)View.inflate(getActivity(),R.layout.choice_footerlayout,null);

            listView.addFooterView(loadMoreView);

            loadMoreView.setVisibility(View.GONE);

            this.requestChoiceData(1);

            getActivity().overridePendingTransition(R.xml.choice_animation_in,R.xml.choice_animation_none);

            autoBannerViewPager.setOnTouchListener(new View.OnTouchListener()
            {
                int flage = 0;

                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    VideoModel model = new VideoModel();

                    Intent intent = new Intent(getActivity(), ChoiceDetailActivity.class);

                    Bundle bundle = new Bundle();

                    bundle.putSerializable("model", model);

                    intent.putExtra("intentData",bundle);

                    startActivity(intent);

                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            flage = 0 ;
                            break ;
                        case MotionEvent.ACTION_MOVE:
                            flage = 1 ;
                            break ;
                        case  MotionEvent.ACTION_UP :
                            if (flage == 0) {
//                                int item = autoBannerViewPager.getCurrentItem();
//                                if (item == 0) {
//                                    Intent intent = new Intent(sa, NoNetWork.class);
//                                    sa.startActivity(intent);
//                                } else if (item == 1) {
//                                    Intent intent = new Intent(sa, NoNetWork.class);
//                                    sa.startActivity(intent);
//                                } else if (item == 2) {
//                                    Intent intent = new Intent(sa, NoNetWork.class);
//                                    sa.startActivity(intent);
//                                }else if (item == 3) {
//                                    Intent intent = new Intent(sa, NoNetWork.class);
//                                    sa.startActivity(intent);
//                                }
                            }
                            break ;


                    }

                    return false;
                }
            });

        }else
        {
            ViewGroup parent = (ViewGroup) rootView.getParent();

            if (parent != null)
            {
                parent.removeView(rootView);
            }
        }

        return rootView;
    }

    public void createBannerAd()
    {
        loadNativeAd();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void loadComplete(boolean isRemove)
    {
        loadMoreView.setVisibility(View.VISIBLE);//设置刷新界面不可见

        isLoadmore = false;//设置正在刷新标志位false

        if (!hasNext)
        {
            listView.removeFooterView(loadMoreView);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void loadNativeAd()
    {
        try {

            /**
             *  如果选择支持视频的模版样式，请使用{@link Constants#NativeExpressSupportVideoPosID}
             */
            nativeExpressAD = new NativeExpressAD(getActivity(), getMyADSize(), Define.APPID, Define.NativeExpressPosID, this); // 这里的Context必须为Activity
            nativeExpressAD.setVideoOption(new VideoOption.Builder()
                    .setAutoPlayPolicy(VideoOption.AutoPlayPolicy.WIFI) // 设置什么网络环境下可以自动播放视频
                    .setAutoPlayMuted(true) // 设置自动播放视频时，是否静音
                    .build()); // setVideoOption是可选的，开发者可根据需要选择是否配置
            nativeExpressAD.loadAD(5);
        } catch (NumberFormatException e) {
        }
    }

    private com.qq.e.ads.nativ.ADSize getMyADSize() {
        int w =  com.qq.e.ads.nativ.ADSize.FULL_WIDTH;
        int h =  com.qq.e.ads.nativ.ADSize.AUTO_HEIGHT;
        return new com.qq.e.ads.nativ.ADSize(w, h);
    }

    private void refreshBanner()
    {
        arrayBanner.add(R.mipmap.choice_banner_0);

        arrayBanner.add(R.mipmap.choice_banner_1);

        arrayBanner.add(R.mipmap.choice_banner_2);

        arrayBanner.add(R.mipmap.choice_banner_3);

        arrayBanner.add(R.mipmap.choice_banner_4);

        autoBannerPagerAdapter.notifyDataSetChanged();

        autoBannerViewPager.start();

        indictorLayout.removeAllViews();

        for (int i = 0; i < arrayBanner.size(); i++)
        {
            ImageView imageView = new ImageView(getActivity());

            imageView.setImageResource(i==0?R.drawable.banner_shape_select:R.drawable.banner_shape_normal);

            indictorLayout.addView(imageView);

            LinearLayout.LayoutParams layoutParams=
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);

            layoutParams.leftMargin = 10;

            imageView.setLayoutParams(layoutParams);

        }

        autoBannerViewPager.callBack = new AutoBannerViewPager.scrollCallBack() {
            @Override
            public void scroll(int page)
            {
                Log.d("tttttttt", "scroll: "+page);

                for (int i = 0; i < indictorLayout.getChildCount(); i++)
                {
                    ImageView imageView = (ImageView) indictorLayout.getChildAt(i);

                    imageView.setImageDrawable(null);

                    if (i == page)
                    {
                        imageView.setBackgroundResource(R.drawable.banner_shape_select);
                    }
                    else {
                        imageView.setBackgroundResource(R.drawable.banner_shape_normal);
                    }
                }
            }
        };
    }

    @Override
    public void onNoAD(AdError adError) {

    }

    @Override
    public void onADLoaded(List<NativeExpressADView> list)
    {
        arrayBanner.clear();

//        arrayBanner.addAll(list);
    }

    @Override
    public void onRenderFail(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onRenderSuccess(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADExposure(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADClicked(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADClosed(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADLeftApplication(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {

    }
}
