package com.earth.message.algorithms;

import java.util.Collections;
import java.util.PriorityQueue;

public class median {
    private PriorityQueue<Integer> minHeap, maxHeap;

    public median(){
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        minHeap = new PriorityQueue<>();

    }


    public void addNum(Integer num) {
        maxHeap.add(num);
        minHeap.add(maxHeap.poll());

        if (maxHeap.size() < minHeap.size()) {
            maxHeap.add(minHeap.poll());
        }
    }

    public Integer getMedian(){
        if (maxHeap.size() == minHeap.size()) {
            return (maxHeap.peek() + minHeap.peek()) / 2;
        }else {
            return maxHeap.peek();
        }
    }

}
