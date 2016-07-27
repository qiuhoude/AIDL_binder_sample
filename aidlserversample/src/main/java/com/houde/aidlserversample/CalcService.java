package com.houde.aidlserversample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import android.os.Handler;

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


    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    public void onRebind(Intent intent) {
        Log.e(TAG, "onRebind");
        super.onRebind(intent);
    }

    IRemoteCallback mCallback;

    public static Handler uiHander = new Handler();

    class LocalBinder extends ICalcAIDL.Stub {
        private int count = 0;

        @Override
        public int add(int x, int y) {
            Log.e(TAG,"server add run");
            return x + y;
        }

        @Override
        public int min(int x, int y) {
            Log.e(TAG, "server min run");
            return x - y;
        }

        @Override
        public String showPerson(Person requester) throws RemoteException {
            return requester.toString() + " show " + (count++) + " ";
        }

        @Override
        public void registerCallback(IRemoteCallback cb) throws RemoteException {
            Log.e(TAG, "server registerCallback run");
            mCallback = cb;
            task = new RecordTask();
            uiHander.postDelayed(task, 1000);
        }

        @Override
        public void unregisterCallback(IRemoteCallback cb) throws RemoteException {
            Log.e(TAG, "server unregisterCallback run");
            mCallback = null;
            uiHander.removeCallbacks(task);
            task = null;
        }

//        @Override
//        public void callCallback(IRemoteCallback cb) throws RemoteException {
//            cb.onDataUpdate(20, 16.3, 10);
//        }
    }

    public Runnable task;

    double duration = 16.3;

    class RecordTask implements Runnable {

        @Override
        public void run() {
            Log.d("qiu","server RecordTask run");
            if (mCallback != null) {
                try {
                    mCallback.onDataUpdate(20, duration, 10);
                    duration += 0.5;
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 创建binder 远程的
     */
    private final ICalcAIDL.Stub mBinder = new LocalBinder();
}
