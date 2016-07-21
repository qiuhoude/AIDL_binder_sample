package com.houde.aidlserversample;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

/**
 * 之定义binder,来实现进程间的通讯,
 * 服务的的server端的代码
 */
public class CalcPlusService extends Service {
    public CalcPlusService() {
    }

    private static final String TAG = "CalcPlusService";

    private IBinder mBinder = new CustomServerBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void onCreate() {
        Log.e(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
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


    class CustomServerBinder extends Binder {
        //这个值要与客户端的一样,通讯的唯一标示
        private static final java.lang.String DESCRIPTOR = "com.houde.aidlserversample.custom";

        //因为服务器端的binder需要实现 onTransact 方法

        /**
         * code 是一个整形的唯一标识，用于区分执行哪个方法，客户端会传递此参数，告诉服务端执行哪个方法
         * data客户端传递过来的参数
         * replay服务器返回回去的值
         * flags标明是否有返回值，0为有（双向），1为没有（单向）
         */
        @Override
        protected boolean onTransact(int code, Parcel data,
                                     Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                case 0x11: {
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0;
                    _arg0 = data.readInt();
                    int _arg1;
                    _arg1 = data.readInt();
                    int _result = this.add(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                }
                case 0x22: {
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0;
                    _arg0 = data.readInt();
                    int _arg1;
                    _arg1 = data.readInt();
                    int _result = this.min(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }

        private int min(int arg0, int arg1) {
            //实现自己的算法
            return arg0 - arg1;
        }

        private int add(int arg0, int arg1) {
            //
            return arg0 + arg1;
        }

    }


}
