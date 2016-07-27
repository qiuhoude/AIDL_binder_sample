// ICalcAIDL.aidl
package com.houde.aidlserversample;

/*
Java编程语言的基本数据类型 (int, long, char, boolean等),String和CharSequence
，集合接口类型List和Map，不需要import 语句。
*/
//需要import进Person
import com.houde.aidlserversample.Person;
import com.houde.aidlserversample.IRemoteCallback;
// Declare any non-default types here with import statements

interface ICalcAIDL {

   int add(int x , int y);

   int min(int x , int y );

   //非原始类型中，除了String和CharSequence以外，
   //其余均需要一个方向指示符。方向指示符包括in、out、和inout。
    // in 表示传入数据， out 表示传出数据， inout 表示双向传递。
    //注意含有 out 时 Person 类需要实现 readFromParcel() 方法
   String  showPerson(in Person preson);

   void registerCallback(IRemoteCallback cb);
   void unregisterCallback(IRemoteCallback cb);

}
