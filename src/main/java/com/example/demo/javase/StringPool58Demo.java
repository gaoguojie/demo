package com.example.demo.javase;


/**
 *方法去和运行时常量池溢出
 * 由于运行时常量池是方法区的一部分，JDK8中完全使用元空间来代替永久代
 *
 *
 * string.intern()是一个本地方法，它的作用是如果字符串常量池中已经包含一个等于String对象的字符串，则返回代表池中这个字符串的String对象引用；
 * 否则，会将此String对象包含的字符串添加到常量池中，并且返回此String对象的引用
 */
public class StringPool58Demo {

    public static void main(String[] args) {
        String str1 = new StringBuilder().append("58").append("tongcheng").toString();
        System.out.println(str1);
        System.out.println(str1.intern());

        System.out.println(str1 == str1.intern());

        // 只有java是false，其它都是true
        //又一个初始化java字符串（jdk出娘胎自带的）在加载sun。misc。version这个类的时候进入常量池
        String str2 = new StringBuilder().append("ja").append("va").toString();
        System.out.println(str2);
        System.out.println(str2.intern());

        System.out.println(str2 == str2.intern());
    }
}
