package com.benson.thread;

import com.benson.exception.JobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhangchen on 2016/2/16.
 */
public abstract class AbstractTimeoutJob implements TimeoutJob {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
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
            logger.error(e.getMessage(), e);
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