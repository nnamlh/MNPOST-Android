package com.mnpost.app.deliveryhistory;

import android.os.Bundle;

import com.mnpost.app.BaseActivity;
import com.mnpost.app.R;
import com.mnpost.app.util.ActivityUtils;

public class DeliveryHistoryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delivery);

        DeliveryHistoryFragment deliveryHistoryFragment = (DeliveryHistoryFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (deliveryHistoryFragment == null) {
            deliveryHistoryFragment = DeliveryHistoryFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),  deliveryHistoryFragment, R.id.contentFrame);

        }

        new DeliveryHistoryPresenter(deliveryHistoryFragment);
    }


}
