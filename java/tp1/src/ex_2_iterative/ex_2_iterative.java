package ex_2_iterative;

class SumThread extends Thread {
    private int from, to; // fields for communicating inputs
    private int[] arr;
    int ret = 0; // for communicating result

    SumThread(int[] arr, int from, int to) {
        this.from = from;
        this.to = to;
        this.arr = arr;
    }

    public void run() {
        // insert your code here
        for (int i = this.from; i < this.to; i++) {
            ret += arr[i];
        }
    }
}

public class ex_2_iterative {
    private static int NUM_END = 10000;
    private static int NUM_THREAD = 4; // assume NUM_END is divisible by NUM_THREAD

    public static void main(String[] args) {
        if (args.length == 2) {
            NUM_THREAD = Integer.parseInt(args[0]);
            NUM_END = Integer.parseInt(args[1]);
        }
        int[] int_arr = new int [NUM_END];
        int s;

        for (int i = 0; i < NUM_END; i++) int_arr[i] = i + 1;
        s = sum(int_arr);
        System.out.println("sum = " + s) ;
    }

    static int sum(int[] arr) {
        // insert your code here
        SumThread[] sumThreads = new SumThread[NUM_THREAD];
        int ret = 0;
        for (int i = 0; i < NUM_THREAD; ++i) {
            sumThreads[i] = new SumThread(arr, (arr.length / NUM_THREAD) * i, (arr.length / NUM_THREAD) * (i + 1));
            sumThreads[i].start();
        }
        for (int i = 0; i < NUM_THREAD; ++i) {
            try {
                sumThreads[i].join();
                ret += sumThreads[i].ret;
            } catch (InterruptedException e) {

            }
        }
        return ret;
    }
}
