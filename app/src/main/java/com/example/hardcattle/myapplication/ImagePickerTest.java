package com.example.hardcattle.myapplication;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hardcattle.Utils.GlideImageLoader;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.util.ArrayList;
import java.util.List;

import static com.lzy.imagepicker.ImagePicker.REQUEST_CODE_PREVIEW;

/**
 * Created by guc on 2018/4/2.
 * 描述：
 */

public class ImagePickerTest extends AppCompatActivity implements View.OnClickListener {
    private Button btnGetPic, btnTakePic;
    private ImageView ivShow;
    public static final int REQUEST_CODE_SELECT = 100;
    private ImagePicker imagePicker;
    private int maxNum = 1;

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
        ivShow = findViewById(R.id.iv_imagepickertest);
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setShowCamera(true);
        imagePicker.setSelectLimit(maxNum);
        imagePicker.setCrop(false);
        images = new ArrayList<>();
        btnGetPic.setOnClickListener(this);
        btnTakePic.setOnClickListener(this);
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
        }
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
