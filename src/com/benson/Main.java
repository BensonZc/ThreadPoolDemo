package com.benson;

import com.benson.pool.MultiThreadPool;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        MultiThreadPool.execute(new TestThread("t1"));
        MultiThreadPool.execute(new TestThread("t2"));
        MultiThreadPool.execute(new TestThread("t3"));
        MultiThreadPool.execute(new TestThread("t4"));
        MultiThreadPool.execute(new TestThread("t5"));
        //main函数中要手动退出
        System.exit(0);
    }
}
