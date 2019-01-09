package com.abhisheksing.git.projects;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumerSimple {
	
	private Queue<Integer> list;
    private int size;
    private Object lock = new Object();
    public ProducerConsumerSimple(int size) {
        this.list = new LinkedList<>();
        this.size = size;
    }
    
	public static void main(String[] args) throws InterruptedException {
		ProducerConsumerSimple buffer = new ProducerConsumerSimple(5);
		Thread producerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    buffer.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread consumerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    buffer.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        producerThread.start();
        consumerThread.start();
        
        producerThread.join();
        consumerThread.join();
	}
	
	private void produce() throws InterruptedException{
		int value = 0;
		while(true){
			synchronized(lock){
				if(isFull(list)){
					lock.wait();
				}
				list.add(value);
				System.out.println("Produced Value :"+ value);
				value++;
				lock.notify();
				Thread.sleep(100);
			}
		}
		}
	private void consume() throws InterruptedException{
		while(true){
			synchronized(lock){
				if(isEmpty(list)){
					lock.wait();
				}
				int value = list.remove();
				System.out.println("Consumed Value :"+ value);
				value++;
				
				lock.notify();
				Thread.sleep(100);
			}
		}
	}
	
	private boolean isFull(Queue<Integer> list2){
		return list2.size() == size;
	}
	
	private boolean isEmpty(Queue<Integer> list2){
		return list2.size() == 0;
	}
	
}