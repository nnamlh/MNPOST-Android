package com.mnpost.app.Splash;

import android.os.Bundle;

import com.mnpost.app.BaseActivity;
import com.mnpost.app.R;
import com.mnpost.app.util.ActivityUtils;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SplashFragment splashFrag = (SplashFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (splashFrag == null) {

            splashFrag = SplashFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), splashFrag, R.id.contentFrame);

        }

        new SplashPresenter(splashFrag);

    }
}
