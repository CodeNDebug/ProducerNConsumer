package com.abhisheksing.git.projects;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumerOptimized {
	
	private int bufferSize;
	private Queue<Integer> buffer = new LinkedList<Integer>();
	
	private Object lock = new Object();

	public ProducerConsumerOptimized(int bufferSize) {
		super();
		this.bufferSize = bufferSize;
	}

	public static void main(String[] args) throws InterruptedException {
		ProducerConsumerOptimized pc = new ProducerConsumerOptimized(5);
		Thread producerThread = new Thread(new Runnable() {
			int value = 0;
			@Override
			public void run() {
				while(true){
					try {
						pc.add(value);
						System.out.println("Produced value :"+ value);
						value++;
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
					try {
						int value= pc.poll();
						System.out.println("Consumed value :"+ value);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		producerThread.start();
		consumerThread.start();
		
		producerThread.join();
		consumerThread.join();
	}
	
	public void add(int value) throws InterruptedException {
		synchronized(lock){
			if(isFull(buffer)){
				lock.wait();
			}
			buffer.add(value);
			lock.notify();
			Thread.sleep(100);
		}
	}
		
	public int poll() throws InterruptedException {
		synchronized(lock) {
			if(isEmpty(buffer)){
				lock.wait();
			}
			int value = buffer.remove();
			lock.notify();
			Thread.sleep(100);
			return value;
		}
	}

	private boolean isFull(Queue<Integer> buffer2) {
		return buffer2.size() == bufferSize;
	}
	
	private boolean isEmpty(Queue<Integer> buffer2) {
		return buffer2.size() == 0;
	}
	}
