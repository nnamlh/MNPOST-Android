package com.mnpost.app.updatedelivery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.frosquivel.magicalcamera.MagicalCamera;
import com.mnpost.app.BaseActivity;
import com.mnpost.app.R;
import com.mnpost.app.util.ActivityUtils;

import java.util.Map;

public class UpdateDeliveryActivity extends BaseActivity {

    UpdateDeliveryFragment updateDeliveryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delivery);

        updateDeliveryFragment = (UpdateDeliveryFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (updateDeliveryFragment == null) {
            updateDeliveryFragment = UpdateDeliveryFragment.newInstance();
            updateDeliveryFragment.updateActivity(this);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),  updateDeliveryFragment, R.id.contentFrame);

        }

        new UpdateDeliveryPresenter(updateDeliveryFragment);
    }



}
