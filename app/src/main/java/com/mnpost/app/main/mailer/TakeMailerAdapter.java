package com.mnpost.app.main.mailer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mnpost.app.R;
import com.mnpost.app.data.source.remote.TakeMailerInfo;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TakeMailerAdapter extends RecyclerView.Adapter<TakeMailerAdapter.MyViewHolder>{

    List<TakeMailerInfo> takeMailerInfos;

    AdapterListener listener;

    Context context;

    public TakeMailerAdapter(Context context, List<TakeMailerInfo> datas, AdapterListener listener) {
        this.context = context;
        this.takeMailerInfos = datas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_take_mailer, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TakeMailerAdapter.MyViewHolder holder, int position) {
        TakeMailerInfo info = takeMailerInfos.get(position);

        holder.eCustomer.setText(info.getCustomerName());

        holder.ePhone.setText("sdt: " + info.getCustomerAddress());

        holder.eAddress.setText(info.getCustomerAddress());

        holder.eNotes.setText(info.getContent());
    }

    @Override
    public int getItemCount() {
        return takeMailerInfos.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.customer)
        TextView eCustomer;

        @BindView(R.id.phone)
        TextView ePhone;

        @BindView(R.id.address)
        TextView eAddress;

        @BindView(R.id.notes)
        TextView eNotes;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onSelected(takeMailerInfos.get(getAdapterPosition()));
                }
            });
        }
    }


    public interface AdapterListener {
        void onSelected(TakeMailerInfo contact);
    }

}
