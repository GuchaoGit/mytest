package com.example.hardcattle.myapplication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by guc on 2018/7/12.
 * 描述：测试通知
 */
public class TestNotice extends Activity {
    public static final String CHANNEL_ID = "notice";
    public static final String CHANNEL_ID_SUB = "notice_sub";
    @BindView(R.id.btn_notice1)
    Button mBtnNotice1;
    @BindView(R.id.btn_notice2)
    Button mBtnNotice2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_test_notice);
        ButterKnife.bind(this);
        initNoticeChannel();

    }

    @OnClick({R.id.btn_notice1, R.id.btn_notice2, R.id.btn_clear_notice, R.id.btn_subscribe})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_notice1:
                sendNotice(1, "收到一条通知消息", "今天中午吃什么？");
                break;
            case R.id.btn_notice2:
                sendNotice(2, "收到另一条通知消息", "吃面");
                break;
            case R.id.btn_subscribe:
                sendSubscribe(3, "收到另一条订阅消息", "显示两条未读", 2);
                break;
            case R.id.btn_clear_notice:
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.cancelAll();
                break;
        }
    }

    private void initNoticeChannel() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            String channelName;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            channelName = "通知消息";
            createNotificationChannel(CHANNEL_ID, channelName, importance);
            channelName = "订阅消息";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(CHANNEL_ID_SUB, channelName, importance);
        }
    }

    /**
     * 发送不可清除的通知
     *
     * @param id
     * @param title
     * @param text
     */
    private void sendNotice(int id, String title, String text) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(text)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                .setContentIntent(getPendingIntent())
//                .setAutoCancel(true)
                .build();
        notification.flags = Notification.FLAG_NO_CLEAR;
        manager.notify(id, notification);
    }

    private void sendSubscribe(int id, String title, String text, int number) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(text)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
//                .setContentIntent(getPendingIntent())
                .setNumber(number)
                .setAutoCancel(true)
                .build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        manager.notify(id, notification);
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClass(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        Context mContext = getApplicationContext();
        PendingIntent contextIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
        return contextIntent;
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.setShowBadge(true);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

}
