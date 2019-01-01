package com.mnpost.app.deliveryhistory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mnpost.app.R;
import com.mnpost.app.data.source.remote.MailerDeliveryInfo;
import com.mnpost.app.main.mailer.MailerDeliveryAdapter;
import com.mnpost.app.util.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeliveryHistoryAdapter extends RecyclerView.Adapter<DeliveryHistoryAdapter.MyViewHolder> {

    List<MailerDeliveryInfo> mailerDeliveryInfos;

    MailerDeliveryAdapterListener listener;

    Context context;

    public DeliveryHistoryAdapter(Context context, List<MailerDeliveryInfo> datas, DeliveryHistoryAdapter.MailerDeliveryAdapterListener listener) {
        this.context = context;
        this.mailerDeliveryInfos = datas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mailer_delivery_history, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MailerDeliveryInfo info = mailerDeliveryInfos.get(position);

        holder.eMailerId.setText(info.getMailerID());

        holder.ePhone.setText("sdt: " + info.getRecieverPhone() + " (" + info.getRecieverName() + ")");

        holder.eAddress.setText(info.getRecieverAddress());

        holder.eCoD.setText(Utils.formatMoneyToText(info.getCOD()));

        holder.eProvince.setText(info.getRecieProvinceName());

        holder.eDistrict.setText(info.getReceiDistrictName());

        holder.eTime.setText("Ngày phát: " + info.getDeliveryDate() + " " + info.getDeliveryTime());

        holder.status.setText(statusText(info.getCurrentStatusID()));

        holder.reciever.setText("Người nhận: " + info.getDeliveryTo());

        holder.note.setText("Ghi chú: " + info.getDeliveryNotes());

    }

    private String statusText(int status) {
        switch (status) {
            case 3:
                return "Đang phát";
            case 4:
                return "Đã phát xong";
            case 5:
                return "Chuyển hoàn";
            case 6:
                return "Chưa phát được";
            default:
                return "Không xác định";
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

        @BindView(R.id.reciever)
        TextView reciever;

        @BindView(R.id.status)
        TextView status;

        @BindView(R.id.note)
        TextView note;

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
