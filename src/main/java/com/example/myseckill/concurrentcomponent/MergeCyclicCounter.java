package com.example.myseckill.concurrentcomponent;

import org.springframework.stereotype.Component;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


@Component
public class MergeCyclicCounter {

    private static class Generation {
        Generation() {}                 // prevent access constructor creation
    }

    /** The lock for guarding barrier entry */
    private final ReentrantLock lock = new ReentrantLock();
    /** Condition to wait on until tripped */
    private final Condition trip = lock.newCondition();
    /** The number of parties */
    private final int parties = 10;
    /**
     * Number of parties still waiting. Counts down from parties to 0
     * on each generation.  It is reset to parties on each new
     * generation or when broken.
    */
     private volatile int count = 10;
    /** The current generation */
    /** cycle time */
    private final long maxWaitingTime = 200;
    private volatile Generation generation = new Generation();



    /**
     * Updates state on barrier trip and wakes up everyone.
     * Called only while holding lock.
     */
    private void nextGeneration() {
        // signal completion of last generation
        trip.signalAll();
        // set up next generation
        count = parties;
    }



    private int dowait() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Generation g = generation;

            if (Thread.interrupted()) {
                throw new InterruptedException();
            }

            int index = --count;
            if (index == 0) {  // tripped
                nextGeneration();
                return 0;
            }

            // loop until tripped, interrupted, or timed out
            for (;;) {
                try {
                    //first thread waiting
                    if (index == parties - 1){
                        long nanos = trip.awaitNanos(maxWaitingTime);
                        if(nanos <= 0L) {
                            //timeout
                            nextGeneration();
                        }
                    }
                    else {
                        trip.await();
                    }
                } catch (InterruptedException ie) {
                    if (g == generation) {
                        nextGeneration();
                        throw ie;
                    } else {
                        // We're about to finish waiting even if we had not
                        // been interrupted, so this interrupt is deemed to
                        // "belong" to subsequent execution.
                        Thread.currentThread().interrupt();
                    }
                }
                return index;


            }
        } finally {
            lock.unlock();
        }
    }


//    public MergeCyclicCounter(int parties, long timeout, TimeUnit unit) {
//        if (parties <= 0) throw new IllegalArgumentException();
//        this.parties = parties;
//        this.count = parties;
//        this.maxWaitingTime = unit.toNanos(timeout);
//    }
//    public MergeCyclicCounter(){
//        new MergeCyclicCounter(5, 200, TimeUnit.MILLISECONDS);
//    }



    public int getParties() {
        return parties;
    }


    public int await() throws InterruptedException {
        return dowait();
    }







    public void reset() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try { // break the current generation
            nextGeneration(); // start a new generation
        } finally {
            lock.unlock();
        }
    }


    public int getNumberWaiting() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return parties - count;
        } finally {
            lock.unlock();
        }
    }
}

