package com.jvm.memorymgn;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * VM Args:-Xss2M(这时候不妨设置大些)
 */
public class StackOutOfMemory {
    private boolean flag;

    private StackOutOfMemory() {
        this.flag = true;
    }

    private void stackMemoryLeakByThread() throws Exception {
        final CountDownLatchWrapper countDownLatchWrapper = new CountDownLatchWrapper();

        int threadCount = 0;
        try {
            while (flag) {
                Thread thread = new Thread(new Runnable() {
                    public void run() {
                        try {
                            synchronized (StackOutOfMemory.class) {
                                StackOutOfMemory.class.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        countDownLatchWrapper.countDownLatch.countDown();
                    }
                });
                thread.start();

                threadCount++;
            }
        } catch (OutOfMemoryError outOfMemoryError) {
            outOfMemoryError.printStackTrace();

            countDownLatchWrapper.countDownLatch = new CountDownLatch(threadCount);

            this.flag = false;

            synchronized (StackOutOfMemory.class) {
                StackOutOfMemory.class.notifyAll();
            }
        }

        System.out.println("created:" + threadCount + " thread has been created! " + new SimpleDateFormat("mm:ss.SSS").format(new Date()));

        countDownLatchWrapper.countDownLatch.await();

        System.out.println("all thread has finished! " + new SimpleDateFormat("mm:ss.SSS").format(new Date()));
    }

    private static class CountDownLatchWrapper {
        private CountDownLatch countDownLatch;
    }

    public static void main(String[] args) throws Throwable {
        StackOutOfMemory stackOutOfMemory = new StackOutOfMemory();
        stackOutOfMemory.stackMemoryLeakByThread();
    }
}
