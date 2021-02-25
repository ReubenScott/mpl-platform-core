package com.kindustry.framework.thread;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 任务调度管理器
 * </p>
 * 
 * 2010-5-28 下午11:30:20
 */
public class SchedulerThreadManager {

  /**
   * Demo描述: 线程池(ThreadPoolExecutor)及其拒绝策略(RejectedExecutionHandler)使用示例
   * 
   * 工作原理: 线程池的工作中主要涉及到:corePoolSize,workQueue,maximumPoolSize, RejectedExecutionHandler 它们的调用原理: 1 当线程池中线程数量小于corePoolSize则创建线程,并处理请求 2
   * 当线程池中线程数量等于corePoolSize则把请求放入workQueue中,线程池中的的空闲线程就从workQueue中取任务并处理 3 当workQueue已满存放不下新入的任务时则新建线程入池,并处理请求; 如果线程池中线程数大于maximumPoolSize则用RejectedExecutionHandler使用一定的策略来做拒绝处理
   * 
   * 在该机制中还有一个keepAliveTime,文档描述如下: when the number of threads is greater than the core, this is the maximum time that excess idle threads will wait for new tasks before
   * terminating. 它是什么意思呢？ 比如线程池中一共有5个线程,其中3个为核心线程(core)其余两个为非核心线程. 当超过一定时间(keepAliveTime)非核心线程仍然闲置(即没有执行任务或者说没有任务可执行)那么该非核心线程就会被终止. 即线程池中的非核心且空闲线程所能持续的最长时间,超过该时间后该线程被终止.
   * 
   * 
   * RejectedExecutionHandler的四种拒绝策略
   * 
   * hreadPoolExecutor.AbortPolicy: 当线程池中的数量等于最大线程数时抛出java.util.concurrent.RejectedExecutionException异常. 涉及到该异常的任务也不会被执行.
   * 
   * ThreadPoolExecutor.CallerRunsPolicy(): 当线程池中的数量等于最大线程数时,重试添加当前的任务;它会自动重复调用execute()方法
   * 
   * ThreadPoolExecutor.DiscardOldestPolicy(): 当线程池中的数量等于最大线程数时,抛弃线程池中工作队列头部的任务(即等待时间最久Oldest的任务),并执行新传入的任务
   * 
   * ThreadPoolExecutor.DiscardPolicy(): 当线程池中的数量等于最大线程数时,丢弃不能执行的新加任务
   * 
   * 参考资料: http://blog.csdn.net/cutesource/article/details/6061229 http://blog.csdn.net/longeremmy/article/details/8231184
   * http://blog.163.com/among_1985/blog/static/275005232012618849266/ http://blog.csdn.net/longeremmy/article/details/8231184 http://ifeve.com/java-threadpool/
   * http://www.blogjava.net/xylz/archive/2010/07/08/325587.html http://blog.csdn.net/ns_code/article/details/17465497
   * 
   */

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private volatile static ScheduledExecutorService scheExecService;

  private volatile static SchedulerThreadManager instance;

  // 計數器
  private static LongAdder count = new LongAdder();

  /**
   * 根据线程池中线程数量构造调度管理器
   * 
   */
  private SchedulerThreadManager() {
    // 可用cpu逻辑处理器 , 线程池维护的核心线程数
    int corePoolSize = Runtime.getRuntime().availableProcessors();
    scheExecService = Executors.newScheduledThreadPool(corePoolSize, new ThreadFactory() {
      public Thread newThread(Runnable runnable) {
        // 对新创建的线程做一些操作
        Thread thread = Executors.defaultThreadFactory().newThread(runnable);
        thread.setPriority(Thread.MIN_PRIORITY); // 设置优先级 为最低
        count.increment();
        logger.debug("ThreadPool new Thread Group: {} ID: {} Name: {} ", new Object[] {thread.getThreadGroup().getName(), thread.getId(), thread.getName()});
        return thread;
      }
    });
    //
    ((ThreadPoolExecutor)scheExecService).setKeepAliveTime(30, TimeUnit.MINUTES);
    //
    ((ThreadPoolExecutor)scheExecService).allowCoreThreadTimeOut(true);

    logger.debug("SchedulerManager init Thread Size ;{}", corePoolSize);
  }

  /***
   * 获取实例
   * 
   * @return
   */
  public static SchedulerThreadManager getInstance() {
    if (instance == null) {
      synchronized (SchedulerThreadManager.class) {
        if (instance == null) {
          instance = new SchedulerThreadManager();
        }
      }
    }
    return instance;
  }

  /***
   * <p>
   * 添加需要计划的任务
   * </p>
   * 
   * @param command
   * @param time
   *          // 定时
   */
  public void putSchedule(Runnable command, Date... startTimes) {
    long current = System.currentTimeMillis();
    if ((startTimes != null) && (startTimes.length > 0)) {
      for (Date time : startTimes) {
        long delay = time.getTime() - current;
        scheExecService.schedule(command, delay, TimeUnit.MILLISECONDS);
      }
    } else {
      scheExecService.schedule(command, 0, TimeUnit.MILLISECONDS);
    }
  }

  /***
   * 
   * 添加需要计划的任务
   * 
   * 周期性 定时任务
   * 
   */
  public void putFixedRateSchedule(Runnable command, Date startTime, long period) {
    long current = System.currentTimeMillis();
    if (startTime != null) {
      long initialDelay = startTime.getTime() - current;
      scheExecService.scheduleAtFixedRate(command, initialDelay, period, TimeUnit.MILLISECONDS);
    } else {
      scheExecService.scheduleAtFixedRate(command, 0, period, TimeUnit.MILLISECONDS);

    }
  }

  /**
   * 取决于每次任务执行的时间长短，是基于不固定时间间隔进行任务调度。
   * 
   * @param command
   * @param startTime
   * @param period
   * @author kindustry
   */
  public void putWithFixedSchedule(Runnable command, Date startTime, long period) {
    long current = System.currentTimeMillis();
    if (startTime != null) {
      long initialDelay = startTime.getTime() - current;
      scheExecService.scheduleWithFixedDelay(command, initialDelay, period, TimeUnit.MILLISECONDS);
    } else {
      scheExecService.scheduleWithFixedDelay(command, 0, period, TimeUnit.MILLISECONDS);
    }
  }

  /**
   * 添加任务调度工作
   */
  public void push(Runnable thread) {
    scheExecService.execute(thread);
  }

  /**
   * 添加任务调度工作
   */
  public Future<?> push(Callable<?> thread) {
    return scheExecService.submit(thread);
  }

  /***
   * 停止任务调度器
   */
  public void stop() {
    scheExecService.shutdown();
  }

  /**
   * scheExecServiceを取得する。
   * 
   * @return the scheExecService
   */
  public ThreadPoolExecutor getThreadPoolExecutor() {
    return (ThreadPoolExecutor)scheExecService;
  }

}