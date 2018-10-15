package com.mnpost.app.main.statistical;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.mnpost.app.R;
import com.mnpost.app.data.source.CommonData;
import com.mnpost.app.updatedelivery.ReasonAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticalFragment extends Fragment implements StatisticalContract.View {

    StatisticalContract.Presenter mPresenter;

    @BindView(R.id.piechart)
    PieChart pieChart;
    private float[] yData = {20, 10, 20, 21};
    private String[] xData = {"Đã phát", "Phát không được" , "Chuyển hoàn" , "Lấy hàng"};
    @BindView(R.id.spinner_time)
    Spinner spinner;

    List<CommonData> commonDataList;
    public static StatisticalFragment newInstance() {
        return  new StatisticalFragment();
    }

    @Override
    public void setPresenter(StatisticalContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statistical,container, false);

        ButterKnife.bind(this, view);

        pieChart.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);
        pieChart.setRotationEnabled(true);
        pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("");
        pieChart.setCenterTextSize(10);
        addDataSet();

        initChooseTime();

        return view;
    }


    public void initChooseTime() {
        commonDataList = new ArrayList<>();

        CommonData data = new CommonData();
        data.setCode("1");
        data.setName("Trong ngày");

        commonDataList.add(data);

        data = new CommonData();
        data.setCode("2");
        data.setName("Trong tuần");
        commonDataList.add(data);

        data = new CommonData();
        data.setCode("3");
        data.setName("Trong tháng");
        commonDataList.add(data);

        data = new CommonData();
        data.setCode("4");
        data.setName("Tháng trước");
        commonDataList.add(data);


        ReasonAdapter adapter = new ReasonAdapter(getActivity(), commonDataList);
        spinner.setAdapter(adapter);

        spinner.setVisibility(View.GONE);
    }

    private void addDataSet() {

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i] , i));
        }

        for(int i = 1; i < xData.length; i++){
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Trạng thái");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);


        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}
