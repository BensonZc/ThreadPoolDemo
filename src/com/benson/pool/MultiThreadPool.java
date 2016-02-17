package com.benson.pool;

import com.benson.exception.JobException;
import com.benson.thread.JobResult;
import com.benson.thread.TimeoutJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * Created by zhangchen on 2016/2/14.
 */
public final class MultiThreadPool {
    private static final ExecutorService executorService;

    static {
        /**
         * 创建一个线程池
         * newSingleThreadExecutor() 创建一个单线程线程池
         * newFixedThreadPool() 创建一个可重用固定线程数的线程池
         * newCachedThreadPool() 创建一个可缓存的线程池
         * newScheduledThreadPool() 创建一个大小无限的线程池。此线程池支持定时以及周期性执行任务的需求。
         */
        executorService = Executors.newFixedThreadPool(2);
        /**
         * jvm中增加一个关闭的钩子，当jvm关闭的时候，会执行系统中已经设置的所有通过方法addShutdownHook添加的钩子。
         * 当系统执行完这些钩子后，jvm才会关闭。所以这些钩子可以在jvm关闭的时候进行内存清理、对象销毁等操作。
         */
        Runtime.getRuntime().addShutdownHook(new Thread(new DefaultCleanThreadPool(executorService)));
    }

    /**
     * 执行线程任务
     * @param timeoutJob 任务
     */
    public static void execute(final TimeoutJob timeoutJob) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    timeoutJob.run();
                } catch (JobException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    /**
     * 执行有返回参数的线程任务
     * @param timeoutJob 任务
     * @return
     */
    public static JobResult executeWithJobResult(final TimeoutJob timeoutJob) {
        Future<JobResult> future = executorService.submit(new Callable<JobResult>() {
            @Override
            public JobResult call() throws Exception {
                timeoutJob.run();
                return new JobResult(JobResult.RETURN_SUCCESS, "job run success");
            }
        });
        try {
            return future.get(timeoutJob.getTimeoutTime(), TimeUnit.SECONDS);
        } catch (Exception e) {
            return new JobResult(JobResult.RETURN_FAIL, e.getMessage());
        }
    }

    public static abstract class CleanThreadPool implements Runnable {
        protected ExecutorService executorService;
        public CleanThreadPool(ExecutorService executorService) {
            this.executorService = executorService;
        }
    }

    public static class DefaultCleanThreadPool extends CleanThreadPool {
        public DefaultCleanThreadPool(ExecutorService executorService) {
            super(executorService);
        }

        @Override
        public void run() {
            /**
             * 大部分线程被终止，
             * 如果线程中没有sleep 、wait、Condition、定时锁等应用, interrupt()方法是无法中断当前的线程的。
             * 所以，ShutdownNow()并不代表线程池就一定立即就能退出，它可能必须要等待所有正在执行的任务都执行完成了才能退出。
             * shutdown只是将空闲的线程interrupt()了，因此在shutdown()之前提交的任务可以继续执行直到结束。
             */
            executorService.shutdownNow();
        }
    }

    public static void shutDownNow(){
        executorService.shutdownNow();
    }
}
