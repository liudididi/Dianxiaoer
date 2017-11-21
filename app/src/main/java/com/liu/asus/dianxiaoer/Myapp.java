package com.liu.asus.dianxiaoer;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;


import com.facebook.drawee.backends.pipeline.Fresco;
import com.igexin.sdk.PushManager;
import com.mob.MobSDK;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by 地地 on 2017/11/15.
 * 邮箱：461211527@qq.com.
 */

public class Myapp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this, "2279a704a623a", "266ff864e44a680ca19d6ed10c3e9846");
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        //Bugly 初始化
        Fresco.initialize(this);
        AutoLayoutConifg.getInstance().useDeviceSize();
        Context context = getApplicationContext();
// 获取当前包名
        String packageName = context.getPackageName();
// 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
// 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
// 初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), "e1c69cc4ab", true);

// 如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
// CrashReport.initCrashReport(context, strategy);

        //统计
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent. startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this,
                "5a0c16c18f4a9d27480000c4", ""));

        PushManager.getInstance().initialize(this.getApplicationContext(),DemoPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(),DemoIntentService.class);

    }
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
