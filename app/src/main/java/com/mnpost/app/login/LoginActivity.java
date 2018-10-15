package com.mnpost.app.login;

import android.os.Bundle;
import com.mnpost.app.BaseActivity;
import com.mnpost.app.R;
import com.mnpost.app.util.ActivityUtils;


public class LoginActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    loginFragment, R.id.contentFrame);
        }

        new LoginPresenter(loginFragment);
    }

}
