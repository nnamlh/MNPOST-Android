package com.mnpost.app.util;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Window;

import com.mnpost.app.R;

public class DialogLoading extends Dialog {


    public DialogLoading(@NonNull Context context) {
        super(context);

        this.setCancelable(false);
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.loading_dialog);

    }
}
