package cn.lizhiyu.closeeye.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.adapter.MineArrayAdapter;
import cn.lizhiyu.closeeye.model.MineItemModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends Fragment
{
    private List<MineItemModel> listModel;

    private ListView listView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    public void initData()
    {
        listModel = new ArrayList<MineItemModel>();

        MineItemModel modelCollect = createModel(R.mipmap.home,"我的关注");

        MineItemModel modelMessage = createModel(R.mipmap.home,"我的消息");

        MineItemModel modelCache = createModel(R.mipmap.home,"清除缓存");

        MineItemModel modelPush = createModel(R.mipmap.home,"通知设置");

        MineItemModel modelFeedback= createModel(R.mipmap.home,"意见反馈");

        MineItemModel modelAbout= createModel(R.mipmap.home,"关于我们");

        listModel.add(modelCollect);

        listModel.add(modelMessage);

        listModel.add(modelCache);

        listModel.add(modelPush);

        listModel.add(modelFeedback);

        listModel.add(modelAbout);

        listModel.add(modelCollect);

        listModel.add(modelCollect);

    }

    private MineItemModel createModel(int icon,String text)
    {
        MineItemModel model = new MineItemModel();

        model.src = icon;

        model.text = text;

        return model;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_mine, container, false);

        initData();

        listView = (ListView)view.findViewById(R.id.mine_listView);

        MineArrayAdapter arrayAdapter = new MineArrayAdapter(getActivity(),R.layout.mineitem_layout,listModel);

        listView.setAdapter(arrayAdapter);

        View viewHead = inflater.inflate(R.layout.layout_minehead,container,false);

        listView.addHeaderView(viewHead);

        RecyclerView recyclerView = (RecyclerView) viewHead.findViewById(R.id.head_recycler1);

        return view;
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
