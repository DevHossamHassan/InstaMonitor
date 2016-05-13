package com.brandedme.hossamhassan.instamonitorsample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brandedme.hossamhassan.instamonitor.Fragment.InstaFragmentS4;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Hossam on 5/12/2016.
 */
public class FragmentFirst extends InstaFragmentS4 {
   @OnClick(R.id.btnExit)void onExit()
    {
        getActivity().onBackPressed();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
