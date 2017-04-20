package com.wldev.tttest;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wldev.tttest.databinding.RvItemBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    private static final String TAG = MainFragment.class.getSimpleName();

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =inflater.inflate(R.layout.fragment_main, container, false);


        LayManager m = new LayManager(getContext());
        m.setOrientation(LinearLayoutManager.HORIZONTAL);
        m.setScrollEnabled(false);



        CollapsingToolbarLayout layout = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        layout.setTitle("Cool restaurant");
        layout.setExpandedTitleColor(Color.WHITE);
        layout.setCollapsedTitleTextColor(Color.WHITE);
        layout.setTitleEnabled(true);

        Toolbar toolbar = (Toolbar) layout.findViewById(R.id.toolbar);
        toolbar.setPadding(0,getStatusBarHeight(),0,0);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_list);

        AppBarLayout appBarLayout = (AppBarLayout) rootView.findViewById(R.id.app_bar_layout);



        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float dencity = metrics.density;
        final float initMargin = - dencity*100;
        final float initHeight = 200*dencity;



        setSharedElementReturnTransition(TransitionInflater.from(
                getActivity()).inflateTransition(R.transition.change_image_transform));
        setExitTransition(TransitionInflater.from(
                getActivity()).inflateTransition(android.R.transition.fade));




        final RecyclerView rview = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        rview.setLayoutManager(m);
        rview.setAdapter(new RVAdapter(new RVAdapter.onClick() {
            @Override
            public void onClick(RvItemBinding binding) {
                SecondFragment endFragment = SecondFragment.newInstance((Integer) binding.image.getTag(),binding.title.getText().toString(),binding.descr.getText().toString());
                endFragment.setSharedElementEnterTransition(TransitionInflater.from(
                        getActivity()).inflateTransition(R.transition.change_image_transform));
                endFragment.setEnterTransition(TransitionInflater.from(
                        getActivity()).inflateTransition(android.R.transition.fade));

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, endFragment)
                        .addToBackStack("details")
                        .addSharedElement(binding.main, "card")
                        .addSharedElement(binding.image, "image")
                        .addSharedElement(binding.title, "main")
                        .addSharedElement(binding.descr, "descr")
                        .commit();

                Log.d(TAG, "onClick: ");
//                    getActivity().getSupportFragmentManager().beginTransaction()
//                            .add(R.id.fragmentContainer,new SecondFragment())
//                            .addToBackStack("lel")
//                            .commit();


            }
        }));



        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) rview.getLayoutParams();
                int topMargin = (int) ((double)((appBarLayout.getTotalScrollRange()-Math.abs(verticalOffset))/(double)appBarLayout.getTotalScrollRange())*initMargin);
                lp.topMargin = topMargin;
                rview.setLayoutParams(lp);

                int imageHeight = (int) ((double)((Math.abs(verticalOffset))/(double)appBarLayout.getTotalScrollRange())*initHeight);
//                Log.d(TAG, "onOffsetChanged: " + imageHeight);
                ((RVAdapter)rview.getAdapter()).changeImageWidth(imageHeight, imageHeight==initHeight);

                LayManager manager = (LayManager) rview.getLayoutManager();
//                Log.d(TAG, "onOffsetChanged: " +String.valueOf(topMargin));
                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0)
                {
                    //  Collapsed
//                    Log.d(TAG, "onOffsetChanged: eable");
                    manager.setScrollEnabled(true);
                }
                else
                {
//                    Log.d(TAG, "onOffsetChanged: disable");
                    manager.setScrollEnabled(false);
                }
            }
        });

        return rootView;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
