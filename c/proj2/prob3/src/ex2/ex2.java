package ex2;

import java.util.concurrent.Semaphore;

class SemaphoreTest extends Thread {
    private Semaphore semaphore;

    SemaphoreTest(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " : acquiring lock...");
            System.out.println(Thread.currentThread().getName() + " : available Semaphore permits now: " + semaphore.availablePermits());

            this.semaphore.acquire();
            System.out.println(Thread.currentThread().getName() + " : got the permit!");

            Thread.sleep(1000);

            System.out.println(Thread.currentThread().getName() + " : releasing lock...");
            semaphore.release();
            System.out.println(Thread.currentThread().getName() + " : available Semaphore permits now: " + semaphore.availablePermits());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

public class ex2 {
    static private final int THREAD_NUMBER = 4;

    public static void main(String[] args) {
        SemaphoreTest threads[] = new SemaphoreTest[THREAD_NUMBER];
        Semaphore semaphore = new Semaphore(THREAD_NUMBER / 2);

        System.out.println("Total available Semaphore permits : " + semaphore.availablePermits());
        for (int i = 0; i < THREAD_NUMBER; i++) {
            threads[i] = new SemaphoreTest(semaphore);
            threads[i].start();
        }
    }
}
