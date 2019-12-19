package com.example.hardcattle.myapplication;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.example.hardcattle.Utils.GlideImageLoader;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.lzy.imagepicker.ImagePicker.REQUEST_CODE_PREVIEW;

/**
 * Created by guc on 2018/4/2.
 * 描述：
 */

public class ImagePickerTest extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ImagePickerTest";
    private Button btnGetPic, btnTakePic, btnToBase64, btnShowBase64;
    private ImageView ivShow, ivShowBase64;
    private TextView tvBase64;
    public static final int REQUEST_CODE_SELECT = 100;
    private ImagePicker imagePicker;
    private int maxNum = 1;
    private String picBase64, picPath;

    private ArrayList<ImageItem> images;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_imagepicker_test);
        checkPermissions();
    }

    private void init() {
        btnGetPic = findViewById(R.id.btn_imagepickertest);
        btnTakePic = findViewById(R.id.btn_imagepickertest_take_pic);
        btnToBase64 = findViewById(R.id.btn_get_base64);
        btnShowBase64 = findViewById(R.id.btn_glide_show_base64);
        ivShow = findViewById(R.id.iv_imagepickertest);
        tvBase64 = findViewById(R.id.tv_base64);
        ivShowBase64 = findViewById(R.id.iv_show);
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setShowCamera(true);
        imagePicker.setSelectLimit(maxNum);
        imagePicker.setCrop(false);
        images = new ArrayList<>();
        btnGetPic.setOnClickListener(this);
        btnTakePic.setOnClickListener(this);
        btnToBase64.setOnClickListener(this);
        btnShowBase64.setOnClickListener(this);
        ivShow.setOnClickListener(this);
    }

    private void checkPermissions() {
        Acp.getInstance(this).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE
                                , Manifest.permission.READ_PHONE_STATE
                                , Manifest.permission.CAMERA
                                , Manifest.permission.RECORD_AUDIO)
                        /*以下为自定义提示语、按钮文字
                        .setDeniedMessage()
                        .setDeniedCloseBtn()
                        .setDeniedSettingBtn()
                        .setRationalMessage()
                        .setRationalBtn()*/
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        init();
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        Toast.makeText(ImagePickerTest.this, permissions.toString() + "权限拒绝", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_imagepickertest:
                jumpPicsSelect();
                break;
            case R.id.iv_imagepickertest:
                if (images != null && images.size() > 0)
                    jumpPicsPreview(0);
                break;
            case R.id.btn_imagepickertest_take_pic:
                takePics();
                break;
            case R.id.btn_get_base64:
                if (images == null || images.size() == 0) {
                    return;
                } else {
                    picPath = images.get(0).path;
                    picBase64 = img2Base64(picPath);

                    tvBase64.setText(picBase64.substring(0, 100) + "...");
                }
                break;
            case R.id.btn_glide_show_base64:
                if (TextUtils.isEmpty(picBase64)) {
                    ToastUtils.showShort("没有Base64图片");
                } else {
                    byte[] data = Base64.decode(picBase64, Base64.DEFAULT);
                    Glide.with(this).load(data).into(ivShowBase64);
                }
                break;
        }
    }

    private String img2Base64(String p) {
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(p);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    /**
     * 直接拍照
     */
    private void takePics() {
        ImagePicker.getInstance().takePicture(this, 500);
    }

    /**
     * 跳转至图片选择
     */
    private void jumpPicsSelect() {
        ImagePicker.getInstance().setSelectLimit(maxNum);
        Intent intent1 = new Intent(this, ImageGridActivity.class);
        intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES, images);
        startActivityForResult(intent1, REQUEST_CODE_SELECT);
    }

    /**
     * 跳转至预览界面
     */
    private void jumpPicsPreview(int position) {
        Intent intentPreview = new Intent(ImagePickerTest.this, ImagePreviewDelActivity.class);
        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, images);
        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
        intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
        intentPreview.putExtra("showDel", false);
        startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                //noinspection unchecked
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null && images.size() > 0) {
                    imagePicker.getImageLoader().displayImage(ImagePickerTest.this, images.get(0).path, ivShow, 800, 800);
                } else {
                    ivShow.setImageResource(R.drawable.ic_default_image);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null && images.size() > 0) {
                    imagePicker.getImageLoader().displayImage(ImagePickerTest.this, images.get(0).path, ivShow, 800, 800);
                } else {
                    ivShow.setImageResource(R.drawable.ic_default_image);
                }
            }
        } else if (requestCode == 500) {//直接拍照返回
            if (resultCode == 0) return;
            //发送广播通知图片增加了
            ImagePicker.galleryAddPic(this, imagePicker.getTakeImageFile());
            String path = imagePicker.getTakeImageFile().getAbsolutePath();
            ImageItem item = new ImageItem();
            item.path = path;
            if (images == null) images = new ArrayList<>();
            images.clear();
            images.add(item);
            imagePicker.getImageLoader().displayImage(ImagePickerTest.this, path, ivShow, 800, 800);
        }
    }
}
