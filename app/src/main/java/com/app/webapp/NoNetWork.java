package com.app.webapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class NoNetWork extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_net_work);
    }

    public void retry_btn() {
        Intent i = new Intent(this, webSitePage.class);
        startActivity(i);
    }
}