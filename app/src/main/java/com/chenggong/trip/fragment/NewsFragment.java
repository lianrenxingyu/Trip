package com.chenggong.trip.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenggong.trip.R;
import com.chenggong.trip.adapter.NewsAdapter;
import com.chenggong.trip.bean.News;
import com.chenggong.trip.ui.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenggong
 */


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recycler_news;

    private List<News> newsList;
    private NewsAdapter adapter;

    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment NewsFragment.
     */
    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Inflate the layout for this fragment
     * @param savedInstanceState
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        recycler_news = view.findViewById(R.id.recycler_news);

        newsList = new ArrayList<>();
        initData();
        adapter = new NewsAdapter(getContext(), newsList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler_news.setLayoutManager(layoutManager);
        recycler_news.addItemDecoration(new SpaceItemDecoration(4,16,16));
        recycler_news.setAdapter(adapter);
        return view;
    }

    public void initData(){
//        News news = new News("名字","我已经到了,我要去那里,我要去那里我要去那里","11:30","imagePath");
        for(int i = 0;i<15;i++){
            News news ;
            news = new News("名字","我已经到了,我要去那里,我要去那里我要去那里,aaa","11:30","imagePath");
            news.setName("名字"+String.valueOf(i));
            newsList.add(news);
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
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    /**
     * Activity中onCreate方法完成,fragment可以与Activity中的控件交互
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * 和Activity的交互接口
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
