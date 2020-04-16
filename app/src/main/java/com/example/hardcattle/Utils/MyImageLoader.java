package com.example.hardcattle.Utils;

import android.util.Log;
import android.util.LruCache;

/**
 * Created by guc on 2019/12/25.
 * 描述：图片缓存
 */
public class MyImageLoader {
    private static final String TAG = "MyImageLoader";
    private LruCache<String, byte[]> mLruCache;

    public MyImageLoader() {
        //设置最大缓存空间为运行时内存的 1/8
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        Log.e(TAG, "MyImageLoader: max:" + maxMemory + "cacheSize:" + cacheSize);
        mLruCache = new LruCache<String, byte[]>(cacheSize) {
            @Override
            protected int sizeOf(String key, byte[] value) {
                //计算一个元素的缓存大小
                return value.length;
            }
        };
    }

    /**
     * 添加图片到 LruCache
     *
     * @param key
     * @param bitmap
     */
    public void addByteData(String key, byte[] bitmap) {
        if (getByteData(key) == null) {
            mLruCache.put(key, bitmap);
        }
    }

    /**
     * 从缓存中获取图片
     *
     * @param key
     * @return
     */
    public byte[] getByteData(String key) {
        return mLruCache.get(key);
    }

    /**
     * 从缓存中删除指定的 ByteData
     *
     * @param key
     */
    public void removeByteDataFromMemory(String key) {
        mLruCache.remove(key);
    }
}
