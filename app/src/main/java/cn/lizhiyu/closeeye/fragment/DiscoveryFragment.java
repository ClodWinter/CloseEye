package cn.lizhiyu.closeeye.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.lizhiyu.closeeye.CustomView.ZYImageView;
import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.adapter.DiscArrayAdapter;
import cn.lizhiyu.closeeye.model.DiscItemModel;
import cn.lizhiyu.closeeye.request.BaseHttpRequest;

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

    private boolean hasNext;

    private boolean isLoadMore = false;

    private DiscArrayAdapter discArrayAdapter;

    private ArrayList arrayDisc = new ArrayList();

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = (View)inflater.inflate(R.layout.fragment_discovery,container,false);

        ListView listView = (ListView)rootView.findViewById(R.id.disc_listView);

        discArrayAdapter = new DiscArrayAdapter(getActivity(),R.layout.disc_list_item,arrayDisc);

        listView.setAdapter(discArrayAdapter);

        requestData(1);

        return rootView;
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
                    request.sendGetRequest("http://120.76.205.241:8000/post/duowan?", param, new BaseHttpRequest.HttpRequestCallBack() {
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
                                    arrayDisc.clear();
                                }

                                arrayDisc.addAll(JSON.parseArray(jsonArray.toJSONString(), DiscItemModel.class));

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
