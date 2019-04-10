package com.mnpost.app.main.notice;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mnpost.app.R;
import com.mnpost.app.data.source.remote.NotificationInfo;

import java.util.List;

/**
 * Created by HAI on 9/15/2017.
 */

public class NotificationAdapter  extends BaseAdapter{


    List<NotificationInfo> notificationInfos;

    Activity activity;

    LayoutInflater inflater;

    public NotificationAdapter(List<NotificationInfo> notificationInfos, Activity activity) {
        this.notificationInfos = notificationInfos;
        this.activity = activity;
        inflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return notificationInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return notificationInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if(view == null) {
            view = inflater.inflate(R.layout.notification_item_row, null);
        }
        TextView txtTile, txtMessenge, txtTime;

        txtTile = (TextView) view.findViewById(R.id.title);
        txtMessenge = (TextView) view.findViewById(R.id.messenge);
        txtTime = (TextView) view.findViewById(R.id.time);

        NotificationInfo info = notificationInfos.get(position);
        txtTile.setText(info.getTitle());
        txtMessenge.setText(info.getMessenger());
        txtTime.setText(info.getTime());

        if(info.isRead()) {
           txtTime.setTextColor(Color.parseColor("#E0E0E0"));
           txtMessenge.setTextColor(Color.parseColor("#E0E0E0"));
           txtTime.setTextColor(Color.parseColor("#E0E0E0"));
        }
        return view;
    }

}
