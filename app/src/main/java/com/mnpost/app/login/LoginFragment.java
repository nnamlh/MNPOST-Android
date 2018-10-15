package com.mnpost.app.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mnpost.app.R;
import com.mnpost.app.main.MainActivity;
import com.mnpost.app.util.DialogLoading;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginFragment extends Fragment implements LoginContract.View {


    Unbinder unbinder;

    private LoginContract.Presenter mPresenter;

    @BindView(R.id.ename)
    EditText eName;

    @BindView(R.id.epass)
    EditText ePass;

    @BindView(R.id.btnlogin)
    Button btnLogin;

    DialogLoading dLoading;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        dLoading = new DialogLoading(getContext());

        unbinder = ButterKnife.bind(this, root);

        return root;
    }


    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.mPresenter = presenter;
    }


    @Override
    public void onResume() {
        super.onResume();
       if(mPresenter != null)
           mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mPresenter != null)
            mPresenter.unsubscribe();
    }


    @Override
    public Activity getMContext() {
        return getActivity();
    }

    @Override
    @OnClick(R.id.btnlogin)
    public void loginSubmit() {
        if(TextUtils.isEmpty(eName.getText().toString())  || TextUtils.isEmpty(ePass.getText().toString())) {
            Toast.makeText(getContext(), "Nhập đủ thông tin", Toast.LENGTH_LONG).show();
        } else {
            mPresenter.callLogin(eName.getText().toString(), ePass.getText().toString());
        }
    }

    @Override
    public void showLoading(boolean flag) {
        if(flag) {
            dLoading.show();
        } else {
            dLoading.dismiss();
        }
    }

    @Override
    public void nextTask() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
