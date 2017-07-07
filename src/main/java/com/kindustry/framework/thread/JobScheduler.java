package com.kindustry.framework.thread;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobScheduler {

  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  private static JobScheduler instance;

  private List<Thread> threadList;

  private final static int BUFCONCURRENT = 2;

  /**
   * 使用调度管理器构造
   * 
   * @param manager
   */
  private JobScheduler() {
    threadList = new ArrayList<Thread>();
  }

  public static JobScheduler getInstance() {
    if (instance == null) {
      synchronized (JobScheduler.class) {
        if (instance == null) {
          instance = new JobScheduler();
        }
      }
    }
    return instance;
  }

  /**
   * <p>
   * 启动该调度器 缓存层
   * </p>
   */
  public void work(int targetNumber) {
    int completed = 0;
    int currentBufJobs = 0;
    boolean isFinished = false;
    while (completed < targetNumber) {
      isFinished = true;
      for (Thread thread : threadList) {
        // 线程执行状态
        State state = thread.getState();
        if (State.TERMINATED != state) {
          isFinished = false;
        }

        switch (state) {
          case NEW: // 新增加的
            System.out.println("NEW");
            if (currentBufJobs < BUFCONCURRENT) {
              thread.start();
              currentBufJobs++;
            }
            break;
          case RUNNABLE:
            System.out.println("RUNNABLE");
            break;
          case BLOCKED:
            System.out.println("BLOCKED");
            break;
          case WAITING:
            System.out.println("WAITING");
            break;
          case TIMED_WAITING:
            System.out.println("TIMED_WAITING");
            break;
          case TERMINATED: // 线程执行完成
            System.out.println("TERMINATED");
            threadList.remove(thread);
            currentBufJobs--;
            completed++;
            break;
        }

      }

      if (completed == targetNumber) {
        break;
      }

      try {
        Thread.sleep(1000); // 暂停1 秒
      } catch (InterruptedException e) {
        logger.error("exception:", e);
      }
    }

  }

  /**
   * 执行任务调度工作
   */
  public long push(Thread thread) {
    threadList.add(thread);
    return thread.getId();
  }

  /**
   * <p>
   * 强制停止该调度器
   * </p>
   */
  public void stop(String threadName) {
    for (Thread thread : threadList) {
      if (thread.getName().equals(threadName)) {
        try {
          thread.interrupt();
          thread.join();
        } catch (InterruptedException e) {
          e.printStackTrace();
          System.out.println("333 " + thread.getId() + "    " + thread.getName() + " " + thread.isInterrupted());
        }
      }
    }
    System.out.println("thread size : " + threadList.size());
  }

}
