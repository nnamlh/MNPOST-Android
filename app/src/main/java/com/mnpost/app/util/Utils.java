package com.mnpost.app.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.frosquivel.magicalcamera.MagicalCamera;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mnpost.app.R;
import com.mnpost.app.data.source.remote.MailerDeliveryInfo;

import java.text.NumberFormat;

public class Utils {

    public static String formatMoneyToText(double value) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString = formatter.format(value);
        if (moneyString.endsWith(".00")) {
            int centsIndex = moneyString.lastIndexOf(".00");
            if (centsIndex != -1) {
                moneyString = moneyString.substring(1, centsIndex);
            }
        }

        return moneyString + " VND";
    }

    public static void showError(Throwable e, Context context) {
        if(e.getClass().equals(HttpException.class)) {
            HttpException msg = (HttpException)e;
            if(msg.code() == 400) {
                Toast.makeText(context, "Sai tài khoản", Toast.LENGTH_SHORT).show();
            } else if(msg.code() == 401) {
                Toast.makeText(context, "Bạn không có quyền truy cập", Toast.LENGTH_SHORT).show();
            }
            else if(msg.code() == 404) {
                Toast.makeText(context, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(context, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        }
    }

    public  static void showDialog(Context context, String title, String content, DialogInterface.OnClickListener listener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setPositiveButton("Đồng ý", listener);
        builder.setNegativeButton("Thôi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        Dialog dialog = builder.create();

        dialog.show();
    }
    public static boolean validateMagicalCameraNull(Context context, MagicalCamera magicalCamera){
        if(magicalCamera != null) {
            if (magicalCamera.getPhoto() != null) {
                return true;
            }else{
                Toast.makeText(context,R.string.error_image_null, Toast.LENGTH_SHORT).show();
                return false;
            }
        }else{
            Toast.makeText(context,R.string.error_init_magicalcamera, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    /**
     * View Snack bar for simple form
     * @param message the message to shown
     * @param view the principal view (layout)
     */
    public static void viewSnackBar(String message, View view){
        Snackbar snackbar =  Snackbar.make(view, message,
                Snackbar.LENGTH_LONG).setDuration(Snackbar.LENGTH_LONG);

        View snackbarView = snackbar.getView();
        TextView tv= (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setMaxLines(5);
        snackbar.show();
    }

    /**
     * validate if the variable is null or empty
     * @param validate the string to validate
     * @return true or false
     */
    public static boolean notNullNotFill(String validate){
        if(validate != null){
            if(!validate.trim().equals("")){
                return true;
            }else{

                return false;
            }
        }else{
            return false;
        }
    }

    //
    public static MailerDeliveryInfo DeliveryInfoCurrent;

}
