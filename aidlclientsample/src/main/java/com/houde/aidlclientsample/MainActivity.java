package com.houde.aidlclientsample;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.houde.aidlserversample.ICalcAIDL;
import com.houde.aidlserversample.IRemoteCallback;
import com.houde.aidlserversample.Person;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ICalcAIDL mCalcAidl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConn);
    }


    class RemoteCallback extends IRemoteCallback.Stub {

        @Override
        public void onDataUpdate(double distance, double duration, double velocity) throws RemoteException {
            Toast.makeText(MainActivity.this, String.format("distance %d duration %d velocity %d",
                    distance, duration, velocity), Toast.LENGTH_SHORT).show();
        }
    }

    private RemoteCallback callback;
    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("client", "onServiceDisconnected");
//            try {
//                mCalcAidl.unregisterCallback(callback);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
            mCalcAidl = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("client", "onServiceConnected");
            //通过aidl的方式
            mCalcAidl = ICalcAIDL.Stub.asInterface(service);
        }
    };


    private ServiceConnection mServiceConnByBinder = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("client", "onServiceConnected by binder");
            mRemoteBinder = service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("client", "onServiceDisconnected by binder");
            mRemoteBinder = null;
        }
    };

    private IBinder mRemoteBinder;

    /**
     * 点击BindService按钮时调用
     *
     * @param view
     */
    public void bindService(View view) {

        //使用aidl的方式
        Intent intent = new Intent();
        intent.setAction("com.houde.aidl.calc");
        intent.setPackage("com.houde.aidlserversample");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        bindService(intent, mServiceConn, Context.BIND_AUTO_CREATE);

        //使用自定义binder的形式
        Intent intent2 = new Intent();
        intent2.setAction("com.houde.aidl.calc2");
        intent2.setPackage("com.houde.aidlserversample");
        intent2.addCategory(Intent.CATEGORY_DEFAULT);
        bindService(intent2, mServiceConnByBinder, Context.BIND_AUTO_CREATE);
    }

    /**
     * 点击unBindService按钮时调用
     *
     * @param view
     */
    public void unbindService(View view) {
        try {
            unbindService(mServiceConn);
            unbindService(mServiceConnByBinder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击12+12按钮时调用
     *
     * @param view
     */
    public void addInvoked(View view) throws Exception {

        if (mCalcAidl != null) {
            int addRes = mCalcAidl.add(12, 12);
            Toast.makeText(this, addRes + "", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "服务器被异常杀死，请重新绑定服务端", Toast.LENGTH_SHORT)
                    .show();

        }

    }

    /**
     * 点击50-12按钮时调用
     *
     * @param view
     */
    public void minInvoked(View view) throws Exception {

        if (mCalcAidl != null) {
            int addRes = mCalcAidl.min(58, 12);
            Toast.makeText(this, addRes + "", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "服务端未绑定或被异常杀死，请重新绑定服务端", Toast.LENGTH_SHORT)
                    .show();
        }
    }


    private static final java.lang.String DESCRIPTOR = "com.houde.aidlserversample.custom";

    /**
     * 点击12+12按钮时调用
     *
     * @param view
     */
    public void addInvoked2(View view) throws Exception {
        if (mRemoteBinder != null) {
            android.os.Parcel _data = android.os.Parcel.obtain();
            android.os.Parcel _reply = android.os.Parcel.obtain();

            int addRes;
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                _data.writeInt(12);
                _data.writeInt(12);
                mRemoteBinder.transact(0x11, _data, _reply, 0);
                _reply.readException();
                addRes = _reply.readInt();
            } finally {
                _reply.recycle();
                _data.recycle();
            }
            Toast.makeText(this, addRes + "", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "服务器被异常杀死，请重新绑定服务端", Toast.LENGTH_SHORT)
                    .show();

        }

    }

    /**
     * 点击50-12按钮时调用
     *
     * @param view
     */
    public void minInvoked2(View view) throws Exception {
        if (mRemoteBinder != null) {
            android.os.Parcel _data = android.os.Parcel.obtain();
            android.os.Parcel _reply = android.os.Parcel.obtain();

            int addRes;
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                _data.writeInt(58);
                _data.writeInt(12);
                mRemoteBinder.transact(0x22, _data, _reply, 0);
                _reply.readException();
                addRes = _reply.readInt();
            } finally {
                _reply.recycle();
                _data.recycle();
            }

            Toast.makeText(this, addRes + "", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "服务端未绑定或被异常杀死，请重新绑定服务端", Toast.LENGTH_SHORT)
                    .show();
        }
    }


    public void complexObj(View view) throws RemoteException {
        if (mCalcAidl != null) {
            Person person = new Person();
            person.setName("houde");
            person.setSex(1);
            List<String> hobbys = new ArrayList<>();
            hobbys.add("骑车");
            hobbys.add("打球");
            person.setHobby(hobbys);
            String str = mCalcAidl.showPerson(person);
            Toast.makeText(this, str + "", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "服务端未绑定或被异常杀死，请重新绑定服务端", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void startRemoteCallback(View view) {


        if (mCalcAidl != null) {
            if (callback == null) {
                callback = new RemoteCallback();
            }
            try {
                mCalcAidl.registerCallback(callback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Toast.makeText(this, "startRemoteCallback", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "服务端未绑定或被异常杀死，请重新绑定服务端", Toast.LENGTH_SHORT)
                    .show();
        }
    }


    public void stopRemoteCallback(View view) {
        if (mCalcAidl != null) {

            try {
                mCalcAidl.unregisterCallback(callback);
                callback = null;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Toast.makeText(this, "stopRemoteCallback", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "服务端未绑定或被异常杀死，请重新绑定服务端", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
