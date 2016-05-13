package com.brandedme.hossamhassan.instamonitor.Fragment;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brandedme.hossamhassan.instamonitor.InstaMonitor;
import com.brandedme.hossamhassan.instamonitor.util.InstaLog;
import com.brandedme.hossamhassan.instamonitor.util.Prefs;

/**
 * InstaFragment extended from Fragment
 * for tracking Fragments that will extend it
 * Created by Hossam on 5/11/2016.
 */
public class InstaFragment extends Fragment {
    /**
     * Instantiates a new Insta fragment.
     */
    public InstaFragment() {
        super();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        InstaLog.d("onAttach : "+this.getClass().getName());
        Prefs.addFragmentIdToShared(this.getActivity(),this.getClass().getName());
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InstaLog.d("onCreate : "+this.getClass().getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        InstaLog.d("onCreateView : "+this.getClass().getName());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InstaLog.d("onViewCreated : "+this.getClass().getName());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InstaLog.d("onActivityCreated : "+this.getClass().getName());
    }

    @Override
    public void onStart() {
        super.onStart();
        InstaLog.d("onStart : "+this.getClass().getName());
    }

    @Override
    public void onResume() {
        super.onResume();
        String fragmentName=this.getClass().getName();
        InstaLog.d("onResume : "+fragmentName);
        Prefs.setLongPreference(this.getActivity(), fragmentName + Prefs.START, System.currentTimeMillis());

    }

    @Override
    public void onPause() {
        super.onPause();
        InstaLog.d("onPause : "+this.getClass().getName());
        InstaMonitor.getInstance().calculateSession(this.getActivity(),this.getClass().getName());
    }

    @Override
    public void onStop() {
        super.onStop();
        InstaLog.d("onStop : "+this.getClass().getName());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        InstaLog.d("onSaveInstanceState : "+this.getClass().getName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        InstaLog.d("onDestroy : "+this.getClass().getName());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        InstaLog.d("onDetach : "+this.getClass().getName());
    }
}
