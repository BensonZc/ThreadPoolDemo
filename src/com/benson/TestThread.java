package com.benson;

import com.benson.exception.JobException;
import com.benson.thread.AbstractTimeoutJob;

/**
 * Created by zhangchen on 2016/2/14.
 */
public class TestThread extends AbstractTimeoutJob {
    private String threadName;
    private static final long TIMEOUT_TIME = 300000l;

    public TestThread(String threadName) {
        this.threadName = threadName;
    }

    @Override
    protected void execute() throws JobException {
        System.out.println(Thread.currentThread().getName() + " is Running......");
    }

    @Override
    protected long timeoutTime() {
        return TIMEOUT_TIME;
    }
}
