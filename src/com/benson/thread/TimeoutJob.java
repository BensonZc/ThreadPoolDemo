package com.benson.thread;

import com.benson.exception.JobException;

/**
 * Created by zhangchen on 2016/2/16.
 */
public interface TimeoutJob {
    /**
     * @return  -1 means not yet start
     */
    public long startTime();

    public void run() throws JobException;

    /**
     * @param executeThread : executeThread.interrupt()
     * @throws JobException
     */
    public void about(Thread executeThread) throws JobException;

    public long getTimeoutTime();
}
