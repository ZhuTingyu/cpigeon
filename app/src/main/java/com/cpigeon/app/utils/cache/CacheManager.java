package com.cpigeon.app.utils.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

/**
 * Created by chenshuai on 2017/4/23.
 * <p>
 * 使用方法
 * 初始化
 * <p>
 * CacheManager.setCacheModel(CacheManager.ALL_ALLOW);//缓存模式
 * CacheManager.setMemaryCacheTime(30 * 1000);//内存缓存时间
 * CacheManager.setDiskCacheTime(3 * 60 * 1000);//磁盘缓存时间
 * CacheManager.init(this);//初始化
 * <p>
 * 保存缓存
 * CacheManager.put(key, object, 1000);
 * 获取缓存
 * CacheManager.getCache(cacheKey);
 */

public class CacheManager {
    /**
     * 只使用内存缓存(LruCache)
     */
    public static final int ONLY_LRU = 1;
    /**
     * 只使用硬盘缓存(DiskLruCache)
     */
    public static final int ONLY_DISKLRU = 2;
    /**
     * 同时使用内存缓存(LruCache)与硬盘缓存(DiskLruCache)
     */
    public static final int ALL_ALLOW = 0;

    /**
     * 设置类型为硬盘缓存——用于取硬盘缓存大小
     */
    public static final int DISKSIZE = 0;
    /**
     * 设置类型为内存缓存——用于取内存缓存大小
     */
    public static final int MEMORYSIZE = 1;

    //设置硬盘缓存的最大值，单位为M
    private static int maxSizeForDiskLruCache = 0;
    //设置内存缓存的最大值，单位为M
    private static int maxMemoryForLruCache = 0;
    //设置自定义的硬盘缓存文件夹名称
    private static String dirNameForDiskLruCache = "";
    //记录硬盘缓存与内存缓存起效标志
    private static int model = 0;
    //硬盘缓存管理类
    private static DiskLruCacheManager diskLruCacheManager;
    //内存缓存管理类
    private static LruCacheManager lruCacheManager;
    private static Context ct;

    //内存缓存过期时间
    private static long mMemaryCacheTime = 30;
    //磁盘缓存过期时间
    private static long mDiskCacheTime = -1;

    public static long getMemaryCacheTime() {
        return mMemaryCacheTime;
    }

    public static void setMemaryCacheTime(long millis) {
        mMemaryCacheTime = millis;
        if (lruCacheManager != null) lruCacheManager.setDefaultCacheTime(mMemaryCacheTime);
    }

    public static long getDiskCacheTime() {
        return mDiskCacheTime;
    }

    public static void setDiskCacheTime(long millis) {
        mDiskCacheTime = millis;
        if (diskLruCacheManager != null) diskLruCacheManager.setDefaultCacheTime(mDiskCacheTime);
    }

    /**
     * 初始化缓存管理
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        ct = context.getApplicationContext();
        init_();
    }

    //根据传入的标志，初始化内存缓存以及硬盘缓存，默认开启是同时使用
    private static void init_() {
        switch (model) {
            case ALL_ALLOW:
                initDiskLruCacheManager();
                initLruCacheManager();
                break;
            case ONLY_LRU:
                initLruCacheManager();
                break;
            case ONLY_DISKLRU:
                initDiskLruCacheManager();
                break;
            default:
                break;
        }
    }

    //初始化内存缓存管理
    private static void initLruCacheManager() {
        if (maxMemoryForLruCache > 0) {
            lruCacheManager = new LruCacheManager(maxMemoryForLruCache);
        } else {
            lruCacheManager = new LruCacheManager();
        }
        lruCacheManager.setDefaultCacheTime(mMemaryCacheTime);
    }

    //初始化硬盘缓存管理
    private static void initDiskLruCacheManager() {
        if (maxSizeForDiskLruCache > 0 && !TextUtils.isEmpty(dirNameForDiskLruCache)) {
            diskLruCacheManager = new DiskLruCacheManager(ct, dirNameForDiskLruCache, maxSizeForDiskLruCache * 1024 * 1024);
        } else if (maxSizeForDiskLruCache > 0) {
            diskLruCacheManager = new DiskLruCacheManager(ct, maxSizeForDiskLruCache * 1024 * 1024);
        } else if (!TextUtils.isEmpty(dirNameForDiskLruCache)) {
            diskLruCacheManager = new DiskLruCacheManager(ct, dirNameForDiskLruCache);
        } else {
            diskLruCacheManager = new DiskLruCacheManager(ct);
        }
        diskLruCacheManager.setDefaultCacheTime(mDiskCacheTime);
    }

    /**
     * 设置硬盘缓存的最大值，单位为兆（M）.
     *
     * @param maxSizeForDisk 硬盘缓存最大值，单位为兆（M）
     */
    public static void setMaxSize(int maxSizeForDisk) {
        maxSizeForDiskLruCache = maxSizeForDisk;
    }

    /**
     * 设置内存缓存的最大值，单位为兆（M）.
     *
     * @param maxMemory 内存缓存最大值，单位为兆（M）
     */
    public static void setMaxMemory(int maxMemory) {
        maxMemoryForLruCache = maxMemory;
    }

    /**
     * 设置硬盘缓存自定义的文件名
     *
     * @param dirName 自定义文件名
     */
    public static void setDirName(String dirName) {
        dirNameForDiskLruCache = dirName;
    }

    /**
     * 索引key对应的data写入缓存
     * @param key
     * @param data
     * @param memoryCacheTime 内存缓存时间（毫秒）
     * @param diskCacheTime 文件缓存时间（毫秒）
     */
    public static void put(String key, Object data, long memoryCacheTime, long diskCacheTime) {
        switch (model) {
            case ALL_ALLOW:
                if (lruCacheManager != null && diskLruCacheManager != null) {
                    //设置硬盘缓存成功后，再设置内存缓存
                    if (diskLruCacheManager.putDiskCache(key, data, diskCacheTime)) {
                        lruCacheManager.putCache(key, data, memoryCacheTime);
                    }
                }
                break;
            case ONLY_LRU:
                if (lruCacheManager != null) {
                    lruCacheManager.putCache(key, data, memoryCacheTime);
                }
                break;
            case ONLY_DISKLRU:
                if (diskLruCacheManager != null) {
                    diskLruCacheManager.putDiskCache(key, data, diskCacheTime);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 索引key对应的data写入缓存
     * @param key
     * @param data
     * @param cacheTime 内存缓存时间（毫秒）
     */
    public static void put(String key, Object data, long cacheTime) {
        put(key, data, cacheTime, -1);
    }

    /**
     * 索引key对应的data写入缓存
     * @param key
     * @param data
     */
    public static void put(String key, Object data) {
        switch (model) {
            case ALL_ALLOW:
                if (lruCacheManager != null && diskLruCacheManager != null) {
                    //设置硬盘缓存成功后，再设置内存缓存
                    if (diskLruCacheManager.putDiskCache(key, data)) {
                        lruCacheManager.putCache(key, data);
                    }
                }
                break;
            case ONLY_LRU:
                if (lruCacheManager != null) {
                    lruCacheManager.putCache(key, data);
                }
                break;
            case ONLY_DISKLRU:
                if (diskLruCacheManager != null) {
                    diskLruCacheManager.putDiskCache(key, data);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取索引key对应的缓存内容
     *
     * @param key 缓存索引key
     * @return key索引对应的数据
     */
    public static <T> T getCache(String key) {
        T val = null;
        switch (model) {
            case ALL_ALLOW:
                if (lruCacheManager != null && diskLruCacheManager != null) {
                    val = lruCacheManager.getCache(key);
                    if (val == null) {
                        //如果硬盘缓存内容存在，内存缓存不存在。则在获取硬盘缓存后，将内容写入内存缓存
                        val = diskLruCacheManager.getDiskCache(key);
                        if (val != null)
                            lruCacheManager.putCache(key,val);
                    }
                }
                break;
            case ONLY_LRU:
                if (lruCacheManager != null) {
                    val = lruCacheManager.getCache(key);
                }
                break;
            case ONLY_DISKLRU:
                if (diskLruCacheManager != null) {
                    val = diskLruCacheManager.getDiskCache(key);
                }
                break;
            default:
                break;
        }
        return val;
    }

    /**
     * 删除所有缓存
     */
    public static void delete() {
        switch (model) {
            case ALL_ALLOW:
                if (lruCacheManager != null && diskLruCacheManager != null) {
                    lruCacheManager.deleteCache();
                    diskLruCacheManager.deleteDiskCache();
                }
                break;
            case ONLY_LRU:
                if (lruCacheManager != null) {
                    lruCacheManager.deleteCache();
                }
                break;
            case ONLY_DISKLRU:
                if (diskLruCacheManager != null) {
                    diskLruCacheManager.deleteDiskCache();
                }
                break;

            default:
                break;
        }
    }

    /**
     * 移除一条索引key对应的缓存
     *
     * @param key 索引
     */
    public static void remove(String key) {
        switch (model) {
            case ALL_ALLOW:
                if (lruCacheManager != null && diskLruCacheManager != null) {
                    lruCacheManager.removeCache(key);
                    diskLruCacheManager.removeDiskCache(key);
                }
                break;
            case ONLY_LRU:
                if (lruCacheManager != null) {
                    lruCacheManager.removeCache(key);
                }
                break;
            case ONLY_DISKLRU:
                if (diskLruCacheManager != null) {
                    diskLruCacheManager.removeDiskCache(key);
                }
                break;

            default:
                break;
        }
    }

    /**
     * 缓存数据同步
     */
    public static void flush() {
        switch (model) {
            case ALL_ALLOW:
                if (lruCacheManager != null && diskLruCacheManager != null) {
                    diskLruCacheManager.fluchCache();
                }
                break;
            case ONLY_LRU:
                break;
            case ONLY_DISKLRU:
                if (diskLruCacheManager != null) {
                    diskLruCacheManager.fluchCache();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 设置缓存模式
     *
     * @param modelSet ONLY_LRU、ONLY_DISK、ALL_ALLOW
     */
    public static void setCacheModel(int modelSet) {
        model = modelSet;
    }

    /**
     * 删除特定文件名的缓存文件
     *
     * @param dirName 文件名
     */
    public static void deleteFile(String dirName) {
        if (diskLruCacheManager != null) {
            diskLruCacheManager.deleteFile(ct, dirName);
        }
    }

    /**
     * 获取缓存大小——内存缓存+硬盘缓存
     *
     * @return
     */
    public static int size() {
        int size = 0;
        if (diskLruCacheManager != null) {
            size += diskLruCacheManager.size();
        }
        if (lruCacheManager != null) {
            size += lruCacheManager.size();
        }
        return size;
    }

    /**
     * 获取缓存大小
     *
     * @param type 硬盘缓存类型：DISKSIZE、内存缓存类型：MEMORYSIZE
     * @return 对应类型的缓存大小
     */
    public static int size(int type) {
        int size = 0;
        switch (type) {
            case DISKSIZE:
                if (diskLruCacheManager != null) {
                    size += diskLruCacheManager.size();
                }
                break;
            case MEMORYSIZE:
                if (lruCacheManager != null) {
                    size += lruCacheManager.size();
                }
                break;

            default:
                break;
        }
        return size;
    }

    /**
     * 关闭缓存
     */
    public static void close() {
        if (diskLruCacheManager != null) {
            diskLruCacheManager.close();
        }
    }
}