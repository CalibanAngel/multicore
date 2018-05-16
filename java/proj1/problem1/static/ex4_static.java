class MyClassStatic extends Thread {
    private int from;
    private int to;
    private int id;
    public int ret = 0;

    MyClassStatic(int from, int to, int id) {
        this.from = from;
        this.to = to;
        this.id = id;
    }

    private boolean isPrime(int x) {
        if (x % 2 == 0) return false; // If even number return false
        double q = Math.floor(Math.sqrt(x));

        for (int i = 3; i <= q; i += 2) { // start at 3 because first odd number and +2 to only get odd number
            if ((x % i == 0) && (i != x)) return false;
        }
        return true;
    }

    public void run() {
        long startTime = System.currentTimeMillis();

        for (int i = this.from; i < this.to; i++) {
            if (this.isPrime(i) == true) this.ret += 1;
        }
        long endTime = System.currentTimeMillis();
        long timeDiff = endTime - startTime;

        System.out.println("Execution Time in thread " + this.id + " : " + timeDiff + "ms");
    }
}

class ex4_static {
    private static final int NUM_END = 200000;
    private static int NUM_THREAD = 4;

    public static void main(String[] args) {
        int counter = 0;
        if (args.length == 1) {
            NUM_THREAD = Integer.parseInt(args[0]);
        }
        MyClassStatic[] myClasses = new MyClassStatic[NUM_THREAD];

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < NUM_THREAD; ++i) {
            if (i == NUM_THREAD - 1) {
                myClasses[i] = new MyClassStatic((NUM_END / NUM_THREAD) * i, (NUM_END / NUM_THREAD) * (i + 1) + (NUM_END % NUM_THREAD), i);
            } else {
                myClasses[i] = new MyClassStatic((NUM_END / NUM_THREAD) * i, (NUM_END / NUM_THREAD) * (i + 1), i);
            }
            myClasses[i].start();
        }
        for (int i = 0; i < NUM_THREAD; ++i) {
            try {
                myClasses[i].join();
                counter += myClasses[i].ret;
            } catch (InterruptedException e) {

            }
        }

        long endTime = System.currentTimeMillis();
        long timeDiff = endTime - startTime;

        System.out.println("Execution Time : " + timeDiff + "ms");
        System.out.println("1..." + NUM_END + " prime# counter=" + counter + "\n");
    }
}