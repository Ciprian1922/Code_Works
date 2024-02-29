public class SumThread extends Thread {
    private int[] array;
    private int startIndex;
    private int endIndex;
    private long partialSum;

    public SumThread(int[] array, int startIndex, int endIndex) {
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.partialSum = 0;
    }

    public long getPartialSum() {
        return partialSum;
    }

    @Override
    public void run() {
        for (int i = startIndex; i <= endIndex; i++) {
            partialSum += array[i];
        }
    }

    public static void main(int[] inputArray, int numThreads) throws InterruptedException {
        int elementsPerThread = inputArray.length / numThreads;
        SumThread[] threads = new SumThread[numThreads];
        long totalSum = 0;

        //measure start time
        long startTime = System.currentTimeMillis();

        //create and start threads
        for (int i = 0; i < numThreads; i++) {
            int start = i * elementsPerThread;
            int end = (i == numThreads - 1) ? inputArray.length - 1 : start + elementsPerThread - 1;
            threads[i] = new SumThread(inputArray, start, end);
            threads[i].start();
        }

        //wait for all threads to finish
        for (SumThread thread : threads) {
            thread.join();
            totalSum += thread.getPartialSum();
        }

        //measure end time
        long endTime = System.currentTimeMillis();

        //output results
        System.out.println("Total sum: " + totalSum);
        System.out.println("Elapsed time: " + (endTime - startTime) + " milliseconds");
    }
}
