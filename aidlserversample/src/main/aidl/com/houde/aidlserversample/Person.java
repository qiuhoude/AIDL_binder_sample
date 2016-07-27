package com.houde.aidlserversample;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * aidl复杂数据的传输(可以使用Android studio插件来完成Parcelable的实现)
 *
 * @auther 邱坤
 * @date 2016/7/27.
 */
public class Person implements Parcelable {
    private String name;
    private int sex;
    private List<String> hobby; //爱好


    @Override
    public int describeContents() {
        return 0;
    }

    //注意写入变量和读取变量的顺序应该一致 不然得不到正确的结果
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);//先写入的是name
        dest.writeInt(this.sex); //后是age
        dest.writeStringList(this.hobby);
    }


    //注意写入变量和读取变量的顺序应该一致 不然得不到正确的结果
    public void readFromParcel(Parcel in) {
        /**
         * Parcel的常用方法
         * obtain()：在池中获取一个新的Parcel。
         * 　　dataSize()：得到当前Parcel对象的实际存储空间。
         * 　　dataPostion()：获取当前Parcel对象的偏移量。
         * 　　setDataPosition()：设置当前Parcel对象的偏移量。
         * 　　recyle()：清空、回收当前Parcel对象的内存。
         * 　　writeXxx()：向当前Parcel对象写入数据，具有多种重载。
         * 　　readXxx()：从当前Parcel对象读取数据，具有多种重载。
         */
        this.name = in.readString();
        this.sex = in.readInt();
        this.hobby = in.createStringArrayList();

    }


    public Person() {
    }

    protected Person(Parcel in) {
        readFromParcel(in);
    }


    /**
     * 实现Parcelable接口的类必须要有一个static field称为CREATOR，
     * 用于实现Parcelable.Creator接口的对象。
     * 在AIDL文件自动生成的Java接口中，IBinder将调用Parcelable.Creator来获得传递对象：
     * _arg1 = cn.wei.flowingflying.proandroidservice.Person.CREATOR.createFromParcel(data);
     */
    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public List<String> getHobby() {
        return hobby;
    }

    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", hobby=" + hobby +
                '}';
    }
}
