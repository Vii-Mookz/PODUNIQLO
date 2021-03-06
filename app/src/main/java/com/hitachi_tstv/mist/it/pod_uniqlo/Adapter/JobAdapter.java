package com.hitachi_tstv.mist.it.pod_uniqlo.Adapter;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hitachi_tstv.mist.it.pod_uniqlo.Fragment.JobFragment;
import com.hitachi_tstv.mist.it.pod_uniqlo.R;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vipavee on 30/01/2018.
 */

public class JobAdapter extends BaseAdapter {
    private static final String TAG = JobAdapter.class.getSimpleName();
    private Context context;
    private String[]  storeCodeStrings,locationStrings,loginStrings,numberStrings, doNoStrings,storeTypeStrings,runningNoStrings,statusStrings;
    private ViewHolder viewHolder;
    private String name = "JobAdapter";
    private String deliveryDateString;


    public JobAdapter(Context context, String[] storeCodeStrings, String[] locationStrings , String[] loginStrings , String[] numberStrings , String[] doNoStrings, String[] storeTypeStrings, String[] runningNoStrings,String deliveryDateString, String[] statusStrings) {
        this.context = context;
        this.storeCodeStrings = storeCodeStrings;
        this.locationStrings = locationStrings;
        this.loginStrings = loginStrings;
        this.numberStrings = numberStrings;
        this.doNoStrings = doNoStrings;
        this.storeTypeStrings = storeTypeStrings;
        this.runningNoStrings = runningNoStrings;
        this.deliveryDateString = deliveryDateString;
        this.statusStrings = statusStrings;

    }

    @Override
    public int getCount() {
        return numberStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.job_listview, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.DOTextviewTL.setText("DO No. "+doNoStrings[i]);
        viewHolder.sourceTxt.setText("Source Code: "+storeCodeStrings[i]);
        viewHolder.locationTxt.setText("Location: "+locationStrings[i]);
        if (statusStrings[i].equals("Complete")) {
                viewHolder.linearTrip.setForeground(context.getDrawable(R.drawable.layout_bg_3));
                viewHolder.linearTrip.setClickable(true);

        } else {
            viewHolder.linearTrip.setForeground(null);
            viewHolder.linearTrip.setClickable(false);

        viewHolder.linearTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(name +"TAG:", "onClick: " + Arrays.toString(loginStrings));
                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                JobFragment jobFragment = new JobFragment();
                Bundle bundle = new Bundle();

                bundle.putStringArray("Login", loginStrings);
                bundle.putString("DO", doNoStrings[i]);
                bundle.putString("StoreCode", storeCodeStrings[i]);
                bundle.putString("Location",locationStrings[i]);
                bundle.putString("StoreType",storeTypeStrings[i]);
                bundle.putString("Running_No" ,runningNoStrings[i]);
                bundle.putString("Date", deliveryDateString);

                jobFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.contentFragment, jobFragment,"Job").addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        }
        return view;
    }



    static class ViewHolder {
        @BindView(R.id.DOTextviewTL)
        TextView DOTextviewTL;
        @BindView(R.id.sourceTxt)
        TextView sourceTxt;
        @BindView(R.id.locationTxt)
        TextView locationTxt;
        @BindView(R.id.linearTrip)
        LinearLayout linearTrip;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
