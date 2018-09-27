package com.guc.ui.dialogchooseinfo;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DialogChooseInfoUtils {

    public static void showDialogChooseInfo(Context context, String value, String title, String assetsName, FragmentManager fragmentManager, DialogChooseInfo.OnChooseInfoClickListener listener) {
        DialogChooseInfo dialogChooseInfo = setDialogChooseInfo(context, value, title, assetsName, new DialogChooseInfo());
        dialogChooseInfo.show(fragmentManager, "dialog");
        dialogChooseInfo.setOnChooseInfoClickListener(listener);
    }

    public static void showDialogChooseInfo(String value, String title, List<BeanChooseOption> datas, FragmentManager fragmentManager, DialogChooseInfo.OnChooseInfoClickListener listener) {
        DialogChooseInfo dialogChooseInfo = setDialogChooseInfo(value, title, datas, new DialogChooseInfo());
        dialogChooseInfo.show(fragmentManager, "dialog");
        dialogChooseInfo.setOnChooseInfoClickListener(listener);
    }

    private static DialogChooseInfo setDialogChooseInfo(Context context, String title, String assetsName, DialogChooseInfo dialogChooseInfo) {
        return setDialogChooseInfo(context, null, title, assetsName, dialogChooseInfo);
    }

    private static DialogChooseInfo setDialogChooseInfo(Context context, String value, String title, String assetsName, DialogChooseInfo dialogChooseInfo) {
        if (!TextUtils.isEmpty(assetsName) && dialogChooseInfo != null) {
            dialogChooseInfo.setSelectValue(value);
            BeanChooseInfo beanChooseInfo = new BeanChooseInfo();
            List<BeanChooseOption> beanChooseOptionList = getBeanChooseOptionList(context, assetsName);
            beanChooseInfo.title = title;
            beanChooseInfo.beanChooseOptionList = beanChooseOptionList;
            Bundle bundle = new Bundle();
            bundle.putParcelable(DialogChooseInfo.DATA, beanChooseInfo);
            dialogChooseInfo.setArguments(bundle);
        } else {
            return null;
        }
        return dialogChooseInfo;
    }

    private static DialogChooseInfo setDialogChooseInfo(String value, String title, List<BeanChooseOption> datas, DialogChooseInfo dialogChooseInfo) {
        if (datas == null && dialogChooseInfo != null) {
            dialogChooseInfo.setSelectValue(value);
            BeanChooseInfo beanChooseInfo = new BeanChooseInfo();
            beanChooseInfo.title = title;
            beanChooseInfo.beanChooseOptionList = datas;
            Bundle bundle = new Bundle();
            bundle.putParcelable(DialogChooseInfo.DATA, beanChooseInfo);
            dialogChooseInfo.setArguments(bundle);
        } else {
            return null;
        }
        return dialogChooseInfo;
    }

    private static List<BeanChooseOption> getBeanChooseOptionList(Context context, String assetName) {
        Gson gson = new Gson();
        String jsonStr = getAssetsJson(context, assetName);
        List<BeanChooseOption> list = new ArrayList<>();
        Type type = new TypeToken<ArrayList<BeanChooseOption>>() {
        }.getType();
        list = gson.fromJson(jsonStr, type);
        return list;
    }

    private static String getAssetsJson(Context mCxt, String assetsName) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    mCxt.getAssets().open(assetsName + ".json")));
            String line;
            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
