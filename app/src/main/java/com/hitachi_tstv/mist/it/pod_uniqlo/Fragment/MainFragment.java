package com.hitachi_tstv.mist.it.pod_uniqlo.Fragment;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hitachi_tstv.mist.it.pod_uniqlo.Bean.Login;
import com.hitachi_tstv.mist.it.pod_uniqlo.Constant;
import com.hitachi_tstv.mist.it.pod_uniqlo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

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
public class MainFragment extends Fragment {


    @BindView(R.id.usernameEditText)
    EditText usernameEditText;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;
    @BindView(R.id.login_btn)
    Button loginBtn;
    Unbinder unbinder;


    private String usernameString, passwordString;
    private String[] loginStrings,    truckRegString,driverNameString,driverSurname,transportIDString,truckIDString;
    String TAG = MainFragment.class.getSimpleName();
    String name = "MainFragment ";

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                } else {
                    //not granted
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //What is permission be request
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET,Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.BLUETOOTH,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_CONTACTS}, 101);

    }

    //Check the permission is already have
    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.GET_ACCOUNTS);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (!checkIfAlreadyhavePermission()) {
            requestForSpecificPermission();
        }

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.login_btn)
    public void onViewClicked() {
        usernameString = usernameEditText.getText().toString();
        passwordString = passwordEditText.getText().toString();

        SyncCheckLogin syncCheckLogin = new SyncCheckLogin(getActivity(), usernameString, passwordString);
        syncCheckLogin.execute();

    }
    private class SyncCheckLogin extends AsyncTask<Void, Void, String>  {
        private Context context;
        private String usernameString, passwordString;
  
        public SyncCheckLogin(Context context, String usernameString, String passwordString) {
            this.context = context;
            this.usernameString = usernameString;
            this.passwordString = passwordString;
        }


        @Override
        protected String doInBackground(Void... voids) {


            try {

                // 1. connect server with okHttp
                OkHttpClient client = new OkHttpClient();

                // 2. assign get data

                Request request = new Request.Builder().url(Constant.urlGetUser +usernameString +"/"+passwordString).build();
//                Request request = new Request.Builder().url(Constant.urlGetUser ).build();
                // 3. transport request to server
                Response response = client.newCall(request).execute();

                String result = response.body().string();
                String refomat1 = reformat(result);

                Log.d(name +"TAG:", "Result"+ refomat1);

                // parse json string with gson
                Gson gson = new Gson();

                Login login = gson.fromJson(refomat1, Login.class);

                Log.d(name +"TAG:", "Getdata"+ String.valueOf(login.getData().size()));
                Log.d(name +"TAG:","Drivername" + login.getData().get(0).getDriverName());

                return refomat1;

            } catch (Exception e) {
                Log.d(name +"TAG:","Error1: "+ e.getMessage().toString());
//                Log.d("UNIQLO-Tag-Main", "e ==> " + e + " Line " + e.getStackTrace()[0].getLineNumber());
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);

            if (!(s == null)) {

                Log.d(name +"TAG:","result111" + s.substring(1, s.length()-1));



                try {
                    Log.d(name +"TAG:", "onPostExecute: " + s);
                    reformat(s);


                    JSONObject jsonObject = new JSONObject(s);


                    JSONArray dataJsonArray = jsonObject.getJSONArray("data");
                    truckIDString = new String[dataJsonArray.length()];
                    truckRegString = new String[dataJsonArray.length()];
                    driverNameString= new String[dataJsonArray.length()];
                    driverSurname = new String[dataJsonArray.length()];
                    transportIDString = new String[dataJsonArray.length()];

                    Log.d(name +"TAG:", "Result:" + dataJsonArray.length());

                    for (int i = 0; i<dataJsonArray.length();i++) {
                        JSONObject jsonObject1 = dataJsonArray.getJSONObject(i);
                        truckIDString[i] = jsonObject1.getString("TruckCode");
                        truckRegString[i] = jsonObject1.getString("TruckReg");
                        driverNameString[i] = jsonObject1.getString("DriverName");
                        driverSurname[i] = jsonObject1.getString("Driversirname");
                        transportIDString[i] = jsonObject1.getString("TransportID");
                        Log.d(name +"TAG:","result111" + jsonObject1);
                    }


                    String[] loginStrings = new String[]{driverNameString[0], driverSurname[0], truckRegString[0], usernameString,truckIDString[0]};

                    String loginstringsArrays = Arrays.toString(loginStrings);

                    FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                    //Send Arguments
                    ListJobFragment listJobFragment = new ListJobFragment();
                    Bundle args = new Bundle();
                    args.putStringArray("Login", loginStrings);
                    args.putString("TruckReg" ,truckRegString[0]);
                    args.putString("TruckID",truckIDString[0]);
                    args.putString("Date","");
                    listJobFragment.setArguments(args);

                    fragmentTransaction.replace(R.id.contentFragment, listJobFragment,"ListJob");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();



                    Log.d(name +"TAG:","driverName" + loginstringsArrays);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(name +"TAG:", String.valueOf(e) + " Line: " + e.getStackTrace()[0].getLineNumber());
                }

            } else {
                Toast.makeText(context, getResources().getText(R.string.errLogin), Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, "Network Crash, Try Again Later", Toast.LENGTH_SHORT).show();
            }
        }
        private String reformat(String s) {
            String result = s;
            result = result.replaceFirst("\"","");
            if (result.endsWith("\"")){
                result = result.substring(0,result.length()-1);
            }
            result = result.replace("\\","");

            return result;
        }
    }

}