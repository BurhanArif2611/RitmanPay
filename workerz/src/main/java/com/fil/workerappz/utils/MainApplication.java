package com.fil.workerappz.utils;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.view.View;

import com.orm.SugarContext;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by HS on 22-Mar-18.
 * FIL AHM
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        SugarContext.init(this);

    }
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        SugarContext.terminate();
    }

}