package com.kindustry.test.observe;

import java.util.Observable;
/**
 * 工人
 * */
public class Worker extends Observable implements Runnable {

	
	private boolean threadFinish = false;
	
	private boolean workDone = false;
	
	private Long id;
	
	@Override
	public void run() {
		id = Thread.currentThread().getId();
		while (!isThreadFinish()){
			if (!isWorkDone()){
				work();
			}
		}
	}
	
	
	private void work(){
		double d = Math.random();
		try {
			System.out.println("Thread : "+id+" work. get "+d);
			if (d > 0.99){
				System.out.println("work done");
				workDone = true;
			}
			Thread.sleep(500);
			setChanged();
			notifyObservers();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	synchronized public boolean isWorkDone() {
		return workDone;
	}
	
	synchronized public void setThreadFinish(boolean threadFinish) {
		this.threadFinish = threadFinish;
	}
	
	synchronized public boolean isThreadFinish() {
		return threadFinish;
	}
}
