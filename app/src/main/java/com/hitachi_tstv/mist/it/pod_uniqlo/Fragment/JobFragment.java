package com.hitachi_tstv.mist.it.pod_uniqlo.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hitachi_tstv.mist.it.pod_uniqlo.R;

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

    public JobFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_job, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_savepic, R.id.btn_transfer, R.id.btn_arrival, R.id.btn_confirm})
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
        }
    }
}
