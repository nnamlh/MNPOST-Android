package com.mnpost.app.updatedelivery;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mnpost.app.R;
import com.mnpost.app.data.source.CommonData;

import java.util.List;

public class ReasonAdapter extends BaseAdapter {


    Activity context;
    List<CommonData> commonDataList;

    public ReasonAdapter(Activity context, List<CommonData> commonDataList) {
        this.context = context;
        this.commonDataList = commonDataList;
    }

    @Override
    public int getCount() {
        return commonDataList.size();
    }

    @Override
    public CommonData getItem(int position) {
        return commonDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = context.getLayoutInflater().inflate(R.layout.item_text, parent, false);
        }

        TextView eName = convertView.findViewById(R.id.ename);

        CommonData item = commonDataList.get(position);

        eName.setText(item.getName());

        return convertView;
    }
}
