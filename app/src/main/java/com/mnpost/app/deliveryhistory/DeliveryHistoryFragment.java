package com.mnpost.app.deliveryhistory;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TextView;

import com.mnpost.app.R;
import com.mnpost.app.data.source.remote.MailerDeliveryInfo;
import com.mnpost.app.main.mailer.MailerDeliveryAdapter;
import com.mnpost.app.main.mailer.MailerFragment;
import com.mnpost.app.updatedelivery.UpdateDeliveryFragment;
import com.mnpost.app.util.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeliveryHistoryFragment extends Fragment implements DeliveryHistoryContract.View, DatePickerDialog.OnDateSetListener, DeliveryHistoryAdapter.MailerDeliveryAdapterListener {

    DeliveryHistoryContract.Presenter mPresenter;

    @BindView(R.id.etime)
    TextView eTime;

    @BindView(R.id.ecount)
    TextView eCount;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    DatePickerDialog datePickerDialog;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    DeliveryHistoryAdapter mAdapter;

    List<MailerDeliveryInfo> mailerDeliveryInfos;

    public static DeliveryHistoryFragment newInstance() {
        return new DeliveryHistoryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_delivery_history, container, false);

        ButterKnife.bind(this, view);

        Calendar now = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(
                DeliveryHistoryFragment.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        // set time
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        eTime.setText(sdf.format(new Date()));

        eTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show(getActivity().getFragmentManager(), "Chọn ngày xem");
            }
        });

        eCount.setText("0");

        mailerDeliveryInfos = new ArrayList<>();

        mAdapter = new DeliveryHistoryAdapter(getContext(), mailerDeliveryInfos, this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(5), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.subscribe();
            }
        });

        return view;
    }

    @Override
    public Context getMContext() {
        return getContext();
    }

    @OnClick(R.id.btnback)
    public void onBAck() {
        Utils.showDialog(getContext(), "Thông báo", "Hủy cập nhật hiện tại ?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().onBackPressed();
            }
        });
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
    public void showRefeshWipe(boolean show) {
        swipeRefreshLayout.setRefreshing(show);
    }

    @Override
    public void refeshData(List<MailerDeliveryInfo> mailerDeliveryInfoList) {
        eCount.setText("Tìm thấy: " + mailerDeliveryInfoList.size() + "");

        mailerDeliveryInfos.clear();
        mailerDeliveryInfos.addAll(mailerDeliveryInfoList);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(DeliveryHistoryContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        eTime.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
        mPresenter.setTimeChoose(eTime.getText().toString());
        mPresenter.subscribe();
    }

    @Override
    public void onSelected(MailerDeliveryInfo contact) {

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
