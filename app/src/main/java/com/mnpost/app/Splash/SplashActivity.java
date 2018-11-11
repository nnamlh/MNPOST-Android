package com.mnpost.app.Splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mnpost.app.R;
import com.mnpost.app.util.ActivityUtils;

public class SplashActivity extends AppCompatActivity {

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
