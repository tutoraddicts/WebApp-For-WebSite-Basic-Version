package com.app.webapp;

import android.os.Bundle;

public class MyThread extends Thread{
    public static MyThread newInstance() {
        
        Bundle args = new Bundle();
        
        MyThread fragment = new MyThread();
        fragment.setArguments(args);
        return fragment;
    }

    private void setArguments(Bundle args) {
    }


}
