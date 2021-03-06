package cn.lizhiyu.closeeye.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.activity.FeedbackActivity;
import cn.lizhiyu.closeeye.activity.LoginActivity;
import cn.lizhiyu.closeeye.activity.RegisterActivity;
import cn.lizhiyu.closeeye.activity.SettingActivity;
import cn.lizhiyu.closeeye.adapter.MineArrayAdapter;
import cn.lizhiyu.closeeye.adapter.MineMessageAdapter;
import cn.lizhiyu.closeeye.adapter.MineNumerAdapter;
import cn.lizhiyu.closeeye.model.MineItemModel;
import cn.lizhiyu.closeeye.model.MineMessageModel;
import cn.lizhiyu.closeeye.model.MineRecylerModel;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

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

    private RecyclerView recyclerViewNumer;

    private RecyclerView.LayoutManager rLayoutManager;

    private MineNumerAdapter recyclerAdapterNumer;

    private RecyclerView recyclerViewMessage;

    private MineMessageAdapter recyclerAdapterMessage;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private View rootView;

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

        MineItemModel modelCollect = createModel(R.drawable.mine_history,"历史记录");

        MineItemModel modelMessage = createModel(R.drawable.mine_feedback,"意见反馈");

        MineItemModel modelCache = createModel(R.drawable.mine_share_app,"分享闭眼");

        MineItemModel modelPush = createModel(R.drawable.mine_setting,"设置与帮助");

        listModel.add(modelCollect);

        listModel.add(modelMessage);

        listModel.add(modelCache);

        listModel.add(modelPush);
    }

    private ArrayList<MineRecylerModel> getMineHeadRecyclerList()
    {
        ArrayList list = new ArrayList<MineRecylerModel>();

        MineRecylerModel modelFocus = this.createHeadRecylerModel("0","关注",1);

        MineRecylerModel modelFans = this.createHeadRecylerModel("0","粉丝",2);

        MineRecylerModel modelCollection = this.createHeadRecylerModel("0","关注",3);

        MineRecylerModel modelArticle = this.createHeadRecylerModel("0","文章",4);

        list.add(modelFocus);

        list.add(modelFans);

        list.add(modelCollection);

        list.add(modelArticle);

        return list;
    }

    private ArrayList<MineMessageModel> getMineHeadMessageList()
    {
        ArrayList list = new ArrayList<MineMessageModel>();

        MineMessageModel modelReply = this.createMineMessageModel(R.drawable.mine_download,"下载记录",1);

        MineMessageModel modelNoti = this.createMineMessageModel(R.drawable.mine_manuscript,"我的投稿",1);

        MineMessageModel modelMineMessage = this.createMineMessageModel(R.drawable.mine_mail,"私信",1);

        MineMessageModel modelSysMessage = this.createMineMessageModel(R.drawable.mine_notifications,"系统通知",1);

        list.add(modelReply);

        list.add(modelNoti);

        list.add(modelMineMessage);

        list.add(modelSysMessage);

        return list;
    }

    private MineMessageModel createMineMessageModel(int src,String title,int id)
    {
        MineMessageModel model = new MineMessageModel();

        model.src = src;

        model.title = title;

        model.id = id;

        return model;
    }

    private MineRecylerModel createHeadRecylerModel(String number, String title, int id)
    {
        MineRecylerModel model = new MineRecylerModel();

        model.number = number;

        model.title = title;

        model.id = id;

        return model;
    }

    private MineItemModel createModel(int icon,String text)
    {
        MineItemModel model = new MineItemModel();

        model.src = icon;

        model.text = text;

        return model;

    }


    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView == null)
        {
            // Inflate the layout for this fragment
            rootView =  inflater.inflate(R.layout.fragment_mine, container, false);

            initData();

            listView = (ListView)rootView.findViewById(R.id.mine_listView);

            MineArrayAdapter arrayAdapter = new MineArrayAdapter(getActivity(),R.layout.mineitem_layout,listModel);

            listView.setAdapter(arrayAdapter);

            listView.setDivider(null);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                   switch (position)
                   {
                       case 0:
                       {
                           break;
                       }

                       case 1:
                       {

                           break;
                       }

                       case 2:
                       {
                           Intent intent = new Intent(getActivity(), FeedbackActivity.class);

                           startActivity(intent);

                           break;
                       }

                       case 3:
                       {

                           break;
                       }

                       case 4:
                       {
                           Intent intent = new Intent(getActivity(), SettingActivity.class);

                           startActivity(intent);

                           break;
                       }

                       default:
                           break;
                   }
                }
            });

            View viewHead = inflater.inflate(R.layout.layout_minehead,container,false);

            ImageView imageViewUserIcon = viewHead.findViewById(R.id.mine_user_icon);

            Glide.with(getActivity()).load(R.mipmap.beauty_0).bitmapTransform(new CropCircleTransformation(getActivity())).crossFade(1000).into(imageViewUserIcon);

            Button buttonLogin = viewHead.findViewById(R.id.mine_button_login);

            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);

                    startActivity(intent);
                }
            });

            Button buttonRegister = viewHead.findViewById(R.id.mine_button_register);

            buttonRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(getActivity(), RegisterActivity.class);

                    startActivity(intent);
                }
            });

            listView.addHeaderView(viewHead);

            recyclerViewNumer = (RecyclerView)viewHead.findViewById(R.id.head_recyclerHeadCount);

            rLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);

            recyclerAdapterNumer = new MineNumerAdapter(getMineHeadRecyclerList());

            recyclerViewNumer.setAdapter(recyclerAdapterNumer);

            recyclerViewNumer.setLayoutManager(rLayoutManager);

            recyclerViewMessage = (RecyclerView)viewHead.findViewById(R.id.head_recyclerHeadMessage);

            rLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);

            recyclerAdapterMessage = new MineMessageAdapter(getMineHeadMessageList());

            recyclerViewMessage.setAdapter(recyclerAdapterMessage);

            recyclerViewMessage.setLayoutManager(rLayoutManager);

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
