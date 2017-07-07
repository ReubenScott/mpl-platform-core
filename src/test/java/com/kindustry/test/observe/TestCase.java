package com.kindustry.test.observe;

import org.junit.Test;

public class TestCase {

	@Test
	public void test(){
		Worker worker1 = new Worker();
		Worker worker2 = new Worker();
		
		Overseer os = new Overseer();
		
		worker1.addObserver(os);
		worker2.addObserver(os);
		
		Thread t1 = new Thread(worker1);
		t1.start();
		
		Thread t2 = new Thread(worker2);
		t2.start();
		
		while (true){
			if (worker1.isThreadFinish() && worker2.isThreadFinish()){
				break;
			}
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
