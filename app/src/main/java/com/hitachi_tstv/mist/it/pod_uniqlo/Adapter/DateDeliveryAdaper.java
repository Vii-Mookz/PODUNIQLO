package com.hitachi_tstv.mist.it.pod_uniqlo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hitachi_tstv.mist.it.pod_uniqlo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vipavee on 15/02/2018.
 */

public class DateDeliveryAdaper extends BaseAdapter {
    Context context;
    String[] dateStrings;
    DateViewHolder dateViewHolder;

    public DateDeliveryAdaper(Context context, String[] dateStrings) {
        this.context = context;
        this.dateStrings = dateStrings;

    }

    @Override
    public int getCount() {
        return dateStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.date_listview, viewGroup, false);
            dateViewHolder = new DateViewHolder(view);
            view.setTag(dateViewHolder);
        } else {
            dateViewHolder = (DateViewHolder) view.getTag();
        }

        return view;
    }



    static class DateViewHolder {
        @BindView(R.id.txtDLVDate)
        TextView txtDLVDate;
        @BindView(R.id.txtDLVSumjob)
        TextView txtDLVSumjob;

        DateViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
