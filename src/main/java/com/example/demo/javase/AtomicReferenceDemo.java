package com.example.demo.javase;


import lombok.*;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

@Getter
@Setter
@ToString
@AllArgsConstructor
class User{
    String user;
    int age;
}
public class AtomicReferenceDemo {
    public static void main(String[] args) {
        User zs = new User("zhangsan",24);
        User lisi = new User("lisi",43);

        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(zs);
        System.out.println(atomicReference.compareAndSet(zs, lisi) + "\t" + atomicReference.toString());
        System.out.println(atomicReference.compareAndSet(zs, lisi) + "\t" + atomicReference.toString());

    }

}
