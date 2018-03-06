package com.hitachi_tstv.mist.it.pod_uniqlo.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.hitachi_tstv.mist.it.pod_uniqlo.Bean.GetJobDetail;
import com.hitachi_tstv.mist.it.pod_uniqlo.Constant;
import com.hitachi_tstv.mist.it.pod_uniqlo.R;
import com.hitachi_tstv.mist.it.pod_uniqlo.UploadImageUtils1;
import com.hitachi_tstv.mist.it.pod_uniqlo.UtilityClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;


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
    Boolean imgPack1ABoolean, imgPack2ABoolean, imgDoc1ABoolean, imgDoc2ABoolean;
    private String pathPack1String, pathPack2String, pathDoc1String, pathDoc2String;
    String storeCodeString, locationString, doNoString, storeTypeString, runningNoString, deliveryDateString;
    String[] indexFileNameStrings, fileNameStrings, filePathStrings, loginStrings, imgPathStrings, imgFileNameSrings, imagePlacementStrings, arrivalTimeStrings, departTimeStrings, imgaeFileStrings;
    Bitmap imgPackage1Bitmap, imgPackage2Bitmap, imgDoc1Bitmap, imgDoc2Bitmap;
    Uri pack1Uri, pack2Uri, doc1Uri, doc2Uri;
    String name = "JobFragment ";
    Boolean doubleBackPressABoolean = false;

    public JobFragment() {
        // Required empty public constructor
    }

    //Menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.refresh:
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();
                break;
        }

        return super.onOptionsItemSelected(item);
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
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_job, container, false);
        unbinder = ButterKnife.bind(this, view);
        setData();
        setPic();

        txtDodt.setText(doNoString);
        txtlodtl.setText(locationString);
        txtsourcedtl.setText(storeCodeString);
        Log.d(name + "TAG:", "StoreType:" + storeTypeString);

        SynGetJobDetail synGetJobDetail = new SynGetJobDetail(getContext());
        synGetJobDetail.execute();
        return view;

    }

    //set Filename and path
    private void setData() {


        indexFileNameStrings = new String[]{"PDT_1_Package1.jpg", "PDT_2_Package2.jpg", "DOC_1_Document1.jpg", "DOC_2_Document2.jpg"};

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

    //Set hide icon take Photo
    private void setPic() {
        if (!storeTypeString.equals("Roadside")) {
            img4.setVisibility(View.INVISIBLE);
            img5.setVisibility(View.INVISIBLE);
            img6.setVisibility(View.INVISIBLE);
            img7.setVisibility(View.INVISIBLE);
        }
    }

    private Bitmap rotateBitmap(Bitmap src) {

        // create new matrix
        Matrix matrix = new Matrix();
        // setup rotation degree
        matrix.postRotate(90);
        Bitmap bmp = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        return bmp;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    pathPack1String = pack1Uri.getPath();
                    try {
                        imgPackage1Bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(pack1Uri));
                        if (imgPackage1Bitmap.getHeight() < imgPackage1Bitmap.getWidth()) {
                            imgPackage1Bitmap = rotateBitmap(imgPackage1Bitmap);
                        }
                        img4.setImageBitmap(imgPackage1Bitmap);

                        SyncUploadPicture syncUploadPicture = new SyncUploadPicture(getActivity(), indexFileNameStrings[0], runningNoString, imgPackage1Bitmap);
                        syncUploadPicture.execute();
//
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    pathPack2String = pack2Uri.getPath();
                    try {
                        imgPackage2Bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(pack2Uri));
                        if (imgPackage2Bitmap.getHeight() < imgPackage2Bitmap.getWidth()) {
                            imgPackage2Bitmap = rotateBitmap(imgPackage2Bitmap);
                        }
                        img5.setImageBitmap(imgPackage2Bitmap);
                        SyncUploadPicture syncUploadPicture = new SyncUploadPicture(getActivity(), indexFileNameStrings[1], runningNoString, imgPackage2Bitmap);
                        syncUploadPicture.execute();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 3:
                if (resultCode == RESULT_OK) {
                    pathDoc1String = doc1Uri.getPath();
                    try {
                        imgDoc1Bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(doc1Uri));
                        if (imgDoc1Bitmap.getHeight() < imgDoc1Bitmap.getWidth()) {
                            imgDoc1Bitmap = rotateBitmap(imgDoc1Bitmap);
                        }
                        img6.setImageBitmap(imgDoc1Bitmap);
                        if (!Objects.equals(pathDoc1String, "")) {
                            SyncUploadPicture syncUploadPicture = new SyncUploadPicture(getActivity(), indexFileNameStrings[2], runningNoString, imgDoc1Bitmap);
                            syncUploadPicture.execute();
                        } else {
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 4:
                if (resultCode == RESULT_OK) {
                    pathDoc2String = doc2Uri.getPath();
                    try {
                        imgDoc2Bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(doc2Uri));
                        if (imgDoc2Bitmap.getHeight() < imgDoc2Bitmap.getWidth()) {
                            imgDoc2Bitmap = rotateBitmap(imgDoc2Bitmap);
                        }
                        img7.setImageBitmap(imgDoc2Bitmap);

                        SyncUploadPicture syncUploadPicture = new SyncUploadPicture(getActivity(), indexFileNameStrings[3], runningNoString, imgDoc2Bitmap);
                        syncUploadPicture.execute();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
// Pic

    private void writeToSDFile(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (bitmap.getWidth() > bitmap.getHeight()) {
            bitmap = Bitmap.createScaledBitmap(bitmap, 1600, 1200, false);
        } else {
            bitmap = Bitmap.createScaledBitmap(bitmap, 1200, 1600, false);

        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, baos);
        byte[] data = baos.toByteArray();

        data[13] = 00000001;
        data[14] = 00000001;
        data[15] = (byte) 244;
        data[16] = 00000001;

        File target = Environment.getExternalStorageDirectory();
        File file = new File(Environment.getExternalStorageDirectory(), "/DCIMA/");
        if (!file.exists()) {
            file.mkdir();
        }
        Log.d("TAG:", "writeToSDFile: " + file.getAbsolutePath().toString());

        try {
            File gpxfile = new File(file, "myData.txt");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(Base64.encodeToString(data, Base64.DEFAULT));
            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    //Send Photo
    class SyncUploadPicture extends AsyncTask<Void, Void, String> {
        Context context;
        String mFileNameString, runningNoString;
        Bitmap bitmap;
        ProgressDialog progressDialog;

        public SyncUploadPicture(Context context, String mFileNameString, String runningNoString, Bitmap bitmap) {
            this.context = context;
            this.mFileNameString = mFileNameString;
            this.runningNoString = runningNoString;
            this.bitmap = bitmap;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {

            Log.d(name + "TAG:", "PIC: " + " Running_No ==> " + runningNoString + "," + mFileNameString + "," + loginStrings[3]);

            writeToSDFile(bitmap);
            UploadImageUtils1 uploadImageUtils1 = new UploadImageUtils1();
            String result = uploadImageUtils1.uploadFile(mFileNameString, Constant.urlUploadImage, bitmap, loginStrings[4]);
            Log.d("TAG:", "Result1111 " + result);
            if (result.equals("NOK")) {
                return "NOK";
            } else {
                try {

                    Log.d(name + "TAG:", "PIC: " + "Running_No ==> " + runningNoString + "," + loginStrings[4] + "," + loginStrings[3] + "," + mFileNameString + "," + result);
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("pTruckId", loginStrings[4]);
                        jsonObject.put("pRunNo", runningNoString);
                        jsonObject.put("pFileName", mFileNameString);
                        jsonObject.put("pDelType", "FT");

                        if (mFileNameString.equals("PDT_1_Package1.jpg") || mFileNameString.equals("PDT_2_Package2.jpg")) {
                            jsonObject.put("pImgType", "PDT");
                        } else if (mFileNameString.equals("DOC_1_Document1.jpg") || mFileNameString.equals("DOC_2_Document2.jpg")) {
                            jsonObject.put("pImgType", "DOC");
                        }
                        jsonObject.put("pUser", loginStrings[3]);
                        Log.d(name + "TAG:", "PIC Result: " + result);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    OkHttpClient okHttpClient = new OkHttpClient();
                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());
                    Log.d(name + "TAG:", "Request Body PIC:" + jsonObject.toString());
                    Request.Builder builder = new Request.Builder();
                    Request request = builder.url(Constant.urlSaveImage).post(requestBody).build();
                    Response response = okHttpClient.newCall(request).execute();
                    return response.body().string();

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("TAG:", "EXXXXXX" + e + "Line: " + e.getStackTrace()[0].getLineNumber());
                    return "NOK";
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(reformat(s));
            Log.d(name + "TAG:", "onPostExecute1:::-----> " + reformat(s));
            progressDialog.dismiss();

            try {
                JSONArray jsonArray = new JSONArray(reformat(s));

                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String confirm = jsonObject.getString("RESULT");
                if (confirm.equals("OK")) {
                    Toast.makeText(context, getResources().getText(R.string.save_pic_success), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, getResources().getText(R.string.save_pic_unsuccess), Toast.LENGTH_SHORT).show();
                }


                Log.d(name + "TAG:", "check" + confirm);
            } catch (JSONException e) {
                e.printStackTrace();

                Log.d(name + "TAG:", "JSONArray ==> " + e + " Line " + e.getStackTrace()[0].getLineNumber());

            }
//
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
//
            try {
                String arrival = "Arrival";
                Log.d(name + "TAG:", "Arrival: " + "Lat/Long : Running_No ==> " + runningNoString + "," + loginStrings[4] + "," + loginStrings[3] + "," + latString + "," + longString + "," + arrival);

//                String deviceId = utilityClass.getDeviceID();
//                String serial = utilityClass.getSerial();
//                String deviceName = utilityClass.getDeviceName();

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("pCheckType", arrival);
                    jsonObject.put("pLatitude", latString);
                    jsonObject.put("pLongitude", longString);
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
                Request request = builder.url(Constant.urlSaveTimeStamp).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();


                return response.body().string();

            } catch (Exception e) {
                Log.d(name + "TAG:", "e doInBack ==>" + e.toString() + "line::" + e.getStackTrace()[0].getLineNumber());
                return "";
            }
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(reformat(s));
            Log.d(name + "TAG:", "onPostExecute:::-----> " + reformat(s));

        }
    }

    //Departure

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
                Log.d(name + "TAG:", "Departue: " + "Lat/Long : Running_No ==> " + runningNoString + "," + loginStrings[4] + "," + loginStrings[3] + "," + latString + "," + longString + "," + departure);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("pCheckType", departure);
                    jsonObject.put("pLatitude", latString);
                    jsonObject.put("pLongitude", longString);
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
                Request request = builder.url(Constant.urlSaveTimeStamp).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();


                return response.body().string();
//                String deviceId = utilityClass.getDeviceID();
//                String serial = utilityClass.getSerial();
//                String deviceName = utilityClass.getDeviceName();

//                OkHttpClient okHttpClient = new OkHttpClient();
//                RequestBody requestBody = new FormBody.Builder()
////                        .add("isAdd", "true")
//                        .add("pTruckId", loginStrings[2])
//                        .add("pRunNo", runningNoString)
//                        .add("pLatitude", latString)
//                        .add("pLongitude", longString)
//                        .add("pCheckType", departure)
//                        .add("pUser", loginStrings[3])
//                        .build();
//
//                Request.Builder builder = new Request.Builder();
//
//                Request request = builder.url(Constant.urlSaveTimeStamp).post(requestBody).build();
//                Response response = okHttpClient.newCall(request).execute();
//                return response.body().string();

            } catch (Exception e) {
                Log.d(name + "TAG:", "e doInBack ==>" + e.toString() + "line::" + e.getStackTrace()[0].getLineNumber());
                return "";
            }
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(reformat(s));
            Log.d(name + "TAG:", "onPostExecute:::-----> " + reformat(s));

        }
    }

    //GetJobDetail

    class SynGetJobDetail extends AsyncTask<Void, Void, String> {
        Context context;


        public SynGetJobDetail(Context context) {
            this.context = context;

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... params) {
            try {

                // 1. connect server with okHttp
                OkHttpClient client = new OkHttpClient();

                // 2. assign get data

                Request request = new Request.Builder().url(Constant.urlGetJobDetail + loginStrings[4] + "/" + runningNoString).build();
//                Request request = new Request.Builder().url(Constant.urlGetJobList +truckIDString +"/"+deliveryDateString).build();

                // 3. transport request to server
                Response response = client.newCall(request).execute();

                String result = response.body().string();
                String refomat1 = reformat(result);
                Log.d("TAG:" + name, "Request:" + request);

                Log.d("TAG:" + name, "GetJobDetail" + refomat1);

                // parse json string with gson
                Gson gson = new Gson();

                GetJobDetail getJobDetail = gson.fromJson(refomat1, GetJobDetail.class);
                String getTotal = getJobDetail.getData().get(0).getImgFileName();
                Log.d("TAG:" + name, "Getdata" + String.valueOf(getJobDetail.getData().size()));
                Log.d("TAG:" + name, "getImgFileName" + getJobDetail.getData().get(0).getImgFileName());

                return refomat1;

            } catch (Exception e) {
                Log.d("TAG:" + name, "Error1: " + e.getMessage().toString());
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
                imgPathStrings = new String[4];
                imgFileNameSrings = new String[4];
                imagePlacementStrings = new String[dataJsonArray.length()];
                arrivalTimeStrings = new String[dataJsonArray.length()];
                departTimeStrings = new String[dataJsonArray.length()];


                for (int i = 0; i < dataJsonArray.length(); i++) {
                    JSONObject jsonObject1 = dataJsonArray.getJSONObject(i);
//                    imgPathStrings[i] = jsonObject1.getString("ImgPath");
                    imgFileNameSrings[i] = jsonObject1.getString("ImgFileName");
                    imagePlacementStrings[i] = jsonObject1.getString("ImagePlacement");
                    arrivalTimeStrings[i] = jsonObject1.getString("ArrivalDT");
                    departTimeStrings[i] = jsonObject1.getString("DepartureDT");
                    if (!jsonObject1.getString("ArrivalDT").equals("")) {
                        btnArrival.setEnabled(false);
                        btnConfirm.setEnabled(true);
                    } else {
                        btnArrival.setEnabled(true);
                        btnConfirm.setEnabled(false);

                    }
                    if (imgFileNameSrings[i].equals("PDT_1_Package1.jpg")) {
                        imgPathStrings[i] = jsonObject1.getString("ImgPath");
                    } else if (imgFileNameSrings[i].equals("PDT_2_Package2.jpg")) {
                        imgPathStrings[i] = jsonObject1.getString("ImgPath");
                    } else if (imgFileNameSrings[i].equals("DOC_1_Document1.jpg")) {
                        imgPathStrings[i] = jsonObject1.getString("ImgPath");
                    } else if (imgFileNameSrings[i].equals("DOC_2_Document2.jpg")) {
                        imgPathStrings[i] = jsonObject1.getString("ImgPath");
                    }
//                    if (imgFileNameSrings[0].equals("PDT_1_Package1.jpg")) {
//                        imgPathStrings[0] = jsonObject1.getString("ImgPath");
//                    } else if (imgFileNameSrings[1].equals("PDT_2_Package2.jpg")) {
//                        imgPathStrings[1] = jsonObject1.getString("ImgPath");
//                    } else if (imgFileNameSrings[2].equals("DOC_1_Document1.jpg")) {
//                        imgPathStrings[2] = jsonObject1.getString("ImgPath");
//                    } else if (imgFileNameSrings[3].equals("DOC_2_Document2.jpg")) {
//                        imgPathStrings[3] = jsonObject1.getString("ImgPath");
//                    }
//


                }


//                for (int i = 0; i < imgPathStrings.length; i++) {
//                    if (!(imgPathStrings[i] == null)) {
//                        switch (i) {
//                            case 0:
//                                Glide.with(context).load(imgPathStrings[i] + imgFileNameSrings[i]).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(img4);
//                                break;
//                            case 1:
//                                Glide.with(context).load(imgPathStrings[i] + imgFileNameSrings[i]).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(img5);
//                                break;
//                            case 2:
//                                Glide.with(context).load(imgPathStrings[i] + imgFileNameSrings[i]).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(img6);
//                                break;
//                            case 3:
//                                Glide.with(context).load(imgPathStrings[i] + imgFileNameSrings[i]).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(img7);
//                                break;
//
//                        }
//                    }
//                    Log.d("TAG:", "ImgpathandFilename: ==>   " + imgPathStrings[i] + imgFileNameSrings[i]);
//                }
            } catch (JSONException e) {
                Log.d(name + "TAG:", "JSONArray ==> " + e + " Line " + e.getStackTrace()[0].getLineNumber());

            }

            if (imgFileNameSrings != null) {
                Log.d("TAG:" + name, "imgFileNameSrings: " + Arrays.toString(imgFileNameSrings));
            }
            if (!(imgFileNameSrings[0] == null) && (!(imgPathStrings[0] == null))) {
                //check image
                checkImage(0);
            } else if (!(imgFileNameSrings[1] == null) && (!(imgPathStrings[1] == null))) {

                checkImage(1);
            } else if (!(imgFileNameSrings[2] == null) && (!(imgPathStrings[2] == null))) {

                checkImage(2);
            } else if (!(imgFileNameSrings[3] == null) && (!(imgPathStrings[3] == null))) {

                checkImage(3);
            }
            Log.d("TAG:" + name, "imgFileNameSrings11: " + "0" + imgPathStrings[0] + imgFileNameSrings[0]);
            Log.d("TAG:" + name, "imgFileNameSrings11: " + "1" + imgPathStrings[1] + imgFileNameSrings[1]);
            Log.d("TAG:" + name, "imgFileNameSrings11: " + "2" + imgPathStrings[2] + imgFileNameSrings[2]);
            Log.d("TAG:" + name, "imgFileNameSrings11: " + "3" + imgPathStrings[3] + imgFileNameSrings[3]);
        }

        private void checkImage(int i) {
            for (i = 0; i < imgFileNameSrings.length; i++) {
                if (!(imgFileNameSrings[i] == null)) {
                    if (imgFileNameSrings[i].equals("PDT_1_Package1.jpg")) {
                        Log.d("TAG:" + name, "imgFileNameSrings: " + imgFileNameSrings[i] + "imgPathStrings: " + imgPathStrings[i]);
                        Glide.with(context).load(imgPathStrings[i] + imgFileNameSrings[i]).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(img4);
                    } else if (imgFileNameSrings[i].equals("PDT_2_Package2.jpg") && (!(imgPathStrings[i] == null))) {
                        Log.d("TAG:" + name, "imgFileNameSrings: " + imgFileNameSrings[i] + "imgPathStrings: " + imgPathStrings[i]);
                        Glide.with(context).load(imgPathStrings[i] + imgFileNameSrings[i]).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(img5);
                    } else if (imgFileNameSrings[i].equals("DOC_1_Document1.jpg") && (!(imgPathStrings[i] == null))) {
                        Log.d("TAG:" + name, "imgFileNameSrings: " + imgFileNameSrings[i] + "imgPathStrings: " + imgPathStrings[i]);
                        Glide.with(context).load(imgPathStrings[i] + imgFileNameSrings[i]).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(img6);
                    } else if (imgFileNameSrings[i].equals("DOC_2_Document2.jpg") && (!(imgPathStrings[i] == null))) {
                        Log.d("TAG:" + name, "imgFileNameSrings: " + imgFileNameSrings[i] + "imgPathStrings: " + imgPathStrings[i]);
                        Glide.with(context).load(imgPathStrings[i] + imgFileNameSrings[i]).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(img7);
                    }
                    Log.d(name + "TAG:", "checkImage: " + imgPathStrings[i] + imgFileNameSrings[i]);
                }
            }
        }

    }

    @OnClick({R.id.btn_savepic, R.id.btn_transfer, R.id.btn_arrival, R.id.btn_confirm, R.id.img_4, R.id.img_5, R.id.img_6, R.id.img_7})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.btn_transfer:
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                TransferFragment transferFragment = new TransferFragment();
                final Bundle bundle = new Bundle();

                bundle.putStringArray("Login", loginStrings);
                bundle.putString("DO", doNoString);
                bundle.putString("StoreCode", storeCodeString);
                bundle.putString("Location", locationString);
                bundle.putString("StoreType", storeTypeString);
                bundle.putString("Running_No", runningNoString);
                bundle.putString("Date", deliveryDateString);

                transferFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.contentFragment, transferFragment, "Transfer").addToBackStack(null);
                fragmentTransaction.commit();
                break;
            //arrival
            case R.id.btn_arrival:
                final UtilityClass utilityClass = new UtilityClass(getActivity());


                if (utilityClass.setLatLong(0)) {
                    final String latitude = utilityClass.getLatString();
                    final String longitude = utilityClass.getLongString();

                    AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext(), R.style.ConfirmAlertDialogTheme);
                    dialog.setTitle(R.string.alert);
                    dialog.setIcon(R.drawable.warning);
                    dialog.setCancelable(true);
                    dialog.setMessage(R.string.arrivalDialog);


                    dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            JobFragment jobFragment = new JobFragment();
                            if (!(latitude == null)) {
                                SynUpdateArrival syncUpdateArrival = new SynUpdateArrival(latitude, longitude, utilityClass.getTimeString(), getActivity());
                                syncUpdateArrival.execute();

                                Toast.makeText(getActivity(), getResources().getString(R.string.arrival), Toast.LENGTH_LONG).show();
                                //reload
                                Fragment frg = null;
                                frg = getFragmentManager().findFragmentByTag("Job");
                                final android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.detach(frg);
                                ft.attach(frg);
                                ft.commit();


                            } else {
                                Toast.makeText(getActivity(), getResources().getString(R.string.err_gps1), Toast.LENGTH_LONG).show();
                            }

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

                    AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext(), R.style.ConfirmAlertDialogTheme);
                    dialog.setTitle(R.string.alert);
                    dialog.setIcon(R.drawable.warning);
                    dialog.setCancelable(true);
                    dialog.setMessage(R.string.departureDialog);

                    dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (!(longitude == null)) {

                                SynUpdateDeparture synUpdateDeparture = new SynUpdateDeparture(latitude, longitude, utilityClass1.getTimeString(), getActivity());
                                synUpdateDeparture.execute();
                                Toast.makeText(getActivity(), getResources().getString(R.string.departure), Toast.LENGTH_LONG).show();
                                getFragmentManager().popBackStack();
                            } else {
                                Toast.makeText(getActivity(), getResources().getString(R.string.err_gps1), Toast.LENGTH_LONG).show();
                            }
//                            }
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
                    File originalFile1 = new File(Environment.getExternalStorageDirectory() + "/DCIM/", indexFileNameStrings[1]);
                    Intent cameraIntent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    pack2Uri = Uri.fromFile(originalFile1);
                    cameraIntent1.putExtra(MediaStore.EXTRA_OUTPUT, pack2Uri);
                    startActivityForResult(cameraIntent1, 2);
                }
                break;
            case R.id.img_6:
                if (!imgDoc1ABoolean) {
                    File originalFile1 = new File(Environment.getExternalStorageDirectory() + "/DCIM/", indexFileNameStrings[2]);
                    Intent cameraIntent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    doc1Uri = Uri.fromFile(originalFile1);
                    cameraIntent1.putExtra(MediaStore.EXTRA_OUTPUT, doc1Uri);
                    startActivityForResult(cameraIntent1, 3);
                }
                break;
            case R.id.img_7:
                if (!imgDoc2ABoolean) {


                    File originalFile1 = new File(Environment.getExternalStorageDirectory() + "/DCIM/", indexFileNameStrings[3]);
                    Intent cameraIntent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    doc2Uri = Uri.fromFile(originalFile1);
                    cameraIntent1.putExtra(MediaStore.EXTRA_OUTPUT, doc2Uri);
                    startActivityForResult(cameraIntent1, 4);
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
