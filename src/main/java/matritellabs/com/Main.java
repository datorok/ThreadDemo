package matritellabs.com;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.currentTimeMillis;

public class Main {

    private static class Check17DivisionThread extends Thread implements Subject {

        private long lowerLimit;
        private long upperLimit;
        private List<Observer> listenerList;

        public Check17DivisionThread(long lowerLimit, long upperLimit) {
            this.lowerLimit = lowerLimit;
            this.upperLimit = upperLimit;
            listenerList = new ArrayList<Observer>();
        }

        @Override
        public void run() {
            long result = divisivleBy17(lowerLimit, upperLimit);
            System.out.println(result + " divisor have been found");
            notifyAllObservers();
        }

        @Override
        public void registerObserver(Observer o) {
            listenerList.add(o);
        }

        @Override
        public void notifyAllObservers() {
            for (Observer o : listenerList) {
                o.update(this);
            }
        }
    }

    public static class ThreadListener implements Observer {

        private int updateCounter;
        private int numberOfObservedThreads;
        private long start;

        public ThreadListener(int numberOfObservedThreads, long start) {
            updateCounter = 0;
            this.numberOfObservedThreads = numberOfObservedThreads;
            this.start = start;
        }

        public int getUpdateCounter() {
            return updateCounter;
        }

        public int getNumberOfObservedThreads() {
            return numberOfObservedThreads;
        }

        public void setNumberOfObservedThreads(int numberOfObservedThreads) {
            this.numberOfObservedThreads = numberOfObservedThreads;
        }

        @Override
        public void update(Subject s) {

            updateCounter++;
            System.out.println("updateCounter= "+updateCounter);
            if(updateCounter==numberOfObservedThreads){
                System.out.println(currentTimeMillis()-start+" ms was the total running time");
            }
        }
    }

    private static long divisivleBy17(long lowerLimit, long upperLimit) {

        long before = currentTimeMillis();

        long result = 0L;
        for (long i = lowerLimit; i <= upperLimit; i++) {
            if (i % 17L == 0L) {
                result++;
            }
        }

        long after = currentTimeMillis();
        System.out.println((after - before) + "ms");
        return result;
    }

    public static void main(String[] args) {

        //create a version that has a variable that defines the number of threads to use
        //and divide the number range to that many parts and run the calculation in threads!
        //replace the isAlive() polling part with Listener/Observer method

        Scanner sc = new Scanner(System.in);
        System.out.println("How much threads do you want to create?");
        int numberOfThreads = sc.nextInt();

        ThreadListener threadListener = new ThreadListener(numberOfThreads, currentTimeMillis());

        long batch = 10000000000L / numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++) {

            Check17DivisionThread threadTemp = new Check17DivisionThread(i * batch, (i + 1) * batch);
            threadTemp.registerObserver(threadListener);
            threadTemp.start();

        }
    }
}

