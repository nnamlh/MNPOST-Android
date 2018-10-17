package com.mnpost.app.updatetake;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mnpost.app.R;
import com.mnpost.app.data.source.remote.TakeMailerDetailInfo;
import com.mnpost.app.main.ScanBarcodeZXing;
import com.mnpost.app.util.DialogLoading;
import com.mnpost.app.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateTakeFragment extends Fragment implements UpdateTakeContract.View, UpdateTakeAdapter.AdapterListener{


    UpdateTakeContract.Presenter mPresenter;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    DialogLoading dLoading;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    List<TakeMailerDetailInfo> takeMailerDetailInfos;
    List<TakeMailerDetailInfo> takeMailerDetailInfoTemps;

    UpdateTakeAdapter mDapter;


    @BindView(R.id.ecount)
    TextView eCount;

    @BindView(R.id.ecustomer)
    TextView eCustomer;

    @BindView(R.id.btnscan)
    ImageView btnScan;

    @BindView(R.id.esearch)
    EditText eSearch;

    public static UpdateTakeFragment newInstance() {

        return  new UpdateTakeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_update_take, container, false);

        ButterKnife.bind(this, view);
        takeMailerDetailInfoTemps = new ArrayList<>();
        takeMailerDetailInfos = new ArrayList<>();
        mDapter = new UpdateTakeAdapter(takeMailerDetailInfos, getContext(), this);
        dLoading = new DialogLoading(getContext());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new UpdateTakeFragment.GridSpacingItemDecoration(1, dpToPx(5), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mDapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.subscribe();
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setCaptureActivity(ScanBarcodeZXing.class);
                integrator.setPrompt("MNPOST");
                integrator.setOrientationLocked(false);
                integrator.setCameraId(0);  // Use a specific camera of the device
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
            }
        });

        eSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchMailer(eSearch.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        eCustomer.setText(Utils.TakeMailerInfo.getCustomerName());
        return view;
    }
    @Override
    public void showLoading(boolean flag) {
        if(flag) {
            dLoading.show();
        } else {
            dLoading.dismiss();
        }
    }

    @Override
    public void updateTake(int position) {
        takeMailerDetailInfos.get(position).setCurrentStatusID(8);
        mDapter.notifyDataSetChanged();
    }

    @Override
    public Context getMContext() {
        return getContext();
    }

    @Override
    public void onBAck() {
        Utils.showDialog(getContext(), "Thông báo", "Hủy cập nhật hiện tại ?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();

                eSearch.setText(result.getContents());
                searchMailer(result.getContents());

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


    @Override
    public void refeshData(List<TakeMailerDetailInfo> datas) {
        takeMailerDetailInfos.clear();
        takeMailerDetailInfoTemps.clear();
        takeMailerDetailInfoTemps.addAll(datas);
        takeMailerDetailInfos.addAll(datas);
        mDapter.notifyDataSetChanged();
        eCount.setText("Tìm thấy " + takeMailerDetailInfos.size() + " vđ");
    }

    @Override
    public void setRefeshView(boolean isView) {
        swipeRefreshLayout.setRefreshing(isView);
    }

    @Override
    public void searchMailer(String code) {
        takeMailerDetailInfos.clear();
        for(TakeMailerDetailInfo info: takeMailerDetailInfoTemps) {
            if(info.getMailerID().contains(code)){
                takeMailerDetailInfos.add(info);
            }
        }

        mDapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(UpdateTakeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void onSelected(TakeMailerDetailInfo contact, int position) {
        mPresenter.updateTakeMailer(contact.getMailerID(), position);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
