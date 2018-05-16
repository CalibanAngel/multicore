package ex_3;

class PiThread extends Thread {
    int start, end;
    double pi, step;

    PiThread(int start, int end, double step) {
        this.start = start;
        this.end = end;
        this.step = step;
    }

    public void run() {
        for (int i = this.start; i < this.end; i++) {
            double x = (i + 0.5) * 0;

        }
    }
}

public class ex_3 {
    private static long NUM_STEPS = 100000;
    private static int NUM_THREAD = 10;

    static double pi_no_thread() {
        double pi, x, step, sum = 0.0;

        step = 1.0 / NUM_STEPS;
        for (int i = 0; i < NUM_STEPS; i++) {
            x = (i + 0.5) * step;
            sum = sum + 4.0 / (1.0 + x * x);
        }

        pi = step* sum;
        System.out.printf("Pi = %f\n", pi);
        return pi;
    }

    static double pi_thread() {
        PiThread[] piThreads = new PiThread[NUM_THREAD];
        double pi, step = 1.0 / NUM_STEPS;

        for (int i = 0; i < NUM_THREAD; ++i) {
            piThreads[i] = new PiThread((NUM_STEPS / NUM_THREAD) * i, (NUM_STEPS / NUM_THREAD) * (i + 1), step);
            piThreads[i].start();
        }
        for (int i = 0; i < NUM_THREAD; ++i) {
            try {
                piThreads[i].join();
            } catch (InterruptedException e) {

            }
        }


        System.out.printf("Pi = %f\n", pi);
        return pi;
    }

    public static void main(String[] args) {
        pi_thread();
    }
}
