package ex4;

import java.util.concurrent.atomic.AtomicInteger;

class AtomicIntegerTest extends Thread {
    private AtomicInteger at;

    AtomicIntegerTest(AtomicInteger at) {
        this.at = at;
    }

    @Override
    public void run() {
        int counter = at.getAndAdd(1);
        System.out.println(Thread.currentThread().getName() + "  / Counter getAndAdd : " + counter);
        at.set(5);
        counter = at.get();
        System.out.println(Thread.currentThread().getName() + "  / Counter set : " + counter);
        counter = at.addAndGet(1);
        System.out.println(Thread.currentThread().getName() + "  / Counter addAndGet : " + counter);
    }
}

public class ex4 {
    static private final int THREAD_NUMBER = 2;
    private static AtomicInteger at = new AtomicInteger(0);


    public static void main(String[] args) {
        AtomicIntegerTest threads[] = new AtomicIntegerTest[THREAD_NUMBER];

        for (int i = 0; i < THREAD_NUMBER; i++) {
            threads[i] = new AtomicIntegerTest(at);
            threads[i].start();
        }
    }
}
