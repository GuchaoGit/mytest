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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.hardcattle.Utils.GlideImageLoader;
import com.example.hardcattle.Utils.MyImageLoader;
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
    private RequestOptions options = new RequestOptions();
    private MyImageLoader myImageLoader;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_imagepicker_test);
        checkPermissions();
        options.diskCacheStrategy(DiskCacheStrategy.NONE);//禁用调硬盘缓存
        picBase64 = "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";
        myImageLoader = new MyImageLoader();
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
                    Glide.with(this).load(data).apply(options).into(ivShowBase64);
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
