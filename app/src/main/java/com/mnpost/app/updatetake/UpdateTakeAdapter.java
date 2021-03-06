package com.mnpost.app.updatetake;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mnpost.app.R;
import com.mnpost.app.data.source.remote.TakeMailerDetailInfo;
import com.mnpost.app.util.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateTakeAdapter extends RecyclerView.Adapter<UpdateTakeAdapter.MyViewHolder>{

    AdapterListener listener;

    UpdateTakeAdapter.AdapterChangeLister changeLister;

    List<TakeMailerDetailInfo> takeMailerDetailInfos;

    Context context;

    public UpdateTakeAdapter(List<TakeMailerDetailInfo> takeMailerDetailInfos, Context context, UpdateTakeAdapter.AdapterListener listener, UpdateTakeAdapter.AdapterChangeLister changeLister) {
        this.context = context;
        this.listener = listener;
        this.takeMailerDetailInfos = takeMailerDetailInfos;

        this.changeLister = changeLister;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_take_mailer_detail, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        TakeMailerDetailInfo info = takeMailerDetailInfos.get(position);

        holder.eMailerId.setText(info.getMailerID());

        holder.eAddress.setText(info.getRecieverAddress());

        holder.eReciever.setText(info.getRecieverName());

        holder.ePhone.setText(info.getRecieverPhone());

       try{
           holder.eCoD.setText(Utils.formatMoneyToText(Double.valueOf(info.getCOD())));
       }catch (Exception e) {
           holder.eCoD.setText(info.getCOD());
       }

        holder.eWeight.setText(info.getWeight());

        holder.eWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changeLister.onChange(position, charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if(info.getCurrentStatusID() == 8) {
            holder.eStatus.setText("ĐÃ LẤY HÀNG");
            holder.eStatus.setTextColor(context.getResources().getColor(R.color.green));
            holder.btnTake.setVisibility(View.GONE);
        } else {
            holder.btnTake.setVisibility(View.VISIBLE);
            holder.eStatus.setText("CHƯA LẤY HÀNG");
            holder.eStatus.setTextColor(context.getResources().getColor(R.color.red));
            holder.btnTake.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onSelected(takeMailerDetailInfos.get(position),position, true);
                }
            });
        }


        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSelected(takeMailerDetailInfos.get(position),position, false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return takeMailerDetailInfos.size();
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

        @BindView(R.id.receiver)
        TextView eReciever;

        @BindView(R.id.weigh)
        EditText eWeight;

        @BindView(R.id.status)
        TextView eStatus;

        @BindView(R.id.btntake)
        Button btnTake;

        @BindView(R.id.btncancel)
        ImageView btnCancel;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }

    public interface AdapterListener {
        void onSelected(TakeMailerDetailInfo contact, int position, boolean isUpdate);
    }

    public interface  AdapterChangeLister {
        void onChange(int postion, String data);
    }
}
