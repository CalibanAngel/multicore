package ex_2_recursive;

class SumThread extends Thread {
    private int from, to; // fields for communicating inputs
    private int SEQUENTIAL_CUTOFF = 1000;
    private int[] arr;
    int ret = 0; // for communicating result

    SumThread(int[] arr, int from, int to) {
        this.from = from;
        this.to = to;
        this.arr = arr;
    }

    public void run() {
        if ((this.to - this.from) < this.SEQUENTIAL_CUTOFF) {
            for (int i = this.from; i < this.to; i++) {
                this.ret += this.arr[i];
            }
        } else {
            SumThread left = new SumThread(this.arr, this.from, (this.from + this.to) / 2);
            SumThread right = new SumThread(this.arr, (this.from + this.to) / 2, this.to);
            left.start();
            right.run();
            try {
                left.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ret = left.ret + right.ret;
        }
    }
}

public class ex_2_recursive {
    private static int NUM_END = 10000;

    public static void main(String[] args) {
        int[] int_arr = new int [NUM_END];
        int s;

        for (int i = 0; i < NUM_END; i++) int_arr[i] = i + 1;
        s = sum(int_arr);
        System.out.println("sum = " + s) ;
    }

    static int sum(int[] arr) {
        SumThread sumThread = new SumThread(arr, 0, arr.length);
        sumThread.run();
        return sumThread.ret;
    }
}
