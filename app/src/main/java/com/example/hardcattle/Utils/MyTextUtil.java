package com.example.hardcattle.Utils;

import android.text.Html;
import android.text.TextUtils;

/**
 * Created by guc on 2018/7/5.
 * 描述：
 */
public class MyTextUtil {

    public static String getString(String text, String defautlText) {
        return TextUtils.isEmpty(text) ? defautlText : text;
    }

    public static String getString(String text) {
        return getString(text, "暂无");
    }

    /**
     * 获取Html字符串
     *
     * @param content
     * @return
     */
    public static CharSequence getHtmlContent(String content) {
        if (TextUtils.isEmpty(content)) content = "暂无";
        CharSequence charSequence;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            charSequence = Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY);
        } else {
            charSequence = Html.fromHtml(content);
        }
        return charSequence;
    }
}
