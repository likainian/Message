package com.example.dinghao.message;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by dinghao on 2016/12/8.
 */

public class FileSizeUtil {

    /**
     * SDCARD是否存
     */
    public static boolean externalMemoryAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    /**
     * 获取手机内部剩余存储空间
     *
     * @return
     */
    public static String getAvailableInternalMemorySize(Context context) {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return "手机内部剩余存储空间："+Formatter.formatFileSize(context,availableBlocks * blockSize);
    }

    /**
     * 获取手机内部总的存储空间
     *
     * @return
     */
    public static String getTotalInternalMemorySize(Context context) {
        File path = Environment.getDataDirectory();
        Log.i("ttt", "getTotalInternalMemorySize: "+path.getAbsolutePath());
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return "手机内部总的存储空间："+Formatter.formatFileSize(context,totalBlocks * blockSize);
    }

    /**
     * 获取SDCARD剩余存储空间
     *
     * @return
     */
    public static String getAvailableExternalMemorySize(Context context) {
        if (isExternalStorageWritable()) {
            File path = Environment.getExternalStorageDirectory();
            Log.i("ttt", "getTotalInternalMemorySize: "+path.getAbsolutePath());
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return "SDCARD剩余存储空间："+Formatter.formatFileSize(context,availableBlocks * blockSize);
        } else {
            return null;
        }
    }

    /**
     * 获取SDCARD总的存储空间
     *
     * @return
     */
    public static String getSDCardMemory(Context context) {
        long[] sdCardInfo=new long[2];
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long bSize = sf.getBlockSize();
            long bCount = sf.getBlockCount();
            long availBlocks = sf.getAvailableBlocks();

            sdCardInfo[0] = bSize * bCount;//总大小
            sdCardInfo[1] = bSize * availBlocks;//可用大小
        }
        return Formatter.formatFileSize(context,sdCardInfo[0]);
    }
    public static String getTotalExternalMemorySize(Context context) {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            Log.i("ttt", "getTotalInternalMemorySize: "+path.getAbsolutePath());
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return "SDCARD总的存储空间："+Formatter.formatFileSize(context,totalBlocks * blockSize);
        } else {
            return null;
        }
    }

    /**
     * 获取系统总运存
     */
    public static String getTotalMemorySize(Context context) {
        String dir = "/proc/meminfo";
        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr);
            String memoryLine = br.readLine();
            String[] strings = memoryLine.split("\\s+");
            int memory = Integer.valueOf(strings[1]).intValue() * 1024;
            return "系统总运存："+Formatter.formatFileSize(context,memory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前可用运存
     */
    public static String getAvailableMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(memoryInfo);
        return "当前可用运存："+Formatter.formatFileSize(context, memoryInfo.availMem);
    }

}
