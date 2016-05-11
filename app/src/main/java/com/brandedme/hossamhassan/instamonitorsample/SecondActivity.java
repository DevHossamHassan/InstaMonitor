package com.brandedme.hossamhassan.instamonitorsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.brandedme.hossamhassan.instamonitor.InstaMonitor;
import com.brandedme.hossamhassan.instamonitor.Session;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecondActivity extends AppCompatActivity {
    ArrayList<Session>sessionsList;
    @Bind(R.id.tvDataMap)
    TextView tvData;
    InstaMonitor instaMonitor;
    @OnClick(R.id.btnGetData)void onGetDataClicked()
    {
        instaMonitor = InstaMonitor.getInstance();
        sessionsList = instaMonitor.getMonitorData();
        if (sessionsList != null) {
            for (Session session:sessionsList)
            {
                String data=tvData.getText()+session.getName()+"\t"+session.getTime();
                tvData.setText(data);
            }
        }
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
