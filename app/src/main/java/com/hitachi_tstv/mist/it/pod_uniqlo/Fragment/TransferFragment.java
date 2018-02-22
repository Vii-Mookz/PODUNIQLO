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
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hitachi_tstv.mist.it.pod_uniqlo.Bean.GetJobDetailTransfer;
import com.hitachi_tstv.mist.it.pod_uniqlo.Constant;
import com.hitachi_tstv.mist.it.pod_uniqlo.R;
import com.hitachi_tstv.mist.it.pod_uniqlo.UtilityClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransferFragment extends Fragment {


    @BindView(R.id.txtDodt)
    TextView txtDodt;
    @BindView(R.id.txtsourcedtl)
    TextView txtsourcedtl;
    @BindView(R.id.txtlodtl)
    TextView txtlodtl;
    @BindView(R.id.editText)
    EditText TotalEditText;
    @BindView(R.id.img_4)
    ImageView img4;
    @BindView(R.id.img_5)
    ImageView img5;
    @BindView(R.id.img_6)
    ImageView img6;
    @BindView(R.id.img_7)
    ImageView img7;
    @BindView(R.id.btn_save)
    Button btnSave;
    Unbinder unbinder;
    Boolean imgPack1RBoolean, imgPack2RBoolean, imgDoc1RBoolean, imgDoc2RBoolean;
    private String pathPack1String, pathPack2String, pathDoc1String, pathDoc2String;
    String[] indexFileNameStrings, fileNameStrings, filePathStrings, loginStrings,totalStrings;
    String storeCodeString, locationString, doNoString, storeTypeString, runningNoString, deliveryDateString;
    Uri pack1RUri, pack2RUri, doc1RUri, doc2RUri;
    String name = "TransferFragment ";

    public TransferFragment() {
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


        View view = inflater.inflate(R.layout.fragment_transfer, container, false);
        unbinder = ButterKnife.bind(this, view);
        setData();
        setPic();
        txtDodt.setText(doNoString);
        txtlodtl.setText(locationString);
        txtsourcedtl.setText(storeCodeString);
        Log.d(name + "TAG:", "Date Trasfer: " + deliveryDateString);

        SyncTotal syncTotal = new SyncTotal(getContext());
        syncTotal.execute();

        return view;
    }
//Set hide photo type Roadside
    private void setPic() {
        if (!storeTypeString.equals("Roadside")) {
            img4.setVisibility(View.INVISIBLE);
            img5.setVisibility(View.INVISIBLE);
            img6.setVisibility(View.INVISIBLE);
            img7.setVisibility(View.INVISIBLE);
        }
    }
//Set FileName FilePath
    private void setData() {
        indexFileNameStrings = new String[]{"PackageReturn1.png", "PackageReturn2.png", "DocumentReturn1.png", "DocumentReturn2.png"};

        fileNameStrings = new String[indexFileNameStrings.length];
        filePathStrings = new String[indexFileNameStrings.length];

        pathPack1String = "";
        pathPack2String = "";
        pathDoc1String = "";
        pathDoc2String = "";


        imgPack1RBoolean = false;
        imgPack2RBoolean = false;
        imgDoc1RBoolean = false;
        imgDoc2RBoolean = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //insert or update Total Return
    class SyncUpdateTotalTransfer extends AsyncTask<Void, Void, String> {
        Context context;
        String qtyString;
        UtilityClass utilityClass;

        public SyncUpdateTotalTransfer(Context context, String qtyString) {
            this.context = context;
            this.qtyString = qtyString;
            utilityClass = new UtilityClass(context);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... voids) {
            try {

                Log.d(name + "TAG:", "Trasfer: " + "Running_No ==> " + runningNoString + "," + loginStrings[4] + "," + loginStrings[3] +"," + qtyString);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("pDO", doNoString);
                    jsonObject.put("pDate", deliveryDateString);
                    jsonObject.put("pQTY", qtyString);
                    jsonObject.put("pRunNo", runningNoString);
                    jsonObject.put("pTruckId", loginStrings[4]);
                    jsonObject.put("pUser", loginStrings[3]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                OkHttpClient okHttpClient = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());
                Log.d(name + "TAG:", "Request Body:" + jsonObject.toString());
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(Constant.urlSaveTotalTransfer).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();


                return response.body().string();

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(reformat(s));
            Log.d(name + "TAG:", "onPostExecute:::-----> " + reformat(s));

        }
    }

    //Get Total return

    class SyncTotal extends AsyncTask<Void, Void, String> {
        Context context;


        public SyncTotal(Context context) {
            this.context = context;

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... params) {
            try {

                // 1. connect server with okHttp
                OkHttpClient client = new OkHttpClient();

                // 2. assign get data

                Request request = new Request.Builder().url(Constant.urlGetJobDetailTransfer + loginStrings[4]+"/" + runningNoString).build();
//                Request request = new Request.Builder().url(Constant.urlGetJobList +truckIDString +"/"+deliveryDateString).build();

                // 3. transport request to server
                Response response = client.newCall(request).execute();

                String result = response.body().string();
                String refomat1 = reformat(result);
                Log.d("TAG:"+ name, "Request:" + request);

                Log.d("TAG:"+ name, "GetJobDetailTransfer" + refomat1);

                // parse json string with gson
                Gson gson = new Gson();

                GetJobDetailTransfer getJobDetailTransfer = gson.fromJson(refomat1, GetJobDetailTransfer.class);
                String getTotal =  getJobDetailTransfer.getData().get(0).getTotal();
                Log.d("TAG:"+ name, "Getdata" + String.valueOf(getJobDetailTransfer.getData().size()));
                Log.d("TAG:"+ name, "Total" + getJobDetailTransfer.getData().get(0).getTotal());

                return refomat1;

            } catch (Exception e) {
                Log.d("TAG:"+ name, "Error1: " + e.getMessage().toString());
//                Log.d("TAG:","UNIQLO-Tag-Main", "e ==> " + e + " Line " + e.getStackTrace()[0].getLineNumber());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("TAG:" + name, "S ==> " + s);

            try {
                JSONObject jsonObject = new JSONObject(s);

                JSONArray dataJsonArray = jsonObject.getJSONArray("data");
                totalStrings = new String[dataJsonArray.length()];


                for (int i = 0; i < dataJsonArray.length(); i++) {
                    JSONObject jsonObject1 = dataJsonArray.getJSONObject(i);
                    totalStrings[i] = jsonObject1.getString("Total");
                }


            } catch (JSONException e) {
                Log.d(name + "TAG:", "JSONArray ==> " + e + " Line " + e.getStackTrace()[0].getLineNumber());

            }
            TotalEditText.setText(totalStrings[0]);
        }
    }

    //OnClick
    @OnClick({R.id.img_4, R.id.img_5, R.id.img_6, R.id.img_7, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_4:
                if (!imgPack1RBoolean) {
                    File originalFile1 = new File(Environment.getExternalStorageDirectory() + "/DCIM/", indexFileNameStrings[0]);
                    Intent cameraIntent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    pack1RUri = Uri.fromFile(originalFile1);
                    cameraIntent1.putExtra(MediaStore.EXTRA_OUTPUT, pack1RUri);
                    startActivityForResult(cameraIntent1, 1);
                }
                break;
            case R.id.img_5:
                if (!imgPack2RBoolean) {
                    File originalFile1 = new File(Environment.getExternalStorageDirectory() + "/DCIM/", indexFileNameStrings[0]);
                    Intent cameraIntent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    pack2RUri = Uri.fromFile(originalFile1);
                    cameraIntent1.putExtra(MediaStore.EXTRA_OUTPUT, pack2RUri);
                    startActivityForResult(cameraIntent1, 2);
                }
                break;
            case R.id.img_6:
                if (!imgDoc1RBoolean) {
                    File originalFile1 = new File(Environment.getExternalStorageDirectory() + "/DCIM/", indexFileNameStrings[0]);
                    Intent cameraIntent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    doc1RUri = Uri.fromFile(originalFile1);
                    cameraIntent1.putExtra(MediaStore.EXTRA_OUTPUT, doc1RUri);
                    startActivityForResult(cameraIntent1, 3);
                }
                break;
            case R.id.img_7:
                if (!imgDoc2RBoolean) {
                    File originalFile1 = new File(Environment.getExternalStorageDirectory() + "/DCIM/", indexFileNameStrings[0]);
                    Intent cameraIntent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    doc2RUri = Uri.fromFile(originalFile1);
                    cameraIntent1.putExtra(MediaStore.EXTRA_OUTPUT, doc2RUri);
                    startActivityForResult(cameraIntent1, 4);
                }
                break;
            case R.id.btn_save:


                AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                dialog.setTitle(R.string.alert);
//                    dialog.setIcon(R.drawable.warning);
                dialog.setCancelable(true);
                dialog.setMessage(R.string.savedata);

                dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SyncUpdateTotalTransfer syncUpdateTotalTransfer = new SyncUpdateTotalTransfer(getActivity(), TotalEditText.getText().toString());
                        syncUpdateTotalTransfer.execute();
//                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                        JobFragment jobFragment1 = new JobFragment();
//                        fragmentTransaction.replace(R.id.contentFragment, jobFragment1,"Job").addToBackStack(null);
//                        fragmentTransaction.addToBackStack(null);
                        getActivity().getFragmentManager().popBackStack();
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();


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
    }

    //reformat json
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
