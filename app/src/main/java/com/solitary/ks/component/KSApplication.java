package com.solitary.ks.component;

import android.app.Application;

//import com.crashlytics.android.Crashlytics;
//import com.crashlytics.android.core.CrashlyticsCore;
//import com.solitary.ks.BuildConfig;
//
//import io.fabric.sdk.android.Fabric;

public class KSApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        configureCrashReporting();
    }
    
    private void configureCrashReporting() {
//        CrashlyticsCore crashlyticsCore = new CrashlyticsCore.Builder()
//                .disabled(BuildConfig.DEBUG)
//                .build();
//        Fabric.with(this, new Crashlytics.Builder().core(crashlyticsCore).build());
    }
    
}
