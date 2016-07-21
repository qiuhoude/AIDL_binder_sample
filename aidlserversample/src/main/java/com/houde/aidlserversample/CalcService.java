package com.houde.aidlserversample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class CalcService extends Service {
    public CalcService() {
        Log.e(TAG, "CalcService constructor");
    }

    private static final String TAG = "CalcService";


    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind");
        return mBinder;
    }



    public void onDestroy()
    {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

    public boolean onUnbind(Intent intent)
    {
        Log.e(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    public void onRebind(Intent intent)
    {
        Log.e(TAG, "onRebind");
        super.onRebind(intent);
    }


    /**
     * 创建binder 远程的
     */
    private final ICalcAIDL.Stub mBinder = new ICalcAIDL.Stub()
    {

        @Override
        public int add(int x, int y)
        {
            return x + y;
        }

        @Override
        public int min(int x, int y)
        {
            return x - y;
        }

    };
}
