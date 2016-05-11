package com.brandedme.hossamhassan.instamonitor_sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @OnClick(R.id.btnFeedback)void onFeedBackClicked()
    {
        startActivity(new Intent(this,SecondActivity.class));
    }
    @OnClick(R.id.btnReport)void onReportClicked()
    {
        //Instabug.invoke(IBGInvocationMode.IBGInvocationModeFeedbackSender);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }
}
