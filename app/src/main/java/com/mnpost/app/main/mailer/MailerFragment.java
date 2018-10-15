package com.mnpost.app.main.mailer;

import android.content.Context;
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
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mnpost.app.R;
import com.mnpost.app.data.source.remote.MailerDeliveryInfo;
import com.mnpost.app.deliveryhistory.DeliveryHistoryActivity;
import com.mnpost.app.main.ScanBarcodeZXing;
import com.mnpost.app.updatedelivery.UpdateDeliveryActivity;
import com.mnpost.app.util.DialogLoading;
import com.mnpost.app.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MailerFragment extends Fragment implements MailerContract.View, MailerDeliveryAdapter.MailerDeliveryAdapterListener {

    MailerContract.Presenter mPresenter;

    DialogLoading dLoading;

    @BindView(R.id.btnscan)
    ImageView btnScan;

    @BindView(R.id.btndelivery)
    Button btnDelivery;

    @BindView(R.id.btntake)
    Button btnTake;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.btnhistory)
    ImageView btnHistory;

    MailerDeliveryAdapter mAdapter;

    List<MailerDeliveryInfo> mailerDeliveryInfos;

    public static MailerFragment newInstance() {
        return new MailerFragment();
    }


    @Override
    public void setPresenter(MailerContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mailer, container, false);
        dLoading = new DialogLoading(getContext());

        ButterKnife.bind(this, view);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScanBarcode();
            }
        });

        mailerDeliveryInfos = new ArrayList<>();

        mAdapter = new MailerDeliveryAdapter(getContext(), mailerDeliveryInfos, this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new MailerFragment.GridSpacingItemDecoration(1, dpToPx(5), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.subscribe();
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DeliveryHistoryActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
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
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public Context getMContext() {
        return getContext();
    }

    @Override
    public void openScanBarcode() {
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setCaptureActivity(ScanBarcodeZXing.class);
        integrator.setPrompt("MNPOST");
        integrator.setOrientationLocked(false);
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    @Override
    public void refeshData(List<MailerDeliveryInfo> mailerDeliveryInfoList) {
        mailerDeliveryInfos.clear();
        mailerDeliveryInfos.addAll(mailerDeliveryInfoList);
        mAdapter.notifyDataSetChanged();

        btnDelivery.setText("PHÁT HÀNG (" + mailerDeliveryInfoList.size() + ")");

    }

    @Override
    public void stopRefeshWipe() {
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }


    @Override
    public void onSelected(MailerDeliveryInfo contact) {
        Utils.DeliveryInfoCurrent = contact;
        Intent intent = new Intent(getActivity(), UpdateDeliveryActivity.class);
        startActivity(intent);
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
