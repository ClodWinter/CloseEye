package cn.lizhiyu.closeeye.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;

import cn.lizhiyu.closeeye.CustomClass.FollowCardCallback;
import cn.lizhiyu.closeeye.CustomClass.OverLayCardLayoutManager;
import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.adapter.FollowCardAdapter;
import cn.lizhiyu.closeeye.model.CardConfig;
import cn.lizhiyu.closeeye.model.FollowCardItemModel;
import cn.lizhiyu.closeeye.model.PairingItemModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FollowNearFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FollowNearFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FollowNearFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;

    private ArrayList arrayListCards = new ArrayList();

    private View rootView;

    private OnFragmentInteractionListener mListener;

    public FollowNearFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FollowNearFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FollowNearFragment newInstance(String param1, String param2) {
        FollowNearFragment fragment = new FollowNearFragment();
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
            CardConfig.initConfig(getContext());

            requestData();

            rootView = inflater.inflate(R.layout.fragment_follow_near, container, false);

            recyclerView = rootView.findViewById(R.id.follow_near_recycler);

            OverLayCardLayoutManager overLayCardLayoutManager = new OverLayCardLayoutManager();

            FollowCardAdapter followCardAdapter = new FollowCardAdapter(arrayListCards);

            recyclerView.setLayoutManager(overLayCardLayoutManager);

            recyclerView.setAdapter(followCardAdapter);

            ItemTouchHelper.SimpleCallback callback = new FollowCardCallback(0,ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, recyclerView,followCardAdapter,arrayListCards);

            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);

            itemTouchHelper.attachToRecyclerView(recyclerView);
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

    public PairingItemModel createPairingItemModel(String name, int icon, String sex, String signature, String age, String place)
    {
        PairingItemModel model = new PairingItemModel();

        model.userName = name;

        model.icon = icon;

        model.sex = sex;

        model.signature = signature;

        model.age = age;

        model.place = place;

        return model;
    }

    public void requestData()
    {
        arrayListCards.add(createPairingItemModel("草莓味小仙女",R.mipmap.beauty_0,"女","小哥哥，快来找我啊","24", "福建福州"));

        arrayListCards.add(createPairingItemModel("樱桃小丸子",R.mipmap.beauty_1,"女","期待爱情","25","福建福州"));

        arrayListCards.add(createPairingItemModel("笑着流泪",R.mipmap.beauty_2,"女","早起的鸟儿有虫吃,早安.","23","福建福州"));

        arrayListCards.add(createPairingItemModel("梦里花落知多少",R.mipmap.beauty_3,"女","做不到别随便承诺","27","福建福州"));

        arrayListCards.add(createPairingItemModel("我喜欢雨",R.mipmap.beauty_4,"女","活得漂亮~","26","福建福州"));

        arrayListCards.add(createPairingItemModel("悲欢离合",R.mipmap.beauty_5,"女","如人饮水冷暖自知","26","福建福州"));

        arrayListCards.add(createPairingItemModel("爱情没有对错",R.mipmap.beauty_6,"女","且行且珍惜","22","福建福州"));

        arrayListCards.add(createPairingItemModel("邂逅Moment",R.mipmap.beauty_7,"女","谁想去看电影?","18","福建福州"));

        arrayListCards.add(createPairingItemModel("向日葵的忧伤",R.mipmap.beauty_8,"女","明天会更好~","19","福建福州"));

        arrayListCards.add(createPairingItemModel("雨后彩虹",R.mipmap.beauty_9,"女","No zuo No Die,You Can Try!","29","福建福州"));

        arrayListCards.add(createPairingItemModel("冷暖自知",R.mipmap.beauty_10,"女","没头像的别加我","28","福建福州"));

        arrayListCards.add(createPairingItemModel("忧伤的姐姐",R.mipmap.beauty_11,"女","我决定放弃治疗.","24","福建福州"));

        arrayListCards.add(createPairingItemModel("卑微的承诺",R.mipmap.beauty_12,"女","世界那么大，我想去看看。","23","福建福州"));

        arrayListCards.add(createPairingItemModel("迷恋高跟鞋",R.mipmap.beauty_13,"女","承诺都是骗人的","31","福建福州"));

        arrayListCards.add(createPairingItemModel("开心就好",R.mipmap.beauty_14,"女","时间会冲淡一切。","30","福建福州"));

        arrayListCards.add(createPairingItemModel("有文化的深井冰",R.mipmap.beauty_15,"女","本地的一起出来玩呀！","32","福建福州"));

        arrayListCards.add(createPairingItemModel("爱做梦的女孩",R.mipmap.beauty_16,"女","不问成绩的话，我们还是亲戚。","20","福建福州"));

        arrayListCards.add(createPairingItemModel("愿时光温柔以待",R.mipmap.beauty_17,"女","空有一身撩妹本事，可惜我是个女的","21","福建福州"));

        arrayListCards.add(createPairingItemModel("春风十里",R.mipmap.beauty_18,"女","不约，别来找骂!","19","福建福州"));

        arrayListCards.add(createPairingItemModel("带刺的玫瑰",R.mipmap.beauty_19,"女","初心易得 始终难求","30","福建福州"));


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
