public class SortTestExample {

    public static void looper(ISort sorter, int size){
        //TODO: implement this.
        KeyValuePair[] random10000 = inputSorted(size);
        // change as per type of array we want to check
        double timing = 0;
        for (int i = 0; i < 10; i++){
            StopWatch time2 = new StopWatch();
            time2.start();
            sorter.sort(random10000);
            time2.stop();
            timing += time2.getTime();
            // get 10 timings on loop for each test size
        }
        System.out.println(size + "," + timing/10); // print out the average
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
            array[i] = new KeyValuePair(size - i, i);
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

    public static void main(String[] args) {
        SorterD sorter = new SorterD(); // can be initialized to whichever sorter we want to test
        for (int i = 1; i < 10; i++){
            looper(sorter, i*1000);
        }
        for(int i = 1; i <= 10; i++){
            looper(sorter, i*10000);
        }

    }
}

// SorterA has O(nlogn) growth with random arrays and is not stable,
// SorterA has O(nlogn) growth with reverse arrays and,
// O(nlogn) growth with sorted arrays
// Therefore, SorterA is quick sort since it is unstable and has nlogn growth with all
// array types.


// SorterB has O(n^2) growth time with random arrays and is stable,
// SorterB has O(n^2) growth with reversed arrays and,
// O(n) growth time with sorted arrays
// Sorter B is insertion sort because insertion sort is stable and its runtime grows a lot
// more with reversed arrays than with sorted arrays.

// SorterC is Dr. Evil because it fails checkSort and isStable

// SorterD has O(n^2) runtime growth with random arrays and is unstable
// SorterD has O(n^2) runtime growth with reversed arrays and
// O(n^2) growth with sorted arrays as well
// Therefore, SorterD is Selection sort since its unstable and it has O(n^2) growth
// regardless of whether the array is sorted, reversed or random.

// SorterE has O(nlogn) growth with random arrays and is stable
// SorterE has O(nlogn) growth with sorted and reversed arrays as well
// Therefore, SorterE is Merge Sort since it is stable and its runtime growth does not
// change much regardless of type of array.

// SorterF has O(n^2) growth with random arrays and is stable
// SorterF has O(n^2) growth with reversed arrays and
// O(n) growth with sorted arrays
// Therefore, SorterF is bubble sort because it is stable and its runtime with random and
// reversed arrays is a lot greater than its runtime with sorted arrays. To differentiate
// between bubble and insertion sort, I used the test case of a almost sorted array like
// [1, 2, 3, 4......, 0] which would take n time with insertion sort but
// n^2 time with bubble