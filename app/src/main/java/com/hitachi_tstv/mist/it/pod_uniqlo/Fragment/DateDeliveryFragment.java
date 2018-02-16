package com.hitachi_tstv.mist.it.pod_uniqlo.Fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hitachi_tstv.mist.it.pod_uniqlo.Bean.GetDate;
import com.hitachi_tstv.mist.it.pod_uniqlo.Constant;
import com.hitachi_tstv.mist.it.pod_uniqlo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DateDeliveryFragment extends Fragment {


    Unbinder unbinder;
    String[] loginStrings, deliveryDateStrings, doAmountStrings;
    String dateString;
    @BindView(R.id.lisDADate)
    ListView lisDADate;


    public DateDeliveryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_date_delivery, container, false);
        unbinder = ButterKnife.bind(this, view);
        loginStrings = getArguments().getStringArray("Login");
        dateString = getArguments().getString("Date");

        SyncGetDate syncGetDate = new SyncGetDate(getActivity());
        syncGetDate.execute();
        return view;


    }


    //        @OnClick(R.id.lisDADate)
//    public void onViewClicked(AdapterView<?> parent, View view, int position, long id) {
//
//
//            FragmentManager fragmentManager = getFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//            ListJobFragment listJobFragment = new ListJobFragment();
//            Bundle bundle = new Bundle();
//
//            bundle.putStringArray("Login", loginStrings);
//            bundle.putString("Date", deliveryDateStrings[position]);
//
//
//            listJobFragment.setArguments(bundle);
//
//            fragmentTransaction.replace(R.id.contentFragment, listJobFragment);
//            fragmentTransaction.commit();
//
//
//            Log.d("Tag", "Send ==> " + deliveryDateStrings[position] + " " + Arrays.toString(loginStrings));
//
//        }
    class SyncGetDate extends AsyncTask<Void, Void, String> {
        Context context;


        public SyncGetDate(Context context) {
            this.context = context;

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... params) {
            try {

                // 1. connect server with okHttp
                OkHttpClient client = new OkHttpClient();

                // 2. assign get data

                Request request = new Request.Builder().url(Constant.urlGetDate + loginStrings[2]).build();
//                Request request = new Request.Builder().url(Constant.urlGetJobList +truckIDString +"/"+deliveryDateString).build();

                // 3. transport request to server
                Response response = client.newCall(request).execute();

                String result = response.body().string();
                String refomat1 = reformat(result);
                Log.d("TAG:", "Request:" + request);

                Log.d("TAG:", "ResultGetJobList" + refomat1);

                // parse json string with gson
                Gson gson = new Gson();

                GetDate getDate = gson.fromJson(refomat1, GetDate.class);

                Log.d("TAG:", "Getdata" + String.valueOf(getDate.getData().size()));
                Log.d("TAG:", "StoreCode" + getDate.getData().get(0).getDeliveryDate());

                return refomat1;

            } catch (Exception e) {
                Log.d("TAG:", "Error1: " + e.getMessage().toString());
//                Log.d("TAG:","UNIQLO-Tag-Main", "e ==> " + e + " Line " + e.getStackTrace()[0].getLineNumber());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Tag", "S ==> " + s);

            try {
                JSONObject jsonObject = new JSONObject(s);

                JSONArray dataJsonArray = jsonObject.getJSONArray("data");
                deliveryDateStrings = new String[dataJsonArray.length()];
                doAmountStrings = new String[dataJsonArray.length()];


                for (int i = 0; i < dataJsonArray.length(); i++) {
                    JSONObject jsonObject1 = dataJsonArray.getJSONObject(i);
                    deliveryDateStrings[i] = jsonObject1.getString("DeliveryDate");
                    doAmountStrings[i] = jsonObject1.getString("DoAmount");

                }

                DateDeliveryAdaper dateDeliveryAdaper = new DateDeliveryAdaper(context, deliveryDateStrings, doAmountStrings);
                lisDADate.setAdapter(dateDeliveryAdaper);

            } catch (JSONException e) {
                Log.d("Tag", "Error Date Activity SyncGetDate on post JSONArray ==> " + e + " Line " + e.getStackTrace()[0].getLineNumber());

            }
        }

        //Date Adater
        public class DateDeliveryAdaper extends BaseAdapter {
            Context context;
            String[] dateStrings, doAmountString;
            DateViewHolder dateViewHolder;

            public DateDeliveryAdaper(Context context, String[] dateStrings, String[] doAmountString) {
                this.context = context;
                this.dateStrings = dateStrings;
                this.doAmountString = doAmountString;
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
            public View getView(final int i, View view, ViewGroup viewGroup) {
                if (view == null) {
                    LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = layoutInflater.inflate(R.layout.date_listview, viewGroup, false);
                    dateViewHolder = new DateViewHolder(view);
                    view.setTag(dateViewHolder);
                } else {
                    dateViewHolder = (DateViewHolder) view.getTag();
                }
                String date, job;

                date = getResources().getString(R.string.Date) + " : " + deliveryDateStrings[i];
//            job = jobStrings[position] + " " + getResources().getString(R.string.trip);
                dateViewHolder.txtDLVDate.setText(date);
                dateViewHolder.txtDLVSumjob.setText(doAmountStrings[i]);
                dateViewHolder.linearDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("TAG", "onClick: " + Arrays.toString(loginStrings));
                        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                        ListJobFragment listJobFragment = new ListJobFragment();
                        Bundle bundle = new Bundle();

                        bundle.putStringArray("Login", loginStrings);
                        bundle.putString("Date", deliveryDateStrings[i]);
                        bundle.putString("DoNo", doAmountStrings[i]);


                        listJobFragment.setArguments(bundle);

                        fragmentTransaction.replace(R.id.contentFragment, listJobFragment);
                        fragmentTransaction.commit();

                    }
                });
//            viewHolder.sumjobTextView.setText(job);
                return view;
            }


            class DateViewHolder {
                @BindView(R.id.txtDLVDate)
                TextView txtDLVDate;
                @BindView(R.id.txtDLVSumjob)
                TextView txtDLVSumjob;
                @BindView(R.id.linearDate)
                LinearLayout linearDate;
                DateViewHolder(View view) {
                    ButterKnife.bind(this, view);
                }
            }

        }


    }

    private String reformat(String s) {
        String result = s;
        result = result.replaceFirst("\"", "");
        if (result.endsWith("\"")) {
            result = result.substring(0, result.length() - 1);
        }
        result = result.replace("\\", "");

        return result;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}