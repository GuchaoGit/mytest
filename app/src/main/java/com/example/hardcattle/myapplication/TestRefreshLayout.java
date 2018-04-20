package com.example.hardcattle.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.example.hardcattle.widget.ScrollChildSwipeRefreshLayout;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

/**
 * Created by guc on 2018/4/19.
 * 描述：测试下拉刷新布局
 */
public class TestRefreshLayout extends AppCompatActivity {

    private ScrollChildSwipeRefreshLayout mRefreshLayout;
    private LinearLayout mContentLayout;

    private TwinklingRefreshLayout mRefreshLayoutTwinkling;
    private boolean isRefresh = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_test_refresh_layout);
        initView();
    }
    private void initView(){
        mRefreshLayout = findViewById(R.id.refresh_layout);
        mContentLayout = findViewById(R.id.ll_refresh_content);
        mRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
        );
        mRefreshLayout.setScrollUpChild(mContentLayout);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });

        mRefreshLayoutTwinkling = findViewById(R.id.refresh_layout_twinkling);
        ProgressLayout header = new ProgressLayout(this);
        header.setColorSchemeColors(R.color.colorAccent, R.color.colorPrimaryDark, R.color.colorPrimary);
        mRefreshLayoutTwinkling.setHeaderView(header);
        mRefreshLayoutTwinkling.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                initData();
                isRefresh = true;
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                isRefresh = false;
                initData();
            }
        });

    }
    private void initData(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
                if (isRefresh){
                    mRefreshLayoutTwinkling.finishRefreshing();
                }else {
                    mRefreshLayoutTwinkling.finishLoadmore();
                }
            }
        },3000);
    }
}
