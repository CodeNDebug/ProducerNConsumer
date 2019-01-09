package com.abhisheksing.git.projects;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerConsumerBlockingQueue {
	
	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(5);
		Thread producerThread = new Thread(new Runnable() {
			int value = 0;
			@Override
			public void run() {
				while(true){
					try {
						queue.put(value);
						System.out.println("Produced Value :"+ value);
						value++;
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		Thread consumerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					int value;
					try {
						value = queue.take();
						System.out.println("Consumed Value :"+ value);
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		producerThread.start();
		consumerThread.start();
		consumerThread.join();
		producerThread.join();
	}

}
