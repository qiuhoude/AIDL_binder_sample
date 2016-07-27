// IRemoteCallback.aidl
package com.houde.aidlserversample;


//进程间的接口定义,回调接口
interface IRemoteCallback {

    void onDataUpdate(double distance,double duration,double velocity);
}
