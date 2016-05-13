package com.brandedme.hossamhassan.instamonitorsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.brandedme.hossamhassan.instamonitor.InstaMonitor;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @OnClick(R.id.btnRestStates)void onRestClick()
    {
        InstaMonitor.getInstance().resetSessionsState();
        Toast.makeText(MainActivity.this, "All sessions duration removed !", Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.btnFeedback)void onFeedBackClicked()
    {
        startActivity(new Intent(this,SecondActivity.class));
    }
    @OnClick(R.id.btnReport)void onReportClicked()
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.addToBackStack("First");
        ft.replace(R.id.main_container, new FragmentFirst(), "First").commit();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

}
