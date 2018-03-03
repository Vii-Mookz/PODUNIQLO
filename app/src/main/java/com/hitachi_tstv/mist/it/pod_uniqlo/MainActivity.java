package com.hitachi_tstv.mist.it.pod_uniqlo;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hitachi_tstv.mist.it.pod_uniqlo.Fragment.MainFragment;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity TAG:";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {

            MainFragment mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.contentFragment, mainFragment,"main").commit();

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {

        if (!(getSupportFragmentManager().findFragmentByTag("main") == null)) {
            if (getSupportFragmentManager().findFragmentByTag("main").isVisible()) {
                Log.d(TAG, "onBackPressed: " + "Main");
            }
        }

        if (!(getSupportFragmentManager().findFragmentByTag("ListJob") == null)) {
            if (getSupportFragmentManager().findFragmentByTag("ListJob").isVisible()) {
                Log.d(TAG, "onBackPressed: " + "ListJob");


            }
//            createToolbar();
        }

        if (!(getSupportFragmentManager().findFragmentByTag("DeliveryDate") == null)) {
            if (getSupportFragmentManager().findFragmentByTag("DeliveryDate").isVisible()) {
                Log.d(TAG, "onBackPressed: " + "DeliveryDate");
                super.onBackPressed();
            }
        }
        if (!(getSupportFragmentManager().findFragmentByTag("Job") == null)) {
            if (getSupportFragmentManager().findFragmentByTag("Job").isVisible()) {
                Log.d(TAG, "onBackPressed: " + "Job");
                super.onBackPressed();
            }
        }
        if (!(getSupportFragmentManager().findFragmentByTag("Transfer") == null)) {
            if (getSupportFragmentManager().findFragmentByTag("Transfer").isVisible()) {
                Log.d(TAG, "onBackPressed: "+ "Transfer");
                super.onBackPressed();
            }
        }

    }


}