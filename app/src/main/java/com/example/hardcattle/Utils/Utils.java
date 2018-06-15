package com.example.hardcattle.Utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.SimpleArrayMap;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Author: guc
 * Time: 2018/3/20 下午3:38
 * Description:
 */

public class Utils {
    /********************验证相关***********************/
    //region        验证相关

    /**
     * 验证手机号（简单验证）
     *
     * @param input
     * @return
     */
    public static boolean isMobileSimple(final CharSequence input) {
        return isMatch(Constant.REGEX_MOBILE_SIMPLE, input);
    }

    /**
     * 验证手机号（精确的）
     *
     * @param input
     * @return
     */
    public static boolean isMobileExact(final CharSequence input) {
        return isMatch(Constant.REGEX_MOBILE_EXACT, input);
    }

    /**
     * 验证固话
     *
     * @param input
     * @return
     */
    public static boolean isTel(final CharSequence input) {
        return isMatch(Constant.REGEX_TEL, input);
    }

    /**
     * 验证身份证号码 15 位
     *
     * @param input
     * @return
     */
    public static boolean isIDCard15(final CharSequence input) {
        return isMatch(Constant.REGEX_ID_CARD15, input);
    }

    /**
     * 验证身份证号码 18 位
     *
     * @param input
     * @return
     */
    public static boolean isIDCard18(final CharSequence input) {
        return isMatch(Constant.REGEX_ID_CARD18, input);
    }

    /**
     * 验证邮箱
     *
     * @param input
     * @return
     */
    public static boolean isEmail(final CharSequence input) {
        return isMatch(Constant.REGEX_EMAIL, input);
    }

    /**
     * 验证汉字
     *
     * @param input
     * @return
     */
    public static boolean isZh(final CharSequence input) {
        return isMatch(Constant.REGEX_ZH, input);
    }

    /**
     * 验证 yyyy-MM-dd 格式的日期校验，已考虑平闰年
     *
     * @param input
     * @return
     */
    public static boolean isDate(final CharSequence input) {
        return isMatch(Constant.REGEX_DATE, input);
    }

    /**
     * 验证 IP 地址
     *
     * @param input
     * @return
     */
    public static boolean isIP(final CharSequence input) {
        return isMatch(Constant.REGEX_IP, input);
    }

    /**
     * 判断是否匹配正则
     *
     * @param regex 正则表达式
     * @param input 待匹配字符串
     * @return true：匹配，false：不匹配
     */
    public static boolean isMatch(final String regex, final CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }


    /**
     * 判断对象是否为空
     *
     * @param obj 对象
     * @return true：空对象 false：非空
     */
    public static boolean isEmpty(final Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof CharSequence && obj.toString().length() == 0) {
            return true;
        }
        if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        }
        if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof SimpleArrayMap && ((SimpleArrayMap) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof SparseArray && ((SparseArray) obj).size() == 0) {
            return true;
        }
        if (obj instanceof SparseBooleanArray && ((SparseBooleanArray) obj).size() == 0) {
            return true;
        }
        if (obj instanceof SparseIntArray && ((SparseIntArray) obj).size() == 0) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (obj instanceof SparseLongArray && ((SparseLongArray) obj).size() == 0) {
                return true;
            }
        }
        if (obj instanceof LongSparseArray && ((LongSparseArray) obj).size() == 0) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (obj instanceof android.util.LongSparseArray
                    && ((android.util.LongSparseArray) obj).size() == 0) {
                return true;
            }
        }
        return false;
    }

    //endregion

    /********************屏幕相关***********************/
    //region 屏幕相关

    /**
     * 获取屏幕宽度（单位:px）
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return context.getResources().getDisplayMetrics().widthPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    /**
     * 获取屏幕高度（单位:px）
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return context.getResources().getDisplayMetrics().heightPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    /**
     * 设置全屏显示
     *
     * @param activity
     */
    public static void setFullScreen(@NonNull final Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 获取屏幕尺寸与密度.
     *
     * @param context the context
     * @return mDisplayMetrics
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        Resources mResources;
        if (context == null) {
            mResources = Resources.getSystem();

        } else {
            mResources = context.getResources();
        }
        // DisplayMetrics{density=1.5, width=480, height=854, scaledDensity=1.5,
        // xdpi=160.421, ydpi=159.497}
        // DisplayMetrics{density=2.0, width=720, height=1280,
        // scaledDensity=2.0, xdpi=160.42105, ydpi=160.15764}
        DisplayMetrics mDisplayMetrics = mResources.getDisplayMetrics();
        return mDisplayMetrics;
    }

    /**
     * 设置屏幕为横屏
     *
     * @param activity
     */
    public static void setLandscape(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 设置屏幕为竖屏
     *
     * @param activity
     */
    public static void setPortrait(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 判断是否横屏
     *
     * @param context
     * @return true:横屏   false:竖屏
     */
    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 截屏
     *
     * @param activity
     * @return
     */
    public static Bitmap screenShot(@NonNull final Activity activity) {
        return screenShot(activity, false);
    }

    /**
     * 返回当前界面的Bitmap对象
     *
     * @param activity          The activity.
     * @param isDeleteStatusBar true:去掉状态栏，，false:包括状态栏.
     * @return 界面的Bitmap对象
     */
    public static Bitmap screenShot(@NonNull final Activity activity, boolean isDeleteStatusBar) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
        Bitmap bmp = decorView.getDrawingCache();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret;
        if (isDeleteStatusBar) {
            Resources resources = activity.getResources();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = resources.getDimensionPixelSize(resourceId);
            ret = Bitmap.createBitmap(
                    bmp,
                    0,
                    statusBarHeight,
                    dm.widthPixels,
                    dm.heightPixels - statusBarHeight
            );
        } else {
            ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
        }
        decorView.destroyDrawingCache();
        return ret;
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
     * bitmap 转为byte数组
     *
     * @param bitmap
     * @param format
     * @return
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap, final Bitmap.CompressFormat format) {
        if (bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, 100, baos);
        return baos.toByteArray();
    }

    /**
     * byte数组转为bitmap对象
     *
     * @param bytes
     * @return
     */
    public static Bitmap bytes2Bitmap(final byte[] bytes) {
        return (bytes == null || bytes.length == 0)
                ? null
                : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 将信息写入文件
     *
     * @param filePath 文件路径
     * @param content  待保存内容
     * @param append   是否追加
     * @return true：保存成功  false:保存失败
     */
    public static boolean writeMsg2File(String filePath, String content, boolean append) {
        File file = new File(filePath);
        FileOutputStream fos = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file, append);
            fos.write(content.getBytes());
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            fos = null;
        }
        return true;
    }

    /**
     * 压缩图片
     *
     * @param bitmap
     * @param filePath
     */
    public static void compressBitmapToFile(Bitmap bitmap, String filePath) {
        // 0-100 100为不压缩
        int options = 25;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//endregion
    /***********************APP相关*********************/
    //region APP相关

    /**
     * 获取应用的版本号
     *
     * @param context
     * @return 当前版本号
     */
    public static String getAppVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "1.0";
        }
    }

    /**
     * 获取应用版本码
     *
     * @param context
     * @return 当前版本码
     */
    public static int getAppVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            int versionCode = info.versionCode;
            return versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 检测sdcard是否可用
     *
     * @return true为可用，否则为不可用
     */
    public static boolean sdCardIsAvailable() {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED))
            return false;
        return true;
    }


    /**
     * 判断文件是否存在
     *
     * @param file
     * @return
     */
    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }

    /**
     * 安装apk
     * 目标SDK版本APIs大于25时需添加 <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" /> 权限
     *
     * @param context
     * @param file      apk文件
     * @param authority 目标SDK版本APIs大于23时需要定义一个FileProvider
     */
    public static void installApk(Context context, File file, String authority) {
        if (!isFileExists(file)) return;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        String type = "application/vnd.android.package-archive";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            data = Uri.fromFile(file);
        } else {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            data = FileProvider.getUriForFile(context, authority, file);
        }
        intent.setDataAndType(data, type);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 用来判断服务是否运行.
     *
     * @param context   the context
     * @param className 判断的服务名字 eg:"com.xxx.xx..XXXService"
     * @return true 在运行, false 不在运行
     */
    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> servicesList = activityManager
                .getRunningServices(Integer.MAX_VALUE);
        Iterator<ActivityManager.RunningServiceInfo> l = servicesList.iterator();
        while (l.hasNext()) {
            ActivityManager.RunningServiceInfo si = (ActivityManager.RunningServiceInfo) l.next();
            if (className.equals(si.service.getClassName())) {
                isRunning = true;
            }
        }
        return isRunning;
    }

    /**
     * 停止服务.
     *
     * @param context   the context
     * @param className 服务的类名 eg:"XXXService"
     * @return true 停止成功  ，false 停止失败
     */
    public static boolean stopRunningService(Context context, String className) {
        Intent intent_service = null;
        boolean ret = false;
        try {
            intent_service = new Intent(context, Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (intent_service != null) {
            ret = context.stopService(intent_service);
        }
        return ret;
    }

    /**
     * 判断网络是否连接.
     *
     * @param context the context
     * @return boolean
     * true:网络可用,false：网络未连接
     */
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 判断当前网络是否是移动数据网络.
     *
     * @param context the context
     * @return boolean
     * true:使用移动网络，false:未使用
     */
    public static boolean isMobileNet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    //endregion

    /**************************尺寸相关************/
    //region 尺寸相关

    /**
     * dp转为px.
     *
     * @param context
     * @param dpValue dp尺寸.
     * @return int  px尺寸
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转为dp.
     *
     * @param pxValue px尺寸.
     * @return int  dp尺寸
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转为px.
     *
     * @param spValue sp尺寸.
     * @return int    px尺寸
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转为sp
     *
     * @param pxValue px尺寸.
     * @return int    sp尺寸
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    //endregion
    /***************时间格式转换相关****************/
    //region    时间格式转换相关

    /**
     * 日期对象转为指定格式的字符串
     *
     * @param date    日期对象
     * @param pattern 时间格式：eg:yyyy-MM-dd HH:mm:ss
     * @return 时间字符串
     */
    public static String date2Str(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static boolean isBeforeDate(Date date) {
        Date curDate = new Date();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        return isBeforeTime(date);
    }

    /**
     * 日期是否在当前之前
     *
     * @param date
     * @return
     */
    public static boolean isBeforeTime(Date date) {
        Date curDate = new Date();
        if (date.getTime() <= curDate.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 时间字符串转为Date
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date str2Date(String dateStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            date = new Date();
        }
        return date;
    }

    /**
     * 时间毫秒转为指定格式的字符串
     *
     * @param time    时间毫秒
     * @param pattern 时间格式：eg:yyyy-MM-dd HH:mm:ss
     * @return 时间字符串
     */
    public static String timeMillis2Str(long time, String pattern) {
        try {
            Date date = new Date(time);
            return date2Str(date, pattern);
        } catch (Exception e) {
            return time + "";
        }

    }

    /**
     * 时间毫秒转为指定格式的字符串
     *
     * @param timeMillis 时间毫秒
     * @param pattern    时间格式：eg:yyyy-MM-dd HH:mm:ss
     * @return 时间字符串
     * 若有异常， 返回原字符串
     */
    public static String timeMillis2Str(String timeMillis, String pattern) {
        try {
            Date date = new Date(Long.parseLong(timeMillis));
            return date2Str(date, pattern);
        } catch (Exception e) {
            return TextUtils.isEmpty(timeMillis) ? "" : timeMillis;
        }

    }

    /**
     * 时间格式转换
     *
     * @param originPattern 原时间格式
     * @param destnPattern  目标时间格式
     * @param timeStr       时间字符串
     * @return
     */
    public static String timeTranslate(String originPattern, String destnPattern, String timeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(originPattern);
        Date date;
        try {
            date = sdf.parse(timeStr);
        } catch (ParseException e) {
            return timeStr;
        }
        return date2Str(date, destnPattern);
    }

    /**
     * 选择日期or 时间
     *
     * @param mContext
     * @param tvDate
     * @param pattern
     */
    public static void showDateSelectDialog(Context mContext, final TextView tvDate, final String pattern) {
        final SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date curDate;
        if (TextUtils.isEmpty(tvDate.getText().toString().trim())) {
            curDate = new Date();
        } else {
            curDate = str2Date(tvDate.getText().toString().trim(), pattern);
        }
        DatePickDialog slDialog = new DatePickDialog(mContext);
        slDialog.setYearLimt(5);
        slDialog.setTitle("选择时间");
        slDialog.setMessageFormat(pattern);
        switch (pattern) {
            case "yyyy-MM-dd":
                slDialog.setType(com.codbking.widget.bean.DateType.TYPE_YMD);
                break;
            case "yyyy-MM-dd HH:mm":
                slDialog.setType(com.codbking.widget.bean.DateType.TYPE_YMDHM);
                break;
            case "HH:mm":
                slDialog.setType(DateType.TYPE_HM);
                break;
        }
        slDialog.setStartDate(curDate);
        slDialog.setOnChangeLisener(new OnChangeLisener() {
            @Override
            public void onChanged(Date date) {

            }
        });
        slDialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                switch (pattern) {
                    case "yyyy-MM-dd":
                        if (isBeforeDate(date)) {
                            tvDate.setText(format.format(date));
                        } else {
                            ToastUtils.showShort("不能选择当前时间之后的时间");
                        }
                        break;
                    case "yyyy-MM-dd HH:mm":
                        if (isBeforeTime(date)) {
                            tvDate.setText(format.format(date));
                        } else {
                            ToastUtils.showShort("不能选择当前时间之后的时间");
                        }
                        break;
                }

            }
        });
        slDialog.show();
    }

    /**
     * 选择日期  格式为：yyyy-MM-dd
     *
     * @param mContext
     * @param tvDate
     */
    public static void showDateSelectDialog(Context mContext, final TextView tvDate) {
        showDateSelectDialog(mContext, tvDate, "yyyy-MM-dd");
    }
    //endregion


    /*****************文件相关**********************/
    //region    文件相关

    /**
     * 判断文件是否是文件夹
     *
     * @param file
     * @return true：文件夹
     */
    public static boolean isDir(final File file) {
        return file != null && file.exists() && file.isDirectory();
    }

    /**
     * 通过文件路径  获取文件
     *
     * @param filePath
     * @return File对象
     */
    public static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 删除文件夹
     *
     * @param dirPath
     * @return true:删除成功  false:删除失败
     */
    public static boolean deleteDir(final String dirPath) {
        return deleteDir(getFileByPath(dirPath));
    }

    /**
     * 删除文件夹
     *
     * @param dir
     * @return true：删除成功  false:删除失败
     */
    public static boolean deleteDir(final File dir) {
        if (dir == null) return false;
        // dir doesn't exist then return true
        if (!dir.exists()) return true;
        // dir isn't a directory then return false
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return dir.delete();
    }

    //endregion

    /*****************其他**********************/
    //region    其他
    public static String getMimeType(String filePath) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        String mime = "text/plain";
        if (filePath != null) {
            try {
                mmr.setDataSource(filePath);
                mime = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
            } catch (IllegalStateException e) {
                return mime;
            } catch (IllegalArgumentException e) {
                return mime;
            } catch (RuntimeException e) {
                return mime;
            }
        }
        return mime;
    }

    /**
     * 获取泛型类型
     *
     * @param genType
     * @return
     */
    public static ParameterizedType getParameterizedType(Class genType) {
        if (genType == null) {
            return null;
        }
        if (genType.getGenericSuperclass() instanceof ParameterizedType) {
            return (ParameterizedType) genType.getGenericSuperclass();
        } else if ((genType = genType.getSuperclass()) != null && genType != Object.class) {
            return getParameterizedType(genType);
        } else {
            return null;
        }
    }

    /**
     * 获取类中指定 泛型的 类型
     *
     * @param clz eg: MyTest<T>
     * @return T 的泛型
     */
    public static Type getType(Class<?> clz) {
        Type type = null;
        ParameterizedType genType = Utils.getParameterizedType(clz);
        if (genType != null) {
            Type[] params = genType.getActualTypeArguments();
            if (params != null && params.length > 0)
                type = params[0];
        }
        return type;
    }

    /**
     * 设置activity 背景变灰
     *
     * @param activity
     * @param alpha
     */
    public static void setWindowAlpha(Activity activity, float alpha) {
        if (alpha < 0 || alpha > 1) return;
        WindowManager.LayoutParams windowLP = activity.getWindow().getAttributes();
        windowLP.alpha = alpha;
        if (alpha == 1) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        activity.getWindow().setAttributes(windowLP);
    }

    /**
     * 往activity上添加水印布局
     *
     * @param activity 待添加水印activity
     * @param layoutId 水印布局Id
     */
    public static boolean addWatermarkView(final Activity activity, int layoutId) {
        final ViewGroup rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        View framView = LayoutInflater.from(activity).inflate(layoutId, null);
        rootView.addView(framView);
        return true;
    }
    //endregion
}
