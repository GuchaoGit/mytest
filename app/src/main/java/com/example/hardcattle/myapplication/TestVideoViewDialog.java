package com.example.hardcattle.myapplication;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.blankj.utilcode.util.ToastUtils;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.lang.reflect.Method;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestVideoViewDialog extends AppCompatActivity {

    @BindView(R.id.btn_play)
    Button mBtnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_video_view_dialog);
        ButterKnife.bind(this);
//        checkPermissions();
    }

    @OnClick(R.id.btn_play)
    public void onViewClicked() {
        if (hasPermissionFloatWin(this)) {
            createVideoDialg();
        } else {
            ToastUtils.showShort("没有权限");
        }

    }

    /**
     * 权限检测
     */
    private void checkPermissions() {
        Acp.getInstance(this).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.SYSTEM_ALERT_WINDOW)
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        Toast.makeText(TestVideoViewDialog.this, "成功获取权限", Toast.LENGTH_LONG);
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        finish();
                    }
                });
    }

    private void createVideoDialg() {
        LayoutInflater inflater = LayoutInflater

                .from(this);
        final View layout = inflater.inflate(R.layout.dialog_videoview,
                null);
        VideoView mVideoView = (VideoView) layout
                .findViewById(R.id.sh_myvideoview);
        mVideoView.setZOrderOnTop(true);
        mVideoView.setVideoPath("/mnt/sdcard/DCIM/Camera/VID_20181002_162938.mp4");
        //
        final WindowManager wm = (WindowManager) getApplicationContext()
                .getSystemService(WINDOW_SERVICE);
        //
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        mVideoView.start();

        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
//        wmParams.type = 2002; // 2002表示系统级窗口
        wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        wmParams.format = PixelFormat.TRANSPARENT;

        wmParams.flags = 40;
        wmParams.width = width;
        wmParams.height = height;
        wm.addView(layout, wmParams);// 创建View

        // 播放出错回调
        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                wm.removeView(layout);
                return false;
            }
        });

        // 播放完成回调
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                wm.removeView(layout);
            }
        });
    }

    /**
     * 判断是否开启浮窗权限,api未公开，使用反射调用
     *
     * @return
     */
    private boolean hasPermissionFloatWin(Context context) {

        if (android.os.Build.VERSION.SDK_INT < 19) {
            return true;
        }
        try {
            AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            Class c = appOps.getClass();
            Class[] cArg = new Class[3];
            cArg[0] = int.class;
            cArg[1] = int.class;
            cArg[2] = String.class;
            Method lMethod = c.getDeclaredMethod("checkOp", cArg);
            //24是浮窗权限的标记
            return (AppOpsManager.MODE_ALLOWED == (Integer) lMethod.invoke(appOps, 24, Binder.getCallingUid(), context.getPackageName()));

        } catch (Exception e) {
            return false;
        }
    }

}
