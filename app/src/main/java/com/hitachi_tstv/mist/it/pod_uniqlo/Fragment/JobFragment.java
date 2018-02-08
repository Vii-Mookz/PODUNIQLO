package com.hitachi_tstv.mist.it.pod_uniqlo.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hitachi_tstv.mist.it.pod_uniqlo.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class JobFragment extends Fragment {


    @BindView(R.id.txt_name)
    TextView txtName;
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
    String[] indexFileNameStrings, fileNameStrings, filePathStrings;
    Uri pack1Uri, pack2Uri, doc1Uri, doc2Uri;
    public JobFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_job, container, false);
        unbinder = ButterKnife.bind(this, view);
        setData() ;
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

    @OnClick({R.id.btn_savepic, R.id.btn_transfer, R.id.btn_arrival, R.id.btn_confirm,R.id.img_4, R.id.img_5, R.id.img_6, R.id.img_7})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_savepic:
                break;
            case R.id.btn_transfer:
                break;
            case R.id.btn_arrival:
                break;
            case R.id.btn_confirm:
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
}
