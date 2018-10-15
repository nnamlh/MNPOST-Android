package com.mnpost.app.main.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mnpost.app.R;
import com.mnpost.app.login.LoginActivity;
import com.mnpost.app.util.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingFragment extends Fragment implements SettingContract.View {

    SettingContract.Presenter mPresenter;


    @BindView(R.id.btnexit)
    CardView btnExit;

    @Override
    public void setPresenter(SettingContract.Presenter presenter) {
        this.mPresenter = presenter;
    }


     public static SettingFragment newInstance() {

        SettingFragment fragment = new SettingFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        ButterKnife.bind(this, view);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.storeApiKey(getContext(), "");

                PrefUtils.storeUser(getContext(), "");

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }
}
