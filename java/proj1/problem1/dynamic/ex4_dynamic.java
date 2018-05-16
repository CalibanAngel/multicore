class MyClassDynamic extends Thread {
    public static int nextNumber = 1; // start at 1 because of every prime number add odd (except 2)
    private int NumEnd;
    private int id;
    public int ret = 0;

    MyClassDynamic(int NumEnd, int id) {
        this.NumEnd = NumEnd;
        this.id = id;
    }

    private boolean isPrime(int x) {
        if ((x + 1) == 2) { // 2 is the first prime and only even number
            this.ret += 1;
            return false;
        }
        double q = Math.floor(Math.sqrt(x));
        for (int i = 3; i <= q; i += 2) { // start at 3 because first odd number and +2 to only get odd number
            if (x % i == 0) return false;
        }
        return true;
    }

    public static synchronized int getNextNumber() {
        int tmp = nextNumber;
        nextNumber += 2;
        return tmp;
    }

    public void run() {
        int currentNb = this.getNextNumber();

        long startTime = System.currentTimeMillis();
        while (currentNb < this.NumEnd) {
            if (this.isPrime(currentNb) == true) this.ret += 1;
            currentNb = this.getNextNumber();
        }
        long endTime = System.currentTimeMillis();
        long timeDiff = endTime - startTime;

        System.out.println("Execution Time in thread " + this.id + " : " + timeDiff + "ms");
    }
}

class ex4_dynamic {
    private static final int NUM_END = 200000;
    private static int NUM_THREAD = 4;

    public static void main(String[] args) {
        int counter = 0;
        if (args.length == 1) {
            NUM_THREAD = Integer.parseInt(args[0]);
        }
        MyClassDynamic[] myClasses = new MyClassDynamic[NUM_THREAD];

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < NUM_THREAD; ++i) {
            myClasses[i] = new MyClassDynamic(NUM_END, i);
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
