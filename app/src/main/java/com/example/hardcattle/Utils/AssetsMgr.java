package com.example.hardcattle.Utils;

import android.content.Context;

import com.example.hardcattle.bean.BeanKeyValue;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guc on 2018/5/3.
 * 描述：
 */
public class AssetsMgr {
    static Gson gson=new Gson();
    /**
     * 根据文件名获取Assets数据
     * @param context
     * @param assetName assets下文件名
     * @return List<BeanKeyValue>
     */
    public static List<BeanKeyValue> getKeyValuePairsList(Context context, String assetName){
        String jsonStr = getAssetsJson(context, assetName);
        List<BeanKeyValue> list = new ArrayList<BeanKeyValue>();
        Type type=new TypeToken<ArrayList<BeanKeyValue>>(){}.getType();
        list=gson.fromJson(jsonStr,type);
        return list;
    }

    private static String getAssetsJson(Context mCxt,String assetsName) {
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
