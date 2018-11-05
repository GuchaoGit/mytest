package com.example.hardcattle.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.hardcattle.myapplication.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by guc on 2018/11/2.
 * 描述：
 */
public class SignNameView extends View {

    private Context mCxt;
    private Paint mPaint;
    private Path mPath;
    /**
     * 签名画笔
     */
    private Canvas cacheCanvas;
    /**
     * 签名画布
     */
    private Bitmap cachebBitmap;
    private int mBackColor;
    private int mLineColor;
    private int mLineWidth;
    private boolean hasSign = false;

    public SignNameView(Context context) {
        super(context);
        mCxt = context;
        init();
    }

    public SignNameView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignNameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCxt = context;
        initAttrs(attrs, defStyleAttr);
        init();
    }

    private void initAttrs(AttributeSet attrs, int defStyleAttr) {
        TypedArray array = mCxt.getTheme().obtainStyledAttributes(
                attrs, R.styleable.SignNameView, defStyleAttr, 0);
        mLineWidth = array.getDimensionPixelSize(R.styleable.SignNameView_lineWith, dp2px(1));
        mBackColor = array.getColor(R.styleable.SignNameView_backColor, Color.WHITE);
        mLineColor = array.getColor(R.styleable.SignNameView_lineColor, Color.BLACK);
        array.recycle();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(mLineColor);
        mPaint.setStrokeWidth(mLineWidth);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //创建跟view一样大的bitmap，用来保存签名
        cachebBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas(cachebBitmap);
        cacheCanvas.drawColor(mBackColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(event);
                break;
            case MotionEvent.ACTION_UP:
                hasSign = true;
                //将路径画到bitmap中，即一次笔画完成才去更新bitmap，而手势轨迹是实时显示在画板上的。
                cacheCanvas.drawPath(mPath, mPaint);
                break;
        }
        // 更新绘制
        invalidate();
        return true;
    }


    // 手指点下屏幕时调用
    private void touchDown(MotionEvent event) {
        // 重置绘制路线
        float x = event.getX();
        float y = event.getY();
        // mPath绘制的绘制起点
        mPath.moveTo(x, y);
    }

    /**
     * 手指移动
     *
     * @param event
     */
    private void touchMove(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        mPath.lineTo(x, y);

    }

    /**
     * 清除所有
     */
    public void clearAll() {
        mPath.reset();
        hasSign = false;
        if (cacheCanvas != null) {
            cacheCanvas.drawColor(mBackColor, PorterDuff.Mode.CLEAR);
        }
        invalidate();
    }

    /**
     * 清除上一条线
     */
    public void clearLastLine() {
    }

    /**
     * 获取画板的bitmap
     *
     * @return
     */
    public Bitmap getBitMap() {
        return cachebBitmap;
    }

    /**
     * 保存画板
     *
     * @param path 保存到路径
     */
    public void save(String path) throws IOException {
        save(path, false, 0);
    }

    public boolean hasSign() {
        return hasSign;
    }

    /**
     * 保存画板
     *
     * @param path       保存到路径
     * @param clearBlank 是否清除边缘空白区域
     * @param blank      要保留的边缘空白距离
     */
    private void save(String path, boolean clearBlank, int blank) throws IOException {
        Bitmap bitmap = cachebBitmap;
        File file = new File(path);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
        fos.flush();
        fos.close();
    }

    /**
     * convert px to its equivalent dp
     * <p>
     * 将px转换为与之相等的dp
     */
    private int px2dp(float pxValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    private int dp2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
