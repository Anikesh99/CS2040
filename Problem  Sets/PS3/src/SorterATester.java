public class SorterATester {
    public static void main(String args[]) {
        KeyValuePair[] reverseSorted10000 = inputReverse(10000);
        KeyValuePair[] reverseSorted50000 = inputReverse(30000);
        KeyValuePair[] sorted10000 = inputSorted(10000);
        KeyValuePair[] sorted50000 = inputSorted(30000);
//        KeyValuePair[] random10000 = inputRandom(10000);
//        KeyValuePair[] random50000 = inputRandom(50000);
        SorterF sorter = new SorterF();
        double[] timings = new double[6];
        for(int i = 0; i < 10; i++){
            StopWatch time0 = new StopWatch();
            time0.start();
            sorter.sort(reverseSorted10000);
            time0.stop();
            timings[0] += time0.getTime();

            StopWatch time1 = new StopWatch();
            time1.start();
            sorter.sort(reverseSorted50000);
            time1.stop();
            timings[1] += time1.getTime();

            StopWatch time2 = new StopWatch();
            time2.start();
            sorter.sort(sorted10000);
            time2.stop();
            timings[2] += time2.getTime();

            StopWatch time3 = new StopWatch();
            time3.start();
            sorter.sort(sorted50000);
            time3.stop();
            timings[3] += time3.getTime();

//            StopWatch time4 = new StopWatch();
//            time4.start();
//            sorter.sort(random10000);
//            time4.stop();
//            timings[4] += time4.getTime();
//            random10000 = inputRandom(10000);
//
//            StopWatch time5 = new StopWatch();
//            time5.start();
//            sorter.sort(random50000);
//            time5.stop();
//            timings[5] += time5.getTime();
//            random50000 = inputRandom(50000);
        }

        System.out.println("Timing for reverse sorted array of size 10000: " + timings[0]/10);
        System.out.println("Timing for reverse sorted array of size 50000: " + timings[1]/10);
        System.out.println("Ratio of Reverse: " + timings[1]/timings[0]);
        System.out.println("Timing for sorted array of size 10000: " + timings[2]/10);
        System.out.println("Timing for sorted array of size 50000: " + timings[3]/10);
        System.out.println("Ratio of Sorted: " + timings[3]/timings[2]);
        System.out.println("Timing for random array of size 10000: " + timings[4]/10);
        System.out.println("Timing for random array of size 50000: " + timings[5]/10);
        System.out.println("Ratio of Random: " + timings[5]/timings[4]);
    }

    public static KeyValuePair[] inputSorted(int size){
        KeyValuePair[] array = new KeyValuePair[size];
        for(int i = 0; i < size; i++){
            array[i] = new KeyValuePair(i, i);
        }
        return array;
    }

    public static KeyValuePair[] inputReverse(int size){
        KeyValuePair[] array = new KeyValuePair[size];
        for(int i = 0; i < size; i++){
            array[i] = new KeyValuePair(size- i, i);
        }
        return array;
    }

    public static KeyValuePair[] inputRandom(int size){
        KeyValuePair[] array = new KeyValuePair[size];
        for(int i = 0; i < size; i++){
            array[i] = new KeyValuePair((int)(Math.random() * (size + 1)), i);
        }
        return array;
    }

}
