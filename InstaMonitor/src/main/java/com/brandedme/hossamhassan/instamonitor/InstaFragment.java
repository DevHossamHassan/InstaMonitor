package com.brandedme.hossamhassan.instamonitor;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Hossam on 5/11/2016.
 */
public class InstaFragment extends Fragment {
    String TAG=InstaMonitor.TAG;
    public InstaFragment() {
        super();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach: "+this.getTag().getClass().getName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: "+this.getTag().getClass().getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: "+this.getTag().getClass().getName());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: "+this.getTag().getClass().getName());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated: "+this.getTag().getClass().getName());
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: "+this.getTag().getClass().getName());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: "+this.getTag().getClass().getName());
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: "+this.getTag().getClass().getName());
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: "+this.getTag().getClass().getName());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState: "+this.getTag().getClass().getName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: "+this.getTag().getClass().getName());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach: "+this.getTag().getClass().getName());
    }
}
