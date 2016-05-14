package com.brandedme.hossamhassan.instamonitorsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.brandedme.hossamhassan.instamonitor.InstaMonitor;
import com.brandedme.hossamhassan.instamonitor.model.Session;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecondActivity extends AppCompatActivity {
    ArrayList<Session> sessionsList;
    @Bind(R.id.tvDataMap)
    TextView tvData;

    @OnClick(R.id.btnGetData)
    void onGetDataClicked() {
        getData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        TextView tvData=(TextView)findViewById(R.id.tvDataMap);


    }
    void getData()
    {
        sessionsList = InstaMonitor.getInstance().getMonitorData();
        if (sessionsList != null) {
            for (Session session : sessionsList) {
                String data = tvData.getText() + session.getShortName() + "\t" + session.getTime()+"\n";
                tvData.setText(data);
            }
        } else
            tvData.setText("Null");
    }

}
