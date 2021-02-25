package com.kindustry.framework.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import com.kindustry.framework.thread.SchedulerThreadManager;

public class JobService {

  public void addWorker(Worker worker) {

    // worker.addObserver((o, arg) -> {
    // System.out.println(" thread : " + Thread.currentThread().getId() + "    " + Thread.currentThread().getName());
    // System.out.println(o);
    // System.out.println(arg);
    // });
    //
    // threadManager.push(worker);

    // final ExecutorService executor = LimitThreadPool.getInstance().getThreadPoolExecutor();

    Callable<Boolean> callTask = new Callable<Boolean>() {
      @Override
      public Boolean call() throws Exception {
        worker.job();
        return true;
      }
    };

    FutureTask<Boolean> oTask = new FutureTask<Boolean>(callTask) {
      @Override
      protected void done() {
        try {
          // TODO
          System.out.println("oTask");
          Boolean bCheckSuccess = (Boolean)this.get();
          if (bCheckSuccess != null) {
            // 输出获取到的结果
            System.out.println("futureTask.get()-->" + bCheckSuccess);
          } else {
            // 输出获取到的结果
            System.out.println("futureTask.get()未获取到结果");
          }

        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (ExecutionException e) {
          e.printStackTrace();
        }
      }
    };
    // executor.submit(oTask);

    SchedulerThreadManager.getInstance().push(oTask);
  }

}
