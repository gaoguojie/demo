package com.example.demo.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author gaoguojie
 * @version 1.0 2018/07/04
 */
public class SleepUtils {

    public static final void second(long seconds){
        try{
            TimeUnit.SECONDS.sleep(seconds);
        }catch (Exception e){

        }

    }

}
