package com.zrq.base.util;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;

/**
 * 描述: Log的封装类
 *
 * @author zhangrq
 * 2016/8/16 14:27
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    /**
     * 存储 错误日志的 路径
     */
    private String logPath;
    private Context context;
    private OnCrashListener onCrashListener;

    /**
     * 不需要包含 SD 根目录
     */
    public CrashHandler(Context context) {
        this(context, "本地日志" + File.separator + "cash.log");
    }

    /**
     * 不需要包含 SD 根目录
     */
    public CrashHandler(Context context, OnCrashListener onCrashListener) {
        this(context, "本地日志" + File.separator + "cash.log", onCrashListener);
    }

    /**
     * 不需要包含 SD 根目录
     */
    public CrashHandler(Context context, String logPath) {
        this(context, logPath, null);
    }

    /**
     * 不需要包含 SD 根目录
     */
    private CrashHandler(Context context, String logPath, OnCrashListener onCrashListener) {
        if (!Environment.isExternalStorageEmulated()) {
            return;
        }
        this.logPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + logPath + File.separator;
        this.context = context;
        this.onCrashListener = onCrashListener;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void setOnCrashListener(OnCrashListener onCrashListener) {
        this.onCrashListener = onCrashListener;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // 打印异常信息
        ex.printStackTrace();
        // 本地保存异常信息
        String time = DateUtils.getYearMonthDayHourMinuteSeconds(System.currentTimeMillis());
        String phoneInfo = getPhoneInfo();
        String cashMessage = time + "\n" + phoneInfo + ("\n" + getStackTraceString(ex));
        saveFile(cashMessage);
        // 设置捕获到异常
        if (onCrashListener != null)
            onCrashListener.onCrash(cashMessage);
        // 杀死进程
        Process.killProcess(Process.myPid());

    }

    /**
     * 获取手机的信息
     */
    private String getPhoneInfo() {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            return (" versionName : " + packageInfo.versionName) +
                    "\n versionCode : " + packageInfo.versionCode +
                    "\n OS  version : " + Build.VERSION.RELEASE +
                    "\n 制造商 : " + Build.MANUFACTURER +
                    "\n手机型号 : " + Build.MODEL +
                    "\n cpu架构 : " + Build.CPU_ABI + "  " + Build.CPU_ABI2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 保存文件
     */
    private void saveFile(String result) {
        FileOutputStream output = null;
        try {
            File file = new File(logPath + "log" + System.currentTimeMillis() + ".txt");
            String parentStr = file.getParent();
            boolean createSDCardDir = createSDCardDir(parentStr);
            if (!createSDCardDir) {
                return;
            }
            output = new FileOutputStream(new File(logPath + "log" + System.currentTimeMillis() + ".txt"), false);
            output.write(result.getBytes());
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建目录文件
     */
    private boolean createSDCardDir(String newPath) {
        File file = new File(newPath);
        // 若存在，直接返回，若不存在，则创建目录文件
        return file.exists() || file.mkdir();
    }

    /**
     * 获取异常信息
     */
    private String getStackTraceString(Throwable tr) {
        if (tr == null)
            return "";
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    interface OnCrashListener {
        void onCrash(String error);
    }
}

