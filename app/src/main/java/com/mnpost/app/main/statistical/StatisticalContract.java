package com.mnpost.app.main.statistical;

import android.content.Context;

import com.mnpost.app.BasePresenter;
import com.mnpost.app.BaseView;
import com.mnpost.app.data.source.CommonStatistical;

import java.util.List;

public class StatisticalContract {

    interface View extends BaseView<StatisticalContract.Presenter> {
        Context getMContext();
        String getCodeTime();
        void addPieData(List<CommonStatistical> data);
        void addCoDData(List<CommonStatistical> data);
    }


    interface Presenter extends BasePresenter{

    }
}
