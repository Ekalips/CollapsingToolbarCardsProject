package com.wldev.tttest;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    String TAG = "lol";
    Fragment fragment1;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment1 = new MainFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer,fragment1)
                .commit();



    }


}
