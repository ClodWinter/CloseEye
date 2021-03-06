package cn.lizhiyu.closeeye.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.lizhiyu.closeeye.Animations.DiscListHeadTrans;
import cn.lizhiyu.closeeye.Common.ACache;
import cn.lizhiyu.closeeye.Common.Define;
import cn.lizhiyu.closeeye.CustomView.DiscHeadItemView;
import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.ViewPager.AutoBannerViewPager;
import cn.lizhiyu.closeeye.activity.DiscDetailActivity;
import cn.lizhiyu.closeeye.adapter.AutoBannerPagerAdapter;
import cn.lizhiyu.closeeye.adapter.DiscArrayAdapter;
import cn.lizhiyu.closeeye.model.DiscItemModel;
import cn.lizhiyu.closeeye.request.BaseHttpRequest;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DiscoveryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DiscoveryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscoveryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View rootView;

    private int last_index;

    private int total_index;

    private boolean hasNext;

    private boolean isLoadMore = false;

    private DiscArrayAdapter discArrayAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    private View loadMoreView;

    private ListView listView;

    private int page = 1;

    private ArrayList arrayDisc = new ArrayList();

    private View headView;

    private AutoBannerPagerAdapter autoBannerPagerAdapter;

    private AutoBannerViewPager autoBannerViewPager;

    private ArrayList arrayListBanner = new ArrayList();

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
                    discArrayAdapter.notifyDataSetChanged();

                    autoBannerPagerAdapter.notifyDataSetChanged();

                    autoBannerViewPager.start();

                    break;
                }

                case -1:
                {
                    Toast.makeText(getActivity(),"请求失败,请稍后重试.",Toast.LENGTH_LONG).show();

                    break;
                }

                default:
                    break;
            }

            swipeRefreshLayout.setRefreshing(false);

            loadComplete(hasNext);
        }
    };

    private OnFragmentInteractionListener mListener;

    public DiscoveryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiscoveryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiscoveryFragment newInstance(String param1, String param2) {
        DiscoveryFragment fragment = new DiscoveryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (rootView == null)
        {
            try{
                Object win = getActivity().getWindow();
                Class<?> cls = win.getClass();
                Method method = cls.getDeclaredMethod("setStatusBarIconDark",boolean.class);
                method.invoke(win,R.color.darkgray);
            } catch(Exception e) {
                Log.v("ff", "statusBarIconDark,e=" + e);
            }

            ACache aCache = ACache.get(getActivity());

            ArrayList temp = (ArrayList) aCache.getAsObject(Define.DiscRequestUrl);

            if (temp != null)
            {
                arrayDisc.addAll(temp);
            }

            rootView = (View)inflater.inflate(R.layout.fragment_discovery,container,false);

            listView = (ListView)rootView.findViewById(R.id.disc_listView);

            discArrayAdapter = new DiscArrayAdapter(getActivity(),R.layout.disc_list_item,arrayDisc);

            listView.setAdapter(discArrayAdapter);

            requestData(page);

            swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.disc_swiperefresh);

            swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

            swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);

            swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.yello);

            swipeRefreshLayout.setProgressViewEndTarget(true,200);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh()
                {
                    isLoadMore = false;

                    page = 1;

                    requestData(page);
                }
            });

            loadMoreView = (View)View.inflate(getActivity(),R.layout.choice_footerlayout,null);

            listView.addFooterView(loadMoreView);

            loadMoreView.setVisibility(View.GONE);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    DiscItemModel model = (DiscItemModel)arrayDisc.get(i);

                    DiscDetailActivity activity = new DiscDetailActivity();

                    Bundle bundle = new Bundle();

                    bundle.putSerializable("Obj", (Serializable)model);

                    Intent intent = new Intent(getActivity(),DiscDetailActivity.class);

                    intent.putExtra("model",bundle);

                    startActivity(intent);
                }
            });

            headView = inflater.inflate(R.layout.disc_list_headview,null);

            createHeadItem();

            autoBannerViewPager = headView.findViewById(R.id.disc_listHead);

            autoBannerPagerAdapter = new AutoBannerPagerAdapter(getContext());

            autoBannerPagerAdapter.updateDatas(arrayListBanner);

            autoBannerViewPager.setAdapter(autoBannerPagerAdapter);

            listView.addHeaderView(headView);

            autoBannerViewPager.setPageTransformer(false,new DiscListHeadTrans(autoBannerViewPager));

            autoBannerViewPager.callBack = new AutoBannerViewPager.scrollCallBack() {
                @Override
                public void scroll(int page) {

                }
            };

            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView absListView, int i) {
                    if(last_index == total_index && (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE))
                    {
                        //表示此时需要显示刷新视图界面进行新数据的加载(要等滑动停止)
                        if(!isLoadMore)
                        {
                            //不处于加载状态的话对其进行加载
                            isLoadMore = true;
                            //设置刷新界面可见
                            loadMoreView.setVisibility(View.VISIBLE);

                            page++;

                            requestData(page);
                        }
                    }
                }

                @Override
                public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                    last_index = i+i1;
                    total_index = i2;
                    System.out.println("last:  "+last_index);
                    System.out.println("total:  "+total_index);
                }
            });
        }
        else
        {
            ViewGroup parent = (ViewGroup) rootView.getParent();

            if (parent != null)
            {
                parent.removeView(rootView);
            }
        }

        return rootView;
    }

    public void createHeadItem()
    {
        DiscHeadItemView discHeadItemViewRank = headView.findViewById(R.id.disc_head_rank);

        DiscHeadItemView discHeadItemViewHot = headView.findViewById(R.id.disc_head_hot);

        DiscHeadItemView discHeadItemViewKind = headView.findViewById(R.id.disc_head_kinds);

        DiscHeadItemView discHeadItemViewLike = headView.findViewById(R.id.disc_head_like);

        discHeadItemViewRank.imageView.setBackgroundResource(R.drawable.rank);

        discHeadItemViewHot.imageView.setBackgroundResource(R.drawable.hot);

        discHeadItemViewKind.imageView.setBackgroundResource(R.drawable.kinds);

        discHeadItemViewLike.imageView.setBackgroundResource(R.drawable.like);
    }

    public void requestData(int page)
    {
        final BaseHttpRequest request = new BaseHttpRequest();

        final Map<String,String> param = new HashMap<>();

        param.put("apikey","IdFJsLGzIdTvxCAs362MupngfDRXQACCk71LxXBOSDHbzG9Wv5AHN0qEYhWQh9HV");

        param.put("catid","2509");

        param.put("pageToken",""+page);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    request.sendGetRequest(Define.DiscRequestUrl, param, getActivity(), new BaseHttpRequest.HttpRequestCallBack() {
                        @Override
                        public void onRespose(String response, int httpTag)
                        {

                            Message message = new Message();

                            if (httpTag == 200)
                            {
                                JSONObject jsonObject = JSONObject.parseObject(response);

                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                hasNext = jsonObject.getBoolean("hasNext");

                                if (!isLoadMore)
                                {
                                    arrayListBanner.clear();

                                    arrayDisc.clear();
                                }

                                arrayListBanner.add(R.mipmap.choice_topview_bg);

                                arrayListBanner.add(R.mipmap.choiceitem_cover);

                                arrayListBanner.add(R.mipmap.launch);

                                arrayListBanner.add(R.mipmap.mine_headbg);

                                arrayDisc.addAll(JSON.parseArray(jsonArray.toJSONString(), DiscItemModel.class));

                                ACache aCache = ACache.get(getActivity());

                                aCache.put(Define.DiscRequestUrl,arrayDisc);

                                message.what = 200;
                            }else
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

    public void loadComplete(boolean isRemove)
    {
        loadMoreView.setVisibility(View.VISIBLE);//设置刷新界面不可见

        isLoadMore = false;//设置正在刷新标志位false

        if (!hasNext)
        {
            listView.removeFooterView(loadMoreView);
        }
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
