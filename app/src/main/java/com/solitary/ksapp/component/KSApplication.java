package com.solitary.ksapp.component;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.squareup.leakcanary.LeakCanary;

//import com.crashlytics.android.Crashlytics;
//import com.crashlytics.android.core.CrashlyticsCore;
//import com.solitary.ks.BuildConfig;
//
//import io.fabric.sdk.android.Fabric;

public class KSApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
