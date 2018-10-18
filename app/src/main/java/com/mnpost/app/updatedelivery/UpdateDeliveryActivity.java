package com.mnpost.app.updatedelivery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.mnpost.app.BaseActivity;
import com.mnpost.app.R;
import com.mnpost.app.util.ActivityUtils;

import java.util.Map;

public class UpdateDeliveryActivity extends BaseActivity {

    UpdateDeliveryFragment updateDeliveryFragment;
    public static int OPEN_CAMERA_RESULT_ID = 10;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
