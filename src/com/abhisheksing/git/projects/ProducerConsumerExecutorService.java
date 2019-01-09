package com.abhisheksing.git.projects;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerConsumerExecutorService {

public static void main(String[] args) throws InterruptedException {
	
	ExecutorService executor = Executors.newFixedThreadPool(2);
	BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(5);
	Runnable producerThread = ()->{
		int value = 0;
		while(true){
			try {
				queue.put(value);
				System.out.println("Produced Value :"+value);
				value++;
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	Runnable consumerThread = ()->{
		while(true){
			try {
				int value = queue.take();
				System.out.println("Consumed Value :"+value);
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
	};
	executor.execute(producerThread);
	executor.execute(consumerThread);
	executor.shutdown();
}
}
