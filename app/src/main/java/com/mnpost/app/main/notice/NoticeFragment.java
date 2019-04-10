package com.mnpost.app.main.notice;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mnpost.app.R;
import com.mnpost.app.data.source.remote.NotificationInfo;
import com.mnpost.app.data.source.remote.ResponseNotices;
import com.mnpost.app.util.ApiClient;
import com.mnpost.app.util.ApiService;
import com.mnpost.app.util.Utils;
import com.mnpost.app.view.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class NoticeFragment extends Fragment {


    public static NoticeFragment  getInstance() {
        return  new NoticeFragment();
    }

    NotificationAdapter mAdapter;

    @BindView(R.id.list)
    LoadMoreListView listView;

    private ApiService apiService;
    List<NotificationInfo> notificationInfos;
    @NonNull
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notice, container, false);

        ButterKnife.bind(this, view);


        notificationInfos =new ArrayList<>();
        mAdapter = new NotificationAdapter(notificationInfos,getActivity());

        listView.setAdapter(mAdapter);
        listView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
          
            }
        });
        ApiClient.resetApiClient();
        apiService = ApiClient.getClient(getContext()).create(ApiService.class);
        makeRequest();
        return view;
    }

    private void makeRequest() {
        mCompositeDisposable.add(apiService.getNotices().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<ResponseNotices>() {
            @Override
            public void onSuccess(ResponseNotices responseNotices) {
                notificationInfos.addAll(responseNotices.getData());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                Utils.showError(e, getContext());
            }
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCompositeDisposable.clear();
    }

}
