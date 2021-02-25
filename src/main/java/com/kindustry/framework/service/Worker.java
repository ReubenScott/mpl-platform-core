package com.kindustry.framework.service;

/**
 * 
 * */
public interface Worker {

  public void job();

  // {
  //
  // try {
  // double d = Math.random();
  // while (d < 0.90) {
  // d = Math.random();
  // System.out.println(" thread : " + Thread.currentThread().getId() + "  " + Thread.currentThread().getName() + " work. get " + d);
  // Thread.sleep(500);
  // }
  // System.out.println("work done");
  // } catch (InterruptedException e) {
  // e.printStackTrace();
  // }
  // }
}
