package com.hitachi_tstv.mist.it.pod_uniqlo;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.hitachi_tstv.mist.it.pod_uniqlo.Fragment.MainFragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
//            Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolbar);
//            setSupportActionBar(toolbar);
            MainFragment mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.contentFragment, mainFragment).commit();
        }
//        Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolbar);
//        setSupportActionBar(toolbar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:

                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle(R.string.alert);
                dialog.setCancelable(true);
                dialog.setIcon(R.drawable.warning);
                dialog.setMessage(R.string.alert_logout);

                dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        ComponentName componentName = intent.getComponent();
                        Intent backToMainIntent = IntentCompat.makeRestartActivityTask(componentName);
                        startActivity(backToMainIntent);
                    }
                });

                dialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                dialog.show();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

}
