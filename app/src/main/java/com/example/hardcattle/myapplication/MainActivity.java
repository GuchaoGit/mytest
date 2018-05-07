package com.example.hardcattle.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hardcattle.bean.ParcelableBean;
import com.example.hardcattle.bean.UserBean;
import com.example.hardcattle.widget.autocompleteview.AutoCompleteView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private AutoCompleteTextView tvShow;
    private AutoCompleteView autoCompleteView;

    private TextView tvSlideMenu, tvAppName ,tvImageTest,testRefreshLayout;
    private DrawerLayout mDrawLayout;
    private NavigationView mNaviView;
    private Button btnGsonTest,btnSortTest,btnScrollTableTest;
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

    private void findView() {
        tvShow = findViewById(R.id.auto_complete_tv);
        autoCompleteView = findViewById(R.id.acv);
        tvSlideMenu = findViewById(R.id.tv_slide_menu);

        mDrawLayout = findViewById(R.id.drawer_layout);
        mNaviView = findViewById(R.id.nav_view);

        View view = mNaviView.getHeaderView(0);//获取侧边栏View
        tvAppName = view.findViewById(R.id.tv_app_name);
        tvImageTest = view.findViewById(R.id.tv_imagepicker_test);
        testRefreshLayout = view.findViewById(R.id.tv_refresh_layout_test);
        btnGsonTest = findViewById(R.id.btn_gson_test);
        btnSortTest = findViewById(R.id.btn_sort_test);
        btnScrollTableTest = findViewById(R.id.btn_scroll_test);
        autoCompleteView.setData("zylb");

    }

    private void initListener() {
        tvSlideMenu.setOnClickListener(this);
        tvAppName.setOnClickListener(this);
        tvImageTest.setOnClickListener(this);
        btnGsonTest.setOnClickListener(this);
        testRefreshLayout.setOnClickListener(this);
        btnSortTest.setOnClickListener(this);
        btnScrollTableTest.setOnClickListener(this);
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
        }
    }
    /**
     * 权限检测
     */
    private void checkPermissions() {
        Acp.getInstance(this).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE
                                , Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        Toast.makeText(MainActivity.this, "成功获取权限", Toast.LENGTH_LONG);

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
//        UserBean user = new UserBean();
//        user.setId("001");
//        user.setName("Tomi");
//        user.setNumber("3221233");
//        Gson gson = new Gson();
//        String userG=gson.toJson(user,UserBean.class);
//        btnGsonTest.setText(userG);
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
}
