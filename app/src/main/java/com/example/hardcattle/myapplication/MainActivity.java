package com.example.hardcattle.myapplication;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.example.hardcattle.Utils.BinnerImageLoader;
import com.example.hardcattle.bean.ParcelableBean;
import com.example.hardcattle.widget.autocompleteview.AutoCompleteView;
import com.google.zxing.activity.CaptureActivity;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private AutoCompleteTextView tvShow;
    private AutoCompleteView autoCompleteView;

    private TextView tvSlideMenu, tvAppName ,tvImageTest,testRefreshLayout,tvTestSwipeCardView;
    private DrawerLayout mDrawLayout;
    private NavigationView mNaviView;
    private Button btnGsonTest, btnSortTest, btnScrollTableTest, btnTestScanCode, btnNfcTest, btnKeyBoardTest, btnNoticeTest, btnMapTest;
    private Banner mBanner;
    private List<ParcelableBean> userBeanList = new ArrayList<ParcelableBean>(){
        {
            add(new ParcelableBean("guc",24));
            add(new ParcelableBean("cxl",22));
            add(new ParcelableBean("gff",28));
            add(new ParcelableBean("cyw",21));
        }
    };

    private List<String> arrayList = new ArrayList<String>() {
        {
            add("上海");
            add("广州");
            add("北京");
            add("河北");
            add("大连");
            add("天津");
            add("南京");
            add("河南");
            add("广西");
            add("北上广");
            add("南下");

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_main);
        setStatusBar();
        findView();
        initListener();
        testStringArray();
        checkPermissions();

        A a = new A();
        int i = 0;
        a.fermin(i);
        i = i++;
        Log.e("aaa", i + "");
    }

    private void setStatusBar(){
        // 设置图片沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
    private void testStringArray() {
        String[] array = getResources().getStringArray(R.array.city);
        for (String item : array) {
            Log.e("array", item);
        }
    }

    private void initBanner(){
        final List<String> titles = new ArrayList(){
            {
                add("我们准备玩一场大的");
                add("夏日美丽专属");
                add("量身定做");
            }
        };
        List<String> images = new ArrayList(){
            {
                add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1525674275984&di=3dc2ddc98dccfa995ba84db39c87ea50&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01f1cf554501b30000019ae9e37050.jpg%402o.jpg");
                add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1525674276933&di=d77225c265896ec0176e3bc583544ff5&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D6895722%2C610391116%26fm%3D214%26gp%3D0.jpg");
                add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1525674275984&di=8b1cdfc9f6efe4a70f9d53c33441c128&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F13%2F18%2F79%2F36s58PICHwi_1024.jpg");
            }
        };
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        mBanner.setImageLoader(new BinnerImageLoader());
        //设置图片集合
        mBanner.setImages(images);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                ToastUtils.showShort(titles.get(position));
            }
        });
    }
    private void findView() {
        tvShow = findViewById(R.id.auto_complete_tv);
        autoCompleteView = findViewById(R.id.acv);
        tvSlideMenu = findViewById(R.id.tv_slide_menu);

        mDrawLayout = findViewById(R.id.drawer_layout);
        mNaviView = findViewById(R.id.nav_view);

        mBanner = findViewById(R.id.banner);
        View view = mNaviView.getHeaderView(0);//获取侧边栏View
        tvAppName = view.findViewById(R.id.tv_app_name);
        tvImageTest = view.findViewById(R.id.tv_imagepicker_test);
        testRefreshLayout = view.findViewById(R.id.tv_refresh_layout_test);
        tvTestSwipeCardView = view.findViewById(R.id.tv_swipe_cardview_test);
        btnGsonTest = findViewById(R.id.btn_gson_test);
        btnSortTest = findViewById(R.id.btn_sort_test);
        btnScrollTableTest = findViewById(R.id.btn_scroll_test);
        btnTestScanCode = findViewById(R.id.btn_scan_code_test);
        btnNfcTest = findViewById(R.id.btn_nfc_test);
        btnKeyBoardTest = findViewById(R.id.btn_keyboard_test);
        btnNoticeTest = findViewById(R.id.btn_notice_test);
        btnMapTest = findViewById(R.id.btn_map_test);
        autoCompleteView.setData("zylb");

    }

    private void initListener() {
        tvSlideMenu.setOnClickListener(this);
        tvAppName.setOnClickListener(this);
        tvImageTest.setOnClickListener(this);
        btnGsonTest.setOnClickListener(this);
        testRefreshLayout.setOnClickListener(this);
        tvTestSwipeCardView.setOnClickListener(this);
        btnSortTest.setOnClickListener(this);
        btnScrollTableTest.setOnClickListener(this);
        btnTestScanCode.setOnClickListener(this);
        btnNfcTest.setOnClickListener(this);
        btnKeyBoardTest.setOnClickListener(this);
        btnNoticeTest.setOnClickListener(this);
        btnMapTest.setOnClickListener(this);
    }

    /**
     * 布局测试点击事件
     *
     * @param view
     */
    public void onLayoutTest(View view) {
        startActivity(new Intent(this, LayoutActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.auto_complete_tv://
                if (!tvShow.isPopupShowing()) {
                    tvShow.showDropDown();
                }
                break;
            case R.id.tv_slide_menu://侧边栏
                if (!mDrawLayout.isDrawerOpen(mNaviView)) {
                    mDrawLayout.openDrawer(mNaviView);
                }
                break;
            case R.id.tv_app_name://侧滑菜单
                startActivity(new Intent(this,TestRxJavaAndRetrofit.class));
                mDrawLayout.closeDrawer(mNaviView);
                break;
            case R.id.tv_imagepicker_test:
                startActivity(new Intent(this,ImagePickerTest.class));
                mDrawLayout.closeDrawer(mNaviView);
                break;
            case R.id.btn_gson_test:
                testGson();
                break;
            case R.id.tv_refresh_layout_test:
                startActivity(new Intent(this,TestRefreshLayout.class));
                mDrawLayout.closeDrawer(mNaviView);
                break;
            case R.id.btn_sort_test://排序测试
                testSort();
                break;
            case R.id.btn_scroll_test:
                startActivity(new Intent(this,ScrollTableLayoutTest.class));
                break;
            case R.id.tv_swipe_cardview_test://可滑动的SwipeCardView
                startActivity(new Intent(this,TestSwipeCardView.class));
                break;
            case R.id.btn_scan_code_test://二维码扫描测试
                Intent intent = new Intent(this,CaptureActivity.class);
                startActivityForResult(intent,REQUEST_SCAN);
                break;
            case R.id.btn_nfc_test://nfc功能测试
//                Intent intent1 = new Intent(this, ActivityTagViewer.class);
                Intent intent1 = new Intent(this, NFCTest.class);
                startActivity(intent1);
                break;
            case R.id.btn_keyboard_test:
                Intent intent2 = new Intent(this, TestCustomKeyBoard.class);
                startActivity(intent2);
                break;
            case R.id.btn_notice_test://通知
                Intent intent3 = new Intent(this, TestNotice.class);
                startActivity(intent3);
                break;
            case R.id.btn_map_test://百度地图
                Intent intent4 = new Intent(this, TestBaiDuMap.class);
                startActivity(intent4);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SCAN && resultCode == 0) {
            if (data != null) {
                String result = data.getStringExtra("qrcode_result");
                btnTestScanCode.setText("扫描结果："+ result);
            }
        }
    }

    private final int REQUEST_SCAN = 110;
    /**
     * 权限检测
     */
    private void checkPermissions() {
        Acp.getInstance(this).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE
                                , Manifest.permission.READ_EXTERNAL_STORAGE
                                ,Manifest.permission.INTERNET,Manifest.permission.CAMERA
                                , Manifest.permission.NFC
                                , Manifest.permission.ACCESS_FINE_LOCATION
                                , Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        Toast.makeText(MainActivity.this, "成功获取权限", Toast.LENGTH_LONG);
                        initBanner();
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        Toast.makeText(MainActivity.this, permissions.toString() + "权限拒绝", Toast.LENGTH_LONG);
                        finish();
                    }
                });
    }


    /**
     * 测试Gson
     */
    private void testGson(){
//        String gsonstr = "{\"id\":\"001\",\"name\":null,\"number\":\"3221233\"}";
//        UserBean user = new UserBean();
//        Gson gson = new Gson();
//        UserBean userBean =gson.fromJson(gsonstr,UserBean.class);

        ParcelableBean bean = new ParcelableBean();
        bean.setRight(true);
        bean.setAge(23);
        bean.setName("张三");
        bean.setCreateTime(1112532145);
        btnGsonTest.setText(bean.toString());
        Map<String,String> map = new HashMap<>();
        map.put("name","张珊");
        map.put("age","20");
        map.put("sex","女");
        Set<Map.Entry<String,String>> mapSet = map.entrySet();
        for (Map.Entry<String,String> entry:mapSet){
            Log.e("guc----",entry.getKey()+entry.getValue());
        }
    }

    /**
     * 测试排序
     */
    private void testSort(){
        StringBuilder sb = new StringBuilder();
        sb.append("原始顺序：\n");
        for (ParcelableBean bean:userBeanList){
            sb.append(bean.getName()+ bean.getAge()+"\n");
        }
        Collections.sort(userBeanList);
        sb.append("排序后：\n");
        for (ParcelableBean bean:userBeanList){
            sb.append(bean.getName()+ bean.getAge()+"\n");
        }
        btnSortTest.setText(sb.toString());
    }

    class A {
        void fermin(int i) {
            i++;
        }
    }
}
