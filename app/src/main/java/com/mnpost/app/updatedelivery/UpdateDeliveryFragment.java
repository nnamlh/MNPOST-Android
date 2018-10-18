package com.mnpost.app.updatedelivery;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.mnpost.app.R;
import com.mnpost.app.camera.CameraActivity;
import com.mnpost.app.data.source.CommonData;
import com.mnpost.app.data.source.remote.UpdateDeliverySend;
import com.mnpost.app.util.DialogLoading;
import com.mnpost.app.util.Utils;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UpdateDeliveryFragment extends Fragment implements UpdateDeliveryContract.View, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {


    UpdateDeliveryContract.Presenter mPresenter;

    DialogLoading dLoading;


    @BindView(R.id.btnupdate)
    Button btnUpdate;

    @BindView(R.id.btntakephoto)
    ImageView btnTakePhoto;

    @BindView(R.id.btntime)
    Button btnTime;

    @BindView(R.id.btnday)
    Button btnDay;
    @BindView(R.id.carouselView)
    CarouselView carouselView;

    List<Bitmap> bitmaps = new ArrayList<>();
    List<String> imagePaths = new ArrayList<>();

    UpdateDeliveryActivity activity;

    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;

    //
    @BindView(R.id.emailerid)
    TextView eMailerId;

    @BindView(R.id.ephone)
    TextView ePhone;

    @BindView(R.id.eaddress)
    TextView eAddress;

    @BindView(R.id.ecod)
    TextView eCod;

    @BindView(R.id.eprovince)
    TextView eProvince;

    @BindView(R.id.ereciver)
    EditText eReciver;

    @BindView(R.id.enote)
    EditText eNote;


    @BindView(R.id.rchoosestt)
    RadioGroup rGroupStatus;

    int statusChoose = 4; // da phat xong

    public static UpdateDeliveryFragment newInstance() {
        return new UpdateDeliveryFragment();
    }





    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(final int position, ImageView imageView) {
            imageView.setImageBitmap(bitmaps.get(position));
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    Utils.showDialog(getContext(), "Thông báo", "Bạn muốn xóa ảnh này ?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (bitmaps.size() > 1) {
                                bitmaps.remove(position);
                                imagePaths.remove(position);
                                carouselView.setImageListener(imageListener);
                                carouselView.setPageCount(bitmaps.size());
                            } else {
                                bitmaps.clear();
                                imagePaths.clear();
                                carouselView.removeAllViews();
                            }
                        }
                    });

                    return false;
                }
            });
        }
    };


    @BindView(R.id.resion_return)
    Spinner spinner;

    List<CommonData> commonDataList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_update_delivery, container, false);
        dLoading = new DialogLoading(getContext());

        ButterKnife.bind(this, view);

        eReciver.setEnabled(true);
        eNote.setEnabled(false);

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               takePhoto();

            }
        });

        Calendar now = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(
                UpdateDeliveryFragment.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        // set time
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        btnDay.setText(sdf.format(new Date()));
        sdf = new SimpleDateFormat("HH:mm");
        btnTime.setText(sdf.format(new Date()));

        updateView();

        timePickerDialog = TimePickerDialog.newInstance(UpdateDeliveryFragment.this, true);

        btnDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show(getActivity().getFragmentManager(), "Chọn ngày phát");
            }
        });


        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show(getActivity().getFragmentManager(), "Chọn giờ phát");
            }
        });

        rGroupStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_finish) {
                    spinner.setVisibility(View.GONE);
                    statusChoose = 4;
                    eReciver.setEnabled(true);
                    eNote.setEnabled(false);
                } else if (checkedId == R.id.radio_nofinish) {
                    spinner.setVisibility(View.GONE);
                    statusChoose = 6;
                    eReciver.setEnabled(false);
                    eNote.setEnabled(true);
                } else if (checkedId == R.id.radio_return) {
                    statusChoose = 5;
                    eReciver.setEnabled(false);
                    eNote.setEnabled(false);
                    spinner.setVisibility(View.VISIBLE);
                }
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validField()) {
                    Utils.showDialog(getMContext(), "Thông báo", "Bạn xác nhận cập nhật thông tin ?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            MultipartBody requestBody = null;

                            if (imagePaths.size() > 0) {
                                MultipartBody.Builder builder = new MultipartBody.Builder();
                                builder.setType(MultipartBody.FORM);

                                for (int i = 0; i < imagePaths.size(); i++) {
                                    Uri imageUri = Uri.parse(imagePaths.get(i));
                                    File file = new File(imageUri.getPath());
                                    RequestBody requestImage = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                                    builder.addFormDataPart("files", file.getName(), requestImage);
                                }

                                requestBody = builder.build();
                            }


                            UpdateDeliverySend sendInfo = new UpdateDeliverySend();
                            sendInfo.setDocumentID(Utils.DeliveryInfoCurrent.getDocumentID());
                            sendInfo.setMailerID(Utils.DeliveryInfoCurrent.getMailerID());
                            sendInfo.setReciever(eReciver.getText().toString());
                            sendInfo.setNote(eNote.getText().toString());
                            sendInfo.setDeliveryDate(btnDay.getText().toString() + " " + btnTime.getText().toString());
                            sendInfo.setReturnReasonID(commonDataList.get(spinner.getSelectedItemPosition()).getCode());
                            sendInfo.setStatusID(statusChoose);

                            mPresenter.update(requestBody, sendInfo);
                        }
                    });

                }


            }
        });

        return view;
    }


    @Override
    public void setPresenter(UpdateDeliveryContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public Context getMContext() {
        return getContext();
    }

    @Override
    public void takePhoto() {
        Intent intentCamenra = new Intent(getMContext(), CameraActivity.class);
        startActivityForResult(intentCamenra, UpdateDeliveryActivity.OPEN_CAMERA_RESULT_ID);
    }

    @Override
    @OnClick(R.id.btnback)
    public void onBAck() {
        Utils.showDialog(getContext(), "Thông báo", "Hủy cập nhật hiện tại ?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void showImage(Bitmap bitmap) {

       if(bitmap != null){
           bitmaps.add(bitmap);

           carouselView.setImageListener(imageListener);
           carouselView.setPageCount(bitmaps.size());
       }

    }

    @Override
    public void updateActivity(UpdateDeliveryActivity activity) {
        this.activity = activity;
    }

    @Override
    public void updateView() {
        eMailerId.setText(Utils.DeliveryInfoCurrent.getMailerID());

        eAddress.setText(Utils.DeliveryInfoCurrent.getRecieverAddress());

        ePhone.setText(Utils.DeliveryInfoCurrent.getRecieverName() + "." + Utils.DeliveryInfoCurrent.getRecieverPhone());

        eProvince.setText(Utils.DeliveryInfoCurrent.getRecieverProvinceID() + "-" + Utils.DeliveryInfoCurrent.getRecieverDistrictID());

        eCod.setText(Utils.formatMoneyToText(Utils.DeliveryInfoCurrent.getCOD()));
    }

    @Override
    public void initSpinnerReason(List<CommonData> datas) {

        commonDataList = new ArrayList<>();

        commonDataList.addAll(datas);

        ReasonAdapter adapter = new ReasonAdapter(getActivity(), commonDataList);
        spinner.setAdapter(adapter);

        spinner.setVisibility(View.GONE);
    }

    @Override
    public boolean validField() {

        boolean isValid = true;

        if (statusChoose == 4) {
            if (TextUtils.isEmpty(eReciver.getText().toString())) {
                isValid = false;
                Toast.makeText(getMContext(), "Nhập người nhận", Toast.LENGTH_SHORT).show();
            }
        } else if (statusChoose == 5) {

        } else if (statusChoose == 6) {
            if (TextUtils.isEmpty(eNote.getText().toString())) {
                isValid = false;
                Toast.makeText(getMContext(), "Nhập ghi chú thông báo không phát được", Toast.LENGTH_SHORT).show();
            }
        }

        return isValid;
    }

    @Override
    public void showLoading(boolean flag) {
        if (flag) {
            dLoading.show();
        } else {
            dLoading.dismiss();
        }
    }

    @Override
    public void finishUpdate() {
        getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        btnDay.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        btnTime.setText(hourOfDay + ":" + minute);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == UpdateDeliveryActivity.OPEN_CAMERA_RESULT_ID) {
                String path = data.getExtras().getString("PATH");
                if(path != null) {
                    imagePaths.add(path);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap bitmap = BitmapFactory.decodeFile(path, options);
                    showImage(bitmap);
                }
            }

        }
    }

}
