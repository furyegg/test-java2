package com.qiniu.interview.T02_parallel;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

/**
 * 不采用计算公式，使用多线程的方式快速计算1到100亿的和</br>
 * 机器CPU：8核
 */
public class FastSum {
    public static void main(String[] args) {
        FastSum app = new FastSum();
        Stopwatch stopwatch = Stopwatch.createStarted();
        long sum = app.sum(1, 100000000000L);
        System.out.println("result: " + sum + ", cost: + " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }
    
    private long sum(long start, long end) {
        return 0;
    }
}