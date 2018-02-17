package cn.lizhiyu.closeeye.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import cn.jiguang.net.HttpRequest;
import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.ViewPager.AutoBannerViewPager;
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
public class ChoiceFragment extends Fragment {

    private  View rootView;

    private ArrayList arrayChoice;

    private ArrayList arrayBanner;

    private SwipeRefreshLayout swipeRefreshLayout;

    private AutoBannerViewPager autoBannerViewPager;

    private ChoiceArrayAdapter choiceArrayAdapter;

    private AutoBannerPagerAdapter autoBannerPagerAdapter;

    private OnFragmentInteractionListener mListener;

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
                    autoBannerPagerAdapter.notifyDataSetChanged();

                    choiceArrayAdapter.notifyDataSetChanged();

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

                    break;
                }

                case -1:
                {
                    Toast.makeText(getActivity(),"请求失败,请稍后重试.",Toast.LENGTH_LONG).show();

                    break;
                }

                default:

            }

            swipeRefreshLayout.setRefreshing(false);
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

        param.put("apikey","IdFJsLGzIdTvxCAs362MupngfDRXQACCk71LxXBOSDHbzG9Wv5AHN0qEYhWQh9HV");

        param.put("kw","搞笑");

        param.put("pageToken","1");

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    request.sendGetRequest("http://120.76.205.241:8000/video/duowan?", param, new BaseHttpRequest.HttpRequestCallBack() {
                        @Override
                        public void onRespose(String response, int httpTag)
                        {
                            Message message = new Message();

                            if (httpTag == 200)
                            {
                                com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response);

                                com.alibaba.fastjson.JSONArray jsonArray = jsonObject.getJSONArray("data");

                                arrayChoice.clear();

                                arrayChoice.addAll((ArrayList)JSON.parseArray(JSON.toJSONString(jsonArray),VideoModel.class));

                                Log.d("lzyssg", "onRespose: ");

                                arrayBanner.clear();

                                arrayBanner.add(R.mipmap.choice_topview_bg);

                                arrayBanner.add(R.mipmap.choiceitem_cover);

                                arrayBanner.add(R.mipmap.launch);

                                arrayBanner.add(R.mipmap.mine_headbg);

                                message.what = 200;

                            }
                            else
                            {
                                message.what = -1;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView == null)
        {
            arrayBanner = new ArrayList();

            arrayChoice = new ArrayList();

            rootView = (View)inflater.inflate(R.layout.fragment_choice,container,false);

            ListView listView = (ListView)rootView.findViewById(R.id.choice_listView);

            choiceArrayAdapter= new ChoiceArrayAdapter(getActivity(),R.layout.choiceitem_layout,arrayChoice);

            listView.setAdapter(choiceArrayAdapter);

            autoBannerPagerAdapter = new AutoBannerPagerAdapter(getActivity().getApplicationContext());

            autoBannerPagerAdapter.updateDatas(arrayBanner);

            autoBannerViewPager = (AutoBannerViewPager) rootView.findViewById(R.id.choice_autonBanner);

            autoBannerViewPager.setAdapter(autoBannerPagerAdapter);

            autoBannerViewPager.setShowTime(5000);

            autoBannerViewPager.setDirection(AutoBannerViewPager.Direction.Left);

            autoBannerViewPager.start();

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
                    requestChoiceData(0);
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

            this.requestChoiceData(0);

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
}
