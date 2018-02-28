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
import com.hitachi_tstv.mist.it.pod_uniqlo.UploadImageUtils1;
import com.hitachi_tstv.mist.it.pod_uniqlo.UtilityClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    private String pathPack1RString, pathPack2RString, pathDoc1RString, pathDoc2RString;
    String[] indexFileNameStrings, fileNameStrings, filePathStrings, loginStrings,totalStrings;
    String storeCodeString, locationString, doNoString, storeTypeString, runningNoString, deliveryDateString;
    Uri pack1RUri, pack2RUri, doc1RUri, doc2RUri;
    Bitmap imgPackage1RBitmap, imgPackage2RBitmap, imgDoc1RBitmap, imgDoc2RBitmap;
    private String[]  storeCodeStrings,locationStrings, doNoStrings,storeTypeStrings,runningNoStrings,statusStrings, imgPathStrings,imgFileNameStrings,imagePlacementStrings;
    private String[]  storeCodeStrings1,locationStrings1, doNoStrings1,storeTypeStrings1,runningNoStrings1,statusStrings1;
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

        SyncJobDetailTransfer syncJobDetailTransfer = new SyncJobDetailTransfer(getContext());
        syncJobDetailTransfer.execute();

        return view;
    }

    //Set FileName FilePath
    private void setData() {
        indexFileNameStrings = new String[]{"PDT_1_PackageReturn1.jpg", "PDT_2_PackageReturn2.jpg", "DOC_1_DocumentReturn1.jpg", "DOC_2_DocumentReturn2.jpg"};

        fileNameStrings = new String[indexFileNameStrings.length];
        filePathStrings = new String[indexFileNameStrings.length];

        pathPack1RString = "";
        pathPack2RString = "";
        pathDoc1RString = "";
        pathDoc2RString = "";


        imgPack1RBoolean = false;
        imgPack2RBoolean = false;
        imgDoc1RBoolean = false;
        imgDoc2RBoolean = false;
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
                    pathPack1RString = pack1RUri.getPath();
                    try {
                        imgPackage1RBitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(pack1RUri));
                        if (imgPackage1RBitmap.getHeight() < imgPackage1RBitmap.getWidth()) {
                            imgPackage1RBitmap = rotateBitmap(imgPackage1RBitmap);
                        }
                        img4.setImageBitmap(imgPackage1RBitmap);
                        if (!Objects.equals(pathPack1RString, "")) {
                           SyncUploadPicture syncUploadPicture = new SyncUploadPicture(getActivity(), indexFileNameStrings[0], runningNoString, imgPackage1RBitmap);
                            syncUploadPicture.execute();
                            Toast.makeText(getContext(), R.string.save_pic_success, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), R.string.save_pic_unsuccess, Toast.LENGTH_LONG).show();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    pathPack2RString = pack2RUri.getPath();
                    try {
                        imgPackage2RBitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(pack2RUri));
                        if (imgPackage2RBitmap.getHeight() < imgPackage2RBitmap.getWidth()) {
                            imgPackage2RBitmap = rotateBitmap(imgPackage2RBitmap);
                        }
                        img5.setImageBitmap(imgPackage2RBitmap);
                        if (!Objects.equals(pathPack2RString, "")) {
                            SyncUploadPicture syncUploadPicture = new SyncUploadPicture(getActivity(), indexFileNameStrings[1], runningNoString, imgPackage2RBitmap);
                            syncUploadPicture.execute();
                            Toast.makeText(getContext(), R.string.save_pic_success, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), R.string.save_pic_unsuccess, Toast.LENGTH_LONG).show();
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 3:
                if (resultCode == RESULT_OK) {
                    pathDoc1RString = doc1RUri.getPath();
                    try {
                        imgDoc1RBitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(doc1RUri));
                        if (imgDoc1RBitmap.getHeight() < imgDoc1RBitmap.getWidth()) {
                            imgDoc1RBitmap = rotateBitmap(imgDoc1RBitmap);
                        }
                        img6.setImageBitmap(imgDoc1RBitmap);
                        if (!Objects.equals(pathDoc1RString, "")) {
                            SyncUploadPicture syncUploadPicture = new SyncUploadPicture(getActivity(), indexFileNameStrings[2], runningNoString, imgDoc1RBitmap);
                            syncUploadPicture.execute();
                            Toast.makeText(getContext(), R.string.save_pic_success, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), R.string.save_pic_unsuccess, Toast.LENGTH_LONG).show();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 4:
                if (resultCode == RESULT_OK) {
                    pathDoc2RString = doc2RUri.getPath();
                    try {
                        imgDoc2RBitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(doc2RUri));
                        if (imgDoc2RBitmap.getHeight() < imgDoc2RBitmap.getWidth()) {
                            imgDoc2RBitmap = rotateBitmap(imgDoc2RBitmap);
                        }
                        img7.setImageBitmap(imgDoc2RBitmap);
                        if (!Objects.equals(pathDoc2RString, "")) {
                            SyncUploadPicture syncUploadPicture = new SyncUploadPicture(getActivity(), indexFileNameStrings[3], runningNoString, imgDoc2RBitmap);
                            syncUploadPicture.execute();
                            Toast.makeText(getContext(), R.string.save_pic_success, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), R.string.save_pic_unsuccess, Toast.LENGTH_LONG).show();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    //Upload Photo
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
            UploadImageUtils1 uploadImageUtils1 = new UploadImageUtils1();
            final String result = uploadImageUtils1.uploadFile(mFileNameString, Constant.urlSaveImage, bitmap);
//            if (result.equals("NOK")) {
//                return "NOK";
//            } else {
            try {

                Log.d(name + "TAG:", "PIC: " + "Running_No ==> " + runningNoString + "," + loginStrings[4] + "," + loginStrings[3] + "," + mFileNameString + result);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("pTruckId", loginStrings[4]);
                    jsonObject.put("pRunNo", runningNoString);
                    jsonObject.put("pFileName", mFileNameString);
                    jsonObject.put("pDelType", "RT");
                    jsonObject.put("pImgFile", result);

                    if (mFileNameString.equals("PDT_1_PackageReturn1.jpg") || mFileNameString.equals("PDT_2_PackageReturn2.jpg")) {
                        jsonObject.put("pImgType", "PDT");
                    } else if (mFileNameString.equals("DOC_1_DocumentReturn1.jpg") || mFileNameString.equals("DOC_2_DocumentReturn2.jpg")){
                        jsonObject.put("pImgType", "DOC");
                    }
                    jsonObject.put("pUser", loginStrings[3]);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                OkHttpClient okHttpClient = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());
                Log.d(name + "TAG:", "Request Body:" + jsonObject.toString());
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(Constant.urlSaveImage).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (IOException e) {
                e.printStackTrace();
                return "NOK";
            }
//            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(reformat(s));
            Log.d(name + "TAG:", "onPostExecute:::-----> " + reformat(s));
            progressDialog.dismiss();
//
        }
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

    //Get JobDetail Transfer

    class SyncJobDetailTransfer extends AsyncTask<Void, Void, String> {
        Context context;


        public SyncJobDetailTransfer(Context context) {
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
                doNoStrings1 = new String[dataJsonArray.length()];
                locationStrings1 = new String[dataJsonArray.length()];
                storeCodeStrings1= new String[dataJsonArray.length()];
                runningNoStrings1 = new String[dataJsonArray.length()];
                statusStrings1 = new String[dataJsonArray.length()];
                imgPathStrings = new String[dataJsonArray.length()];
                imgFileNameStrings = new String[dataJsonArray.length()];
                imagePlacementStrings = new String[dataJsonArray.length()];


                for (int i = 0; i < dataJsonArray.length(); i++) {
                    JSONObject jsonObject1 = dataJsonArray.getJSONObject(i);
                    totalStrings[i] = jsonObject1.getString("Total");
                    doNoStrings1[i] = jsonObject1.getString("DO");
                    locationStrings1[i] = jsonObject1.getString("Location");
                    storeCodeStrings1[i] = jsonObject1.getString("StoreCode");
                    runningNoStrings1[i] = jsonObject1.getString("Running_No");
                    statusStrings1[i] = jsonObject1.getString("Status");
                    imgPathStrings[i] = jsonObject1.getString("ImgPath");
                    imgFileNameStrings[i] = jsonObject1.getString("ImgFileName");
                    imagePlacementStrings[i] = jsonObject1.getString("ImagePlacement");
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
                    File originalFile1 = new File(Environment.getExternalStorageDirectory() + "/DCIM/", indexFileNameStrings[1]);
                    Intent cameraIntent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    pack2RUri = Uri.fromFile(originalFile1);
                    cameraIntent1.putExtra(MediaStore.EXTRA_OUTPUT, pack2RUri);
                    startActivityForResult(cameraIntent1, 2);
                }
                break;
            case R.id.img_6:
                if (!imgDoc1RBoolean) {
                    File originalFile1 = new File(Environment.getExternalStorageDirectory() + "/DCIM/", indexFileNameStrings[2]);
                    Intent cameraIntent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    doc1RUri = Uri.fromFile(originalFile1);
                    cameraIntent1.putExtra(MediaStore.EXTRA_OUTPUT, doc1RUri);
                    startActivityForResult(cameraIntent1, 3);
                }
                break;
            case R.id.img_7:
                if (!imgDoc2RBoolean) {
                    File originalFile1 = new File(Environment.getExternalStorageDirectory() + "/DCIM/", indexFileNameStrings[3]);
                    Intent cameraIntent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    doc2RUri = Uri.fromFile(originalFile1);
                    cameraIntent1.putExtra(MediaStore.EXTRA_OUTPUT, doc2RUri);
                    startActivityForResult(cameraIntent1, 4);
                }
                break;
            case R.id.btn_save:


                AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                dialog.setTitle(R.string.alert);
                    dialog.setIcon(R.drawable.warning);
                dialog.setCancelable(true);
                dialog.setMessage(R.string.savedata);

                dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SyncUpdateTotalTransfer syncUpdateTotalTransfer = new SyncUpdateTotalTransfer(getActivity(), TotalEditText.getText().toString());
                        syncUpdateTotalTransfer.execute();
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
                        getFragmentManager().popBackStack();

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
