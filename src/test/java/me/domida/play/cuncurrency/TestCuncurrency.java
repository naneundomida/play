package me.domida.play.cuncurrency;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.*;

public class TestCuncurrency {

    @Test
    void simpleExecutorTest() throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> submit = executorService.submit(() -> {
            String helloPallPrograming = "hello pall programming";
            return helloPallPrograming;
        });
        Assertions.assertThat("hello pall programming").isEqualTo(submit.get());
    }

    @Test
    void notDeamonThreadPoolTest() throws NoSuchAlgorithmException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            int randomMiliSecond = Math.abs(SecureRandom.getInstanceStrong().nextInt() % 10000);
            executorService.submit(() -> {
                System.out.println("bingle bingle start " + Thread.currentThread().getName());
                try {
                    System.out.println(Thread.currentThread().getName()+ " sleep "+randomMiliSecond);
                    Thread.sleep(randomMiliSecond);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("bingle bingle end "+ Thread.currentThread().getName());
            });
        }
    }

    @Test
    void threadnotWakeUpSleepWhenMainThreadExit() throws NoSuchAlgorithmException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            int randomMiliSecond = Math.abs(SecureRandom.getInstanceStrong().nextInt() % 10000);
            executorService.submit(() -> {
                System.out.println("bingle bingle start " + Thread.currentThread().getName());
                try {
                    System.out.println(Thread.currentThread().getName()+ " sleep "+randomMiliSecond);
                    Thread.sleep(randomMiliSecond);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("bingle bingle end "+ Thread.currentThread().getName());
            });
        }
        System.out.println(Thread.currentThread() + " end");
    }

    @Test
    void deamonThreadPoolTest() throws NoSuchAlgorithmException, InterruptedException {

        System.out.println(Thread.currentThread() + " start");

        ExecutorService executorService = Executors.newFixedThreadPool(10, getNotDaemonThreadFactory());

        for (int i = 0; i < 10; i++) {
            int randomMiliSecond = Math.abs(SecureRandom.getInstanceStrong().nextInt() % 10000);
            executorService.submit(() -> {
                System.out.println("bingle bingle start " + Thread.currentThread().getName());
                try {
                    System.out.println(Thread.currentThread().getName()+ " sleep "+randomMiliSecond);
                    Thread.sleep(randomMiliSecond);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("bingle bingle end "+ Thread.currentThread().getName());
            });
        }

        Thread.sleep(5000);
        System.out.println(Thread.currentThread() + " end");
    }

    private ThreadFactory getNotDaemonThreadFactory() {
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(false);
                //데몬 쓰레드는 일반 쓰레드가 실행 중일 때에만 동작하며 일반 쓰레드가 종료되면 데몬 쓰레드는 강제 종료된다.
                //true면 강제 종료, false명 강제 종료가 아니다.
                return thread;
            }
        };
        return threadFactory;
    }
}
