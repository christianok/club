package com.earth.message.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicStampedReference;

@Service
public class HttpServer implements Runnable {
    static class Test{
        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        private static AtomicMarkableReference atomicMarkableReference = new AtomicMarkableReference(100, false);

    }
    public int lengthOfLongestSubstring(String s) {
        ArrayList<String> list = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();

        int n = s.length();
        Set<Character> set = new HashSet<>();
        int ans = 0, i = 0, j = 0;
        while (i < n && j < n) {
            // try to extend the range [i, j]
            if (!set.contains(s.charAt(j))){
                set.add(s.charAt(j++));
                ans = Math.max(ans, j - i);
            }
            else {
                set.remove(s.charAt(i++));
            }
        }
        return ans;
    }

    @Override
    public void run() {
        String test = "abcd";
        int i = lengthOfLongestSubstring(test);
        System.out.println(i);
    }

    public  static void main(String[] args) {
        HttpServer httpServer = new HttpServer();
        (new Thread(httpServer)).start();
        (new Thread(httpServer)).start();
    }
}
