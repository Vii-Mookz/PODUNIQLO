package com.hitachi_tstv.mist.it.pod_uniqlo.Fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hitachi_tstv.mist.it.pod_uniqlo.Constant;
import com.hitachi_tstv.mist.it.pod_uniqlo.R;
import com.hitachi_tstv.mist.it.pod_uniqlo.UtilityClass;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class JobFragment extends Fragment {

//
//    @BindView(R.id.txt_name)
//    TextView txtName;
    @BindView(R.id.txtDo)
    TextView txtDo;
    @BindView(R.id.txtDodt)
    TextView txtDodt;
    @BindView(R.id.txtsource)
    TextView txtsource;
    @BindView(R.id.txtsourcedtl)
    TextView txtsourcedtl;
    @BindView(R.id.txtlo)
    TextView txtlo;
    @BindView(R.id.txtlodtl)
    TextView txtlodtl;
    @BindView(R.id.img_4)
    ImageView img4;
    @BindView(R.id.img_5)
    ImageView img5;
    @BindView(R.id.img_6)
    ImageView img6;
    @BindView(R.id.img_7)
    ImageView img7;
    @BindView(R.id.linPDATop)
    LinearLayout linPDATop;
    @BindView(R.id.btn_savepic)
    Button btnSavepic;
    @BindView(R.id.btn_transfer)
    Button btnTransfer;
    @BindView(R.id.linPDABottom1)
    LinearLayout linPDABottom1;
    @BindView(R.id.btn_arrival)
    Button btnArrival;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.linPDABottom)
    LinearLayout linPDABottom;
    Unbinder unbinder;
    Boolean  imgPack1ABoolean, imgPack2ABoolean, imgDoc1ABoolean, imgDoc2ABoolean;
    private String  pathPack1String, pathPack2String, pathDoc1String, pathDoc2String;
    String storeCodeString,locationString, doNoString,storeTypeString,runningNoString,deliveryDateString;
    String[] indexFileNameStrings, fileNameStrings, filePathStrings,loginStrings;
    Uri pack1Uri, pack2Uri, doc1Uri, doc2Uri;
    String name = "JobFragment ";
    Boolean doubleBackPressABoolean = false;
    public JobFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loginStrings = getArguments().getStringArray("Login");
        doNoString = getArguments().getString("DO");
        storeCodeString = getArguments().getString("StoreCode");
        locationString = getArguments().getString("Location");
        storeTypeString = getArguments().getString("StoreType");
        runningNoString = getArguments().getString("Running_No");
        deliveryDateString = getArguments().getString("Date");


        View view = inflater.inflate(R.layout.fragment_job, container, false);
        unbinder = ButterKnife.bind(this, view);
        setData() ;
        setPic();

        txtDodt.setText(doNoString);
        txtlodtl.setText(locationString);
        txtsourcedtl.setText(storeCodeString);
        Log.d(name +"TAG:", "StoreType:" + storeTypeString);


        return view;

    }




    private void setData() {



        indexFileNameStrings = new String[]{"Package1.png", "Package2.png", "Document1.png", "Document2.png"};

        fileNameStrings = new String[indexFileNameStrings.length];
        filePathStrings = new String[indexFileNameStrings.length];

        pathPack1String = "";
        pathPack2String = "";
        pathDoc1String = "";
        pathDoc2String = "";


        imgPack1ABoolean = false;
        imgPack2ABoolean = false;
        imgDoc1ABoolean = false;
        imgDoc2ABoolean = false;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void setPic() {
        if (!storeTypeString.equals("Roadside")) {
        img4.setVisibility(View.INVISIBLE);
        img5.setVisibility(View.INVISIBLE);
        img6.setVisibility(View.INVISIBLE);
        img7.setVisibility(View.INVISIBLE);
        }
    }

// Arrival

    class SynUpdateArrival extends AsyncTask<Void, Void, String> {
        String latString, longString, timeString;
        Context context;
        UtilityClass utilityClass;

        public SynUpdateArrival(String latString, String longString, String timeString, Context context) {
            this.latString = latString;
            this.longString = longString;
            this.timeString = timeString;
            this.context = context;
            utilityClass = new UtilityClass(context);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... voids) {
            try {
                String arrival = "Arrival";
                Log.d(name +"TAG:","Arrival: "+ "Lat/Long : Running_No ==> " + runningNoString  + "," + loginStrings[2]+ "," + loginStrings[3] + "," + latString + "," + longString + "," + arrival);

//                String deviceId = utilityClass.getDeviceID();
//                String serial = utilityClass.getSerial();
//                String deviceName = utilityClass.getDeviceName();

                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
//                        .add("isAdd", "true")
                        .add("pTruckId", loginStrings[2] )
                        .add("pRunNo", runningNoString)
                        .add("pLatitude", latString)
                        .add("pLongitude", longString)
                        .add("pCheckType", arrival)
                        .add("pUser", loginStrings[3])
                        .build();

                Request.Builder builder = new Request.Builder();
                Request request = builder.url(Constant.urlSaveTimeStamp).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                Log.d(name +"TAG:", "e doInBack ==>" + e.toString() + "line::" + e.getStackTrace()[0].getLineNumber());
                return "";
            }
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(name +"TAG:", "onPostExecute:::-----> " + s);

        }
    }

    class SynUpdateDeparture extends AsyncTask<Void, Void, String> {
        String latString, longString, timeString;
        Context context;
        UtilityClass utilityClass;

        public SynUpdateDeparture(String latString, String longString, String timeString, Context context) {
            this.latString = latString;
            this.longString = longString;
            this.timeString = timeString;
            this.context = context;
            utilityClass = new UtilityClass(context);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... voids) {
            try {
                String departure = "Departure";
                Log.d(name +"TAG:","Departue: " + "Lat/Long : Running_No ==> " + runningNoString  + "," + loginStrings[2]+ "," + loginStrings[3] + "," + latString + "," + longString + "," + departure);

//                String deviceId = utilityClass.getDeviceID();
//                String serial = utilityClass.getSerial();
//                String deviceName = utilityClass.getDeviceName();

                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
//                        .add("isAdd", "true")
                        .add("pTruckId", loginStrings[2] )
                        .add("pRunNo", runningNoString)
                        .add("pLatitude", latString)
                        .add("pLongitude", longString)
                        .add("pCheckType", departure)
                        .add("pUser", loginStrings[3])
                        .build();

                Request.Builder builder = new Request.Builder();
                Request request = builder.url(Constant.urlSaveTimeStamp).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                Log.d(name +"TAG:", "e doInBack ==>" + e.toString() + "line::" + e.getStackTrace()[0].getLineNumber());
                return "";
            }
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(name +"TAG", "onPostExecute:::-----> " + s);

        }
    }
    @OnClick({R.id.btn_savepic, R.id.btn_transfer, R.id.btn_arrival, R.id.btn_confirm,R.id.img_4, R.id.img_5, R.id.img_6, R.id.img_7})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_savepic:
                break;
            case R.id.btn_transfer:
                FragmentManager fragmentManager =  getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                TransferFragment transferFragment = new TransferFragment();
                Bundle bundle = new Bundle();

                bundle.putStringArray("Login", loginStrings);
                bundle.putString("DO", doNoString);
                bundle.putString("StoreCode", storeCodeString);
                bundle.putString("Location",locationString);
                bundle.putString("StoreType",storeTypeString);
                bundle.putString("Running_No",runningNoString);
                bundle.putString("Date",deliveryDateString);

                transferFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.contentFragment, transferFragment,"Transfer").addToBackStack(null);
                fragmentTransaction.commit();
                break;
                //arrival
            case R.id.btn_arrival:
                final UtilityClass utilityClass = new UtilityClass(getActivity());
                if (utilityClass.setLatLong(0)) {
                    final String latitude = utilityClass.getLatString();
                    final String longitude = utilityClass.getLongString();

                    AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                    dialog.setTitle(R.string.alert);
//                    dialog.setIcon(R.drawable.warning);
                    dialog.setCancelable(true);
                    dialog.setMessage(R.string.arrivalDialog);

                    dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (!(latitude == null)) {
//                                if (loginStrings[4].equals("N")) {
                                    SynUpdateArrival syncUpdateArrival = new SynUpdateArrival(latitude, longitude, utilityClass.getTimeString(), getActivity());
                                    syncUpdateArrival.execute();
                                } else {
//                                    String distance = utilityClass.getDistanceMeter(storeLatString, storeLongString);
//                                    if (Double.parseDouble(distance) <= Double.parseDouble(storeRadiusString)) {
//                                        SynUpdateArrival syncUpdateArrival = new SynUpdateArrival(latitude, longitude, utilityClass.getTimeString(), getActivity());
//                                        syncUpdateArrival.execute();
//                                    } else {
                                        Toast.makeText(getActivity(), getResources().getString(R.string.gps_err), Toast.LENGTH_LONG).show();
//                                    }
                                }
//                            }
                        }
                    });

                    dialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                }

                break;

                // Departure
            case R.id.btn_confirm:
                final UtilityClass utilityClass1 = new UtilityClass(getActivity());
                if (utilityClass1.setLatLong(0)) {
                    final String latitude = utilityClass1.getLatString();
                    final String longitude = utilityClass1.getLongString();

                    AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                    dialog.setTitle(R.string.alert);
//                    dialog.setIcon(R.drawable.warning);
                    dialog.setCancelable(true);
                    dialog.setMessage(R.string.arrivalDialog);

                    dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (!(latitude == null)) {
//                                if (loginStrings[4].equals("N")) {
                                SynUpdateDeparture synUpdateDeparture = new SynUpdateDeparture(latitude, longitude, utilityClass1.getTimeString(), getActivity());
                                synUpdateDeparture.execute();
                                getActivity().getFragmentManager().popBackStack();
                            } else {
//                                    String distance = utilityClass.getDistanceMeter(storeLatString, storeLongString);
//                                    if (Double.parseDouble(distance) <= Double.parseDouble(storeRadiusString)) {
//                                        SynUpdateArrival syncUpdateArrival = new SynUpdateArrival(latitude, longitude, utilityClass.getTimeString(), getActivity());
//                                        syncUpdateArrival.execute();
//                                    } else {
                                Toast.makeText(getActivity(), getResources().getString(R.string.gps_err), Toast.LENGTH_LONG).show();
//                                    }
                            }
//                            }

                        }
                    });

                    dialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                }
                break;

                    case R.id.img_4:
                        if (!imgPack1ABoolean) {
                            File originalFile1 = new File(Environment.getExternalStorageDirectory() + "/DCIM/", indexFileNameStrings[0]);
                            Intent cameraIntent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            pack1Uri = Uri.fromFile(originalFile1);
                            cameraIntent1.putExtra(MediaStore.EXTRA_OUTPUT, pack1Uri);
                            startActivityForResult(cameraIntent1, 1);
                        }
                        break;
                    case R.id.img_5:
                        if (!imgPack2ABoolean) {
                            File originalFile1 = new File(Environment.getExternalStorageDirectory() + "/DCIM/", indexFileNameStrings[0]);
                            Intent cameraIntent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            pack2Uri = Uri.fromFile(originalFile1);
                            cameraIntent1.putExtra(MediaStore.EXTRA_OUTPUT, pack2Uri);
                            startActivityForResult(cameraIntent1, 1);
                        }
                        break;
                    case R.id.img_6:
                        if (!imgDoc1ABoolean) {
                            File originalFile1 = new File(Environment.getExternalStorageDirectory() + "/DCIM/", indexFileNameStrings[0]);
                            Intent cameraIntent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            doc1Uri = Uri.fromFile(originalFile1);
                            cameraIntent1.putExtra(MediaStore.EXTRA_OUTPUT, doc1Uri);
                            startActivityForResult(cameraIntent1, 1);
                        }
                        break;
                    case R.id.img_7:
                        if (!imgDoc2ABoolean) {
                            File originalFile1 = new File(Environment.getExternalStorageDirectory() + "/DCIM/", indexFileNameStrings[0]);
                            Intent cameraIntent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            doc2Uri = Uri.fromFile(originalFile1);
                            cameraIntent1.putExtra(MediaStore.EXTRA_OUTPUT, doc2Uri);
                            startActivityForResult(cameraIntent1, 1);
                        }
                        break;

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
}
