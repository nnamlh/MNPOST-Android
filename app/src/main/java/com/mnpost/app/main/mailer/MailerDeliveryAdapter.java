package com.mnpost.app.main.mailer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mnpost.app.R;
import com.mnpost.app.data.source.remote.MailerDeliveryInfo;
import com.mnpost.app.util.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MailerDeliveryAdapter extends RecyclerView.Adapter<MailerDeliveryAdapter.MyViewHolder> {

    List<MailerDeliveryInfo> mailerDeliveryInfos;

    MailerDeliveryAdapterListener listener;

    Context context;

    public MailerDeliveryAdapter(Context context, List<MailerDeliveryInfo> datas, MailerDeliveryAdapterListener listener) {
        this.context = context;
        this.mailerDeliveryInfos = datas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mailer_delivery, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MailerDeliveryInfo info = mailerDeliveryInfos.get(position);

        holder.eMailerId.setText(info.getMailerID());

        holder.ePhone.setText("sdt: " + info.getRecieverPhone() + " (" + info.getRecieverName() + ")" );

        holder.eAddress.setText(info.getRecieverAddress());

        holder.eCoD.setText(Utils.formatMoneyToText(info.getCOD()));

        holder.eProvince.setText(info.getRecieProvinceName());

        holder.eDistrict.setText(info.getReceiDistrictName());

        holder.eTime.setText(info.getDocumentDate());

        holder.eWard.setText(info.getReceiWardName());

        holder.eMailerType.setText(info.getMailerTypeID());

        if(info.getMailerTypeID().equals("HT")) {
            holder.imgFast.setVisibility(View.VISIBLE);
        } else {
            holder.imgFast.setVisibility(View.GONE);
        }
}

    @Override
    public int getItemCount() {
        return mailerDeliveryInfos.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.mailerid)
        TextView eMailerId;

        @BindView(R.id.phone)
        TextView ePhone;

        @BindView(R.id.address)
        TextView eAddress;

        @BindView(R.id.cod)
        TextView eCoD;

        @BindView(R.id.province)
        TextView eProvince;

        @BindView(R.id.district)
        TextView eDistrict;

        @BindView(R.id.time)
        TextView eTime;

        @BindView(R.id.ward)
        TextView eWard;

        @BindView(R.id.mailertype)
        TextView eMailerType;

        @BindView(R.id.imgfast)
        ImageView imgFast;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onSelected(mailerDeliveryInfos.get(getAdapterPosition()));
                }
            });
        }
    }


    public interface MailerDeliveryAdapterListener {
        void onSelected(MailerDeliveryInfo contact);
    }

}
