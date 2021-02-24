package com.kindustry.framework.service;

import com.kindustry.framework.thread.ThreadPool;

public class JobService {

  public void addJob(Worker worker) {

    ThreadPool threadManager = ThreadPool.getInstance();

    worker.addObserver((o, arg) -> {
      System.out.println(" thread : " + Thread.currentThread().getId() + "    " + Thread.currentThread().getName());
      System.out.println(o);
      System.out.println(arg);
    });

    threadManager.push(worker);
  }
}
