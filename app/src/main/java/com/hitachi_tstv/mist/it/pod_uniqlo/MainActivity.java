package com.hitachi_tstv.mist.it.pod_uniqlo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hitachi_tstv.mist.it.pod_uniqlo.Fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            MainFragment mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.contentFragment, mainFragment).commit();
        }
    }
}
