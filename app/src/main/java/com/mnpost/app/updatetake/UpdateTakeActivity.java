package com.mnpost.app.updatetake;

import android.os.Bundle;

import com.mnpost.app.BaseActivity;
import com.mnpost.app.R;
import com.mnpost.app.deliveryhistory.DeliveryHistoryFragment;
import com.mnpost.app.deliveryhistory.DeliveryHistoryPresenter;
import com.mnpost.app.util.ActivityUtils;

public class UpdateTakeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_take);

        UpdateTakeFragment updateTakeFragment = (UpdateTakeFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (updateTakeFragment == null) {
            updateTakeFragment = UpdateTakeFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),  updateTakeFragment, R.id.contentFrame);

        }

        new UpdateTakePresenter(updateTakeFragment);
    }
}
