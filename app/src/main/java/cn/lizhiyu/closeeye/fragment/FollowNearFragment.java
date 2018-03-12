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

            for (int i = 0; i < 10; i++)
            {
                FollowCardItemModel model = new FollowCardItemModel();

                model.setResouce(R.mipmap.choice_topview_bg);

                arrayListCards.add(model);
            }

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
