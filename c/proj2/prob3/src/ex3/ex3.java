package ex3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class ReadWriteList<E> {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();
    private final List<E> list = new ArrayList<>();

    @SafeVarargs
    ReadWriteList(E... initList) {
        this.list.addAll(Arrays.asList(initList));
    }

    void add(E item) {
        writeLock.lock();
        try {
            list.add(item);
        } finally {
            writeLock.unlock();
        }
    }

    E get(int index) {
        readLock.lock();
        try {
            return list.get(index);
        } finally {
            readLock.unlock();
        }
    }

    int size() {
        readLock.lock();

        try {
            return list.size();
        } finally {
            readLock.unlock();
        }
    }
}

class Writer extends Thread {
    private ReadWriteList<Integer> sharedList;

    Writer(ReadWriteList<Integer> sharedList) {
        this.sharedList = sharedList;
    }

    @Override
    public void run() {
        Random random = new Random();
        int number = random.nextInt(100);
        sharedList.add(number);

        try {
            Thread.sleep(100);
            System.out.println(Thread.currentThread().getName() + " -> put: " + number);
        } catch (InterruptedException e ) {
            e.printStackTrace();
        }
    }
}

class Reader extends Thread{
    private ReadWriteList<Integer> sharedList;

    Reader(ReadWriteList<Integer> sharedList) {
        this.sharedList = sharedList;
    }

    @Override
    public void run() {
        Random random = new Random();
        int index = random.nextInt(sharedList.size());
        Integer number = sharedList.get(index);

        System.out.println(Thread.currentThread().getName() + " -> get: " + number);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e ) {
            e.printStackTrace();
        }
    }
}

public class ex3 {
    static private final int READER_NUMBER = 5;
    static private final int WRITER_NUMBER = 2;

    public static void main(String[] args) {
        Random random = new Random();
        Integer[] initialElements = {random.nextInt(100), random.nextInt(100), random.nextInt(100), random.nextInt(100)};

        ReadWriteList<Integer> sharedList = new ReadWriteList<>(initialElements);

        for (int i = 0; i < WRITER_NUMBER; i++) {
            new Writer(sharedList).start();
        }

        for (int i = 0; i < READER_NUMBER; i++) {
            new Reader(sharedList).start();
        }
    }
}
