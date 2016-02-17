package com.benson.thread;

import com.benson.exception.JobException;

/**
 * Created by zhangchen on 2016/2/16.
 */
public abstract class AbstractTimeoutJob implements TimeoutJob {
    private long startTime = -1;

    @Override
    public long startTime() {
        return startTime;
    }

    protected abstract void execute() throws JobException;

    protected abstract long timeoutTime();

    @Override
    public void run() {
        startTime = System.currentTimeMillis();
        try {
            execute();
        } catch (JobException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void about(Thread executeThread) throws JobException {
        executeThread.interrupt();
    }

    @Override
    public long getTimeoutTime() {
        return timeoutTime();
    }
}
