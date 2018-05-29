package cn.lizhiyu.closeeye.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.lizhiyu.closeeye.Common.ACache;
import cn.lizhiyu.closeeye.Common.Define;
import cn.lizhiyu.closeeye.CustomView.ZYHFRecyclerView;
import cn.lizhiyu.closeeye.CustomView.ZYTabsView;
import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.adapter.MovieAdapter;
import cn.lizhiyu.closeeye.model.MovieItemModel;
import cn.lizhiyu.closeeye.model.VideoModel;
import cn.lizhiyu.closeeye.request.BaseHttpRequest;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View rootView;

    private MovieAdapter movieAdapter;

    Boolean hasNext;

    boolean isLoadmore;

    ZYHFRecyclerView zyhfRecyclerView;

    ArrayList arrayListMovie;

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
                    movieAdapter.notifyDataSetChanged();

                    break;
                }

                case -1:
                {
                    Toast.makeText(getActivity(),"请求失败,请稍后重试.",Toast.LENGTH_LONG).show();

                    break;
                }

                default:

            }

            if (isLoadmore)
            {

            }
        }
    };

    public MovieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieFragment newInstance(String param1, String param2) {
        MovieFragment fragment = new MovieFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_movie,null);

            arrayListMovie = new ArrayList();

            for (int i = 0; i < 20; i++)
            {
                arrayListMovie.add("1");
            }

            zyhfRecyclerView = rootView.findViewById(R.id.movie_recycler);

            movieAdapter = new MovieAdapter(arrayListMovie,getActivity());

            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

            zyhfRecyclerView.setAdapter(movieAdapter);

            zyhfRecyclerView.setLayoutManager(layoutManager);
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

    public  void requestData(int page)
    {
        final BaseHttpRequest request = new BaseHttpRequest();

        final Map<String,String> param = new HashMap<>();

        param.put("apikey", Define.apiKey);

        param.put("catid","4");

        param.put("pageToken",""+page);

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    request.sendGetRequest(Define.MovieRequestUrl, param, getActivity(),new BaseHttpRequest.HttpRequestCallBack() {
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
                                    arrayListMovie.clear();
                                }

                                arrayListMovie.addAll((ArrayList)JSON.parseArray(JSON.toJSONString(jsonArray),MovieItemModel.class));

                                ACache aCache = ACache.get(getActivity());

                                aCache.put(Define.MovieRequestUrl,arrayListMovie);

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
