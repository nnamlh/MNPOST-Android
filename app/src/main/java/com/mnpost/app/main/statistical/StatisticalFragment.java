package com.mnpost.app.main.statistical;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.mnpost.app.R;
import com.mnpost.app.data.source.CommonData;
import com.mnpost.app.data.source.CommonStatistical;
import com.mnpost.app.updatedelivery.ReasonAdapter;
import com.mnpost.app.util.DialogLoading;
import com.mnpost.app.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticalFragment extends Fragment implements StatisticalContract.View {

    StatisticalContract.Presenter mPresenter;

    @BindView(R.id.piechart)
    PieChart pieChart;

    @BindView(R.id.spinner_time)
    Spinner spinner;

    List<CommonData> commonDataList;

    @BindView(R.id.codkeep)
    TextView coDKepp;

    @BindView(R.id.codpay)
    TextView coDPay;

    @BindView(R.id.btnLoad)
    ImageView btnLoad;

    public static StatisticalFragment newInstance() {
        return new StatisticalFragment();
    }

    @Override
    public void setPresenter(StatisticalContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statistical, container, false);

        ButterKnife.bind(this, view);

        pieChart.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);
        pieChart.setRotationEnabled(true);
        pieChart.setUsePercentValues(false);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("");
        pieChart.setCenterTextSize(10);

        initChooseTime();

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.subscribe();
            }
        });
        return view;
    }


    public void initChooseTime() {
        commonDataList = new ArrayList<>();

        CommonData data = new CommonData();

        data.setCode("Day");
        data.setName("Trong ngày");
        commonDataList.add(data);

        data = new CommonData();
        data.setCode("Yesterday");
        data.setName("Hôm qua");
        commonDataList.add(data);

        data = new CommonData();
        data.setCode("Week");
        data.setName("Trong tuần");
        commonDataList.add(data);

        data = new CommonData();
        data.setCode("Month");
        data.setName("Trong tháng");
        commonDataList.add(data);

        data = new CommonData();
        data.setCode("LastMonth");
        data.setName("Tháng trước");
        commonDataList.add(data);


        ReasonAdapter adapter = new ReasonAdapter(getActivity(), commonDataList);
        spinner.setAdapter(adapter);

    }


    @Override
    public Context getMContext() {
        return getContext();
    }

    @Override
    public String getCodeTime() {
        return commonDataList.get(spinner.getSelectedItemPosition()).getCode();
    }

    @Override
    public void addPieData(List<CommonStatistical> data) {

        if (data == null)
            return;

        ArrayList<PieEntry> yEntrys = new ArrayList<>();

        ArrayList<Integer> colors = new ArrayList<>();

        for (CommonStatistical item : data) {

            switch (item.getCode()) {

                case "3":
                    yEntrys.add(new PieEntry(item.getData(), "Đang phát"));
                    colors.add(Color.GRAY);
                    break;
                case "4":
                    yEntrys.add(new PieEntry(item.getData(), "Đã phát"));
                    colors.add(Color.BLUE);
                    break;
                case "5":
                    yEntrys.add(new PieEntry(item.getData(), "Chuyển hoàn"));
                    colors.add(Color.RED);
                    break;
                case "6":
                    yEntrys.add(new PieEntry(item.getData(), "Chưa phát được"));
                    colors.add(Color.GREEN);
                    break;
            }
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "");
        pieDataSet.setSliceSpace(0);
        pieDataSet.setValueTextSize(12);


        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);

        //create pie data object
        pieChart.setDrawSliceText(false);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        pieChart.invalidate();
    }

    @Override
    public void addCoDData(List<CommonStatistical> data) {
        if(data == null)
            return;

        for(CommonStatistical item : data) {

            if(item.getCode().equals("1")) {
                coDPay.setText(Utils.formatMoneyToText(item.getData()));
            } else if(item.getCode().equals("0")) {
                coDKepp.setText(Utils.formatMoneyToText(item.getData()));
            }

        }

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
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }
}
