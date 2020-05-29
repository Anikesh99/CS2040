/**
 * The Optimization class contains a static routine to find the maximum in an array that changes direction at most once.
 */
public class Optimization {

    /**
     * A set of test cases.
     */
    static int[][] testCases = {
            {1, 3, 5, 7, 9, 11, 10, 8, 6, 4},
            {67, 65, 43, 42, 23, 17, 9, 100},
            {4, -100, -80, 15, 20, 25, 30},
            {2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83}
    };

    /**
     * Returns the maximum item in the specified array of integers which changes direction at most once.
     *
     * @param dataArray an array of integers which changes direction at most once.
     * @return the maximum item in data Array
     */
    public static int searchMax(int[] dataArray) {
        // TODO: Implement this
        int start = 0;
        int end = dataArray.length - 1;
        if(dataArray.length == 1){
            System.out.println(dataArray[0]);
            return dataArray[0];
        }
        if(dataArray[0] > dataArray[1]){
            return Math.max(dataArray[0], dataArray[end]);
        }
        //O(1) runtime when the maximum is at the extremes
        int max = Math.max(dataArray[0], dataArray[end]);
        while (true){
            int mid = (start + end) / 2;
            if((dataArray[mid] >= dataArray[mid + 1]) && (dataArray[mid] >= dataArray[mid - 1])){
                if(max < dataArray[mid]){
                    max = dataArray[mid];
                }
                break;
            } else if (mid == end - 1){
                break;
            } else if (start >= end){
                break;
            } else if (dataArray[mid] < dataArray[mid + 1]){
                start = mid;
            } else {
                end = mid;
            }
        }
        // O(log n) runtime when the maximum is the turning point inside the array
        return max;
    }

    /**
     * A routine to test the searchMax routine.
     */
    public static void main(String[] args) {
        for (int[] testCase : testCases) {
            System.out.println(searchMax(testCase));
        }
    }
}
