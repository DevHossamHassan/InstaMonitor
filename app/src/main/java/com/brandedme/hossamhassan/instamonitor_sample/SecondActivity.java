package com.brandedme.hossamhassan.instamonitor_sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.brandedme.hossamhassan.instamonitor.InstaMonitor;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecondActivity extends AppCompatActivity {
    HashMap<String, String> appData;
    @Bind(R.id.tvDataMap)
    TextView tvData;
    InstaMonitor instaMonitor;
    @OnClick(R.id.btnGetData)void onGetDataClicked()
    {
        instaMonitor = InstaMonitor.getInstance();
        appData = instaMonitor.getMonitorData();
        if (appData != null)
            tvData.setText(appData.toString());
        else
            tvData.setText("Null");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);


    }

}
