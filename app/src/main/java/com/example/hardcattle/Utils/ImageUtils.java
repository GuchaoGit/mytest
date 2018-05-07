package com.example.hardcattle.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by guc on 2018/4/28.
 * 描述：图片工具
 */
public class ImageUtils {
    public static int imgWidth = 960;
    public static int imgHeight = 1280;

    /**
     * 图片压缩
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static Bitmap revitionImageSize(String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= imgWidth)
                    && (options.outHeight >> i <= imgHeight)) {
                in = new BufferedInputStream(
                        new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                options.inScaled = true;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }

    /**
     * 将Bitmap对象 保存到本地
     * 需要读写内存卡的权限，否则会发生异常
     *
     * @param context
     * @param mBitmap
     * @param filePath 图片路径  xxx/xxx/xx.jpg
     * @return
     */
    public static String saveBitmap(Context context, Bitmap mBitmap, String filePath) {
        File filePic;
        try {
            filePic = new File(filePath);
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return filePic.getAbsolutePath();
    }

    /**
     * 压缩bitmap,并保存到指定路径
     *
     * @param options  压缩比例
     * @param bitmap
     * @param filePath
     * @return 保存的文件路径  “”表示保存失败
     */
    public static String compressBitmapToFile(int options, Bitmap bitmap, String filePath) {
        // 0-100 100为不压缩
        if (options < 10) options = 10;
        if (options > 100) options = 100;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            } else {
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(file);
            // 把压缩后的数据存放到baos中
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, fos);
            fos.flush();
            fos.close();
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 在bitmap左上角添加水印
     *
     * @param context
     * @param bitmap      原bitmap
     * @param text        水印文字
     * @param size        文字大小
     * @param color       文字颜色
     * @param paddingLeft 距左侧距离
     * @param paddingTop  距顶部距离
     * @return 添加过水印后的Bitmap
     */
    public static Bitmap drawTextToLeftTop(Context context, Bitmap bitmap, String text,
                                           int size, int color, int paddingLeft, int paddingTop) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(bitmap, text, paint,
                dp2px(context, paddingLeft),
                dp2px(context, paddingTop) + bounds.height());
    }

    /**
     * 在bitmap左下角添加文字水印
     *
     * @param context
     * @param bitmap        原bitmap
     * @param text          水印文字
     * @param size          文字大小
     * @param color         文字颜色
     * @param paddingLeft   距左侧距离
     * @param paddingBottom 距底部距离
     * @return 添加过水印后的Bitmap
     */
    public static Bitmap drawTextToLeftBottom(Context context, Bitmap bitmap, String text,
                                              int size, int color, int paddingLeft, int paddingBottom) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(bitmap, text, paint, dp2px(context, paddingLeft),
                bitmap.getHeight() - dp2px(context, paddingBottom));
    }

    //图片上绘制文字
    private static Bitmap drawTextToBitmap(Bitmap bitmap, String text,
                                           Paint paint, int paddingLeft, int paddingTop) {
        paint.setDither(true); // 获取跟清晰的图像采样
        paint.setFilterBitmap(true);// 过滤一些

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap bitmapNew = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmapNew);
        //在画布 0，0坐标上开始绘制原始图片
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawText(text, paddingLeft, paddingTop, paint);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        bitmap.recycle();
        return bitmapNew;
    }

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
