package com.hitachi_tstv.mist.it.pod_uniqlo.Fragment;


import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hitachi_tstv.mist.it.pod_uniqlo.Adapter.JobAdapter;
import com.hitachi_tstv.mist.it.pod_uniqlo.Bean.GetJobList;
import com.hitachi_tstv.mist.it.pod_uniqlo.Constant;
import com.hitachi_tstv.mist.it.pod_uniqlo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListJobFragment extends Fragment {


    @BindView(R.id.tripListviewTrip)
    ListView tripListviewTrip;
    Unbinder unbinder;

    String TAG = ListJobFragment.class.getSimpleName();
    String[] doNoStrings, storeCodeStrings, locationStrings,runningNoStrings,storeTypeStrings,statusStrings,numberStrings, loginStrings, driverNameStrings;
    String dateString,truckString,deliveryDateString;
    @BindView(R.id.dateBtnTrip)
    Button dateBtnTrip;
    @BindView(R.id.truckIdLblTrip)
    TextView truckIdLblTrip;
    @BindView(R.id.txtLicensePlate)
    TextView txtLicensePlate;
    @BindView(R.id.truckTypeLblTrip)
    TextView truckTypeLblTrip;
    @BindView(R.id.txtDriverName)
    TextView txtDriverName;
    @BindView(R.id.middenLinTrip)
    LinearLayout middenLinTrip;


    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu,menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle(R.string.alert);
                dialog.setCancelable(true);
                dialog.setIcon(R.drawable.warning);
                dialog.setMessage(R.string.alert_logout);

                dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getContext(),MainFragment.class);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loginStrings = getArguments().getStringArray("Login");
       deliveryDateString =  new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        View view = inflater.inflate(R.layout.fragment_list_job, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (driverNameStrings == null) {
//
            if (loginStrings[1].equals("null")){
                String name = loginStrings[0];
                txtDriverName.setText(name);
            }else{
                String name = loginStrings[0] + " " + loginStrings[1];
                txtDriverName.setText(name);
            }


        }
        txtLicensePlate.setText(loginStrings[2]);


        Log.d("DATE11", deliveryDateString);

        Log.d("TAG:", "Bool 1 ==> " + (deliveryDateString.equals("")) + " Bool 2 ==> " + (deliveryDateString == "") + " Date ==> " + deliveryDateString);
        if (deliveryDateString.equals("")) {
            SynGetJobList synGetJobList = new SynGetJobList(this, loginStrings[0]);
            synGetJobList.execute();
        } else {
            SynGetJobList synGetJobList = new SynGetJobList(this, loginStrings[0], deliveryDateString);
            synGetJobList.execute();
        }
setHasOptionsMenu(true);
        return view;
    }



    protected class SynGetJobList extends AsyncTask<Void, Void, String> {
        private Context context;
        private String truckIDString;
        private String deliveryDateString ;

        public SynGetJobList(ListJobFragment context, String truckIDString, String deliveryDateString) {
//            this.context = context;
            this.truckIDString = truckIDString;
            this.deliveryDateString = deliveryDateString;
        }

        public SynGetJobList(ListJobFragment context, String truckIDString) {
//            this.context = context;
            this.truckIDString = truckIDString;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {

                // 1. connect server with okHttp
                OkHttpClient client = new OkHttpClient();

                // 2. assign get data

                Request request = new Request.Builder().url(Constant.urlGetJobList +loginStrings[2] +"/"+"02-02-2018").build();
//                Request request = new Request.Builder().url(Constant.urlGetJobList +loginStrings[2] +"/"+deliveryDateString).build();

                // 3. transport request to server
                Response response = client.newCall(request).execute();

                String result = response.body().string();
                String refomat1 = reformat(result);
                Log.d("TAG:","Request:" +request);

                Log.d("TAG:", "ResultGetJobList"+ refomat1);

                // parse json string with gson
                Gson gson = new Gson();

                GetJobList getJobList = gson.fromJson(refomat1, GetJobList.class);

                Log.d("TAG:", "Getdata"+ String.valueOf(getJobList.getData().size()));
                Log.d("TAG:","StoreCode" + getJobList.getData().get(0).getStoreCode());

                return refomat1;

            } catch (Exception e) {
                Log.d("TAG:","Error1: "+ e.getMessage().toString());
//                Log.d("TAG:","UNIQLO-Tag-Main", "e ==> " + e + " Line " + e.getStackTrace()[0].getLineNumber());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("UNIQLO-Tag", s);

            try {
                            JSONObject jsonObject = new JSONObject(s);

                JSONArray dataJsonArray = jsonObject.getJSONArray("data");
                doNoStrings = new String[dataJsonArray.length()];
                locationStrings = new String[dataJsonArray.length()];
                storeCodeStrings = new String[dataJsonArray.length()];
                runningNoStrings = new String[dataJsonArray.length()];
                storeTypeStrings = new String[dataJsonArray.length()];
                statusStrings = new String[dataJsonArray.length()];
                numberStrings = new String[dataJsonArray.length()];

                for (int i = 0; i<dataJsonArray.length();i++) {
                    JSONObject jsonObject1 = dataJsonArray.getJSONObject(i);
                    doNoStrings[i] = jsonObject1.getString("DO");
                    locationStrings[i] = jsonObject1.getString("Location");
                    storeCodeStrings[i] = jsonObject1.getString("StoreCode");
                    runningNoStrings[i] = jsonObject1.getString("Running_No");
                    storeTypeStrings[i] = jsonObject1.getString("StoreType");
                    statusStrings[i] = jsonObject1.getString("Status");
                    numberStrings[i] = String.valueOf(i + 1);
                }

//                dateBtnTrip.setText(jsonObject.getString("dateDel"));
                dateBtnTrip.setText(deliveryDateString);
                Log.d("TAG:","dateString" + deliveryDateString);

                JobAdapter jobAdapter = new JobAdapter(getActivity(), storeCodeStrings, locationStrings, loginStrings, numberStrings, doNoStrings,storeTypeStrings);
                tripListviewTrip.setAdapter(jobAdapter);


            } catch (JSONException e) {
                e.printStackTrace();

            }


        }

//        @Override
//        public void onDestroyView() {
//            super.onDestroyView();
//            unbinder.unbind();
//        }
private String reformat(String s) {
    String result = s;
    result = result.replaceFirst("\"","");
    if (result.endsWith("\"")){
        result = result.substring(0,result.length()-1);
    }
    result = result.replace("\\","");

    return result;
}
        @OnClick(R.id.dateBtnTrip)
        public void onViewClicked() {

        }
    }


}