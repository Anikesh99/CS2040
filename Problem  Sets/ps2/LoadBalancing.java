/**
 * Contains static routines for solving the problem of balancing m jobs on p processors
 * with the constraint that each processor can only perform consecutive jobs.
 */
public class LoadBalancing {

    /**
     * Checks if it is possible to assign the specified jobs to the specified number of processors such that no
     * processor's load is higher than the specified query load.
     *
     * @param jobSize the sizes of the jobs to be performed
     * @param queryLoad the maximum load allowed for any processor
     * @param p the number of processors
     * @return true iff it is possible to assign the jobs to p processors so that no processor has more than queryLoad load.
     */
    public static boolean feasibleLoad(int[] jobSize, int queryLoad, int p) {
        int counter = 1;
        int current_jobs = 0;

        for (int i = 0; i < jobSize.length; i++) {
            current_jobs += jobSize[i];
            if (jobSize[i] > queryLoad) {
                return false;
            }
            if (current_jobs > queryLoad) {
                current_jobs = jobSize[i];
                counter++;
            }
            if (counter > p) {
                return false;
            }
        }
        //Runtime: O(n)
        return true;
    }

    /**
     * Returns the minimum achievable load given the specified jobs and number of processors.
     *
     * @param jobSize the sizes of the jobs to be performed
     * @param p the number of processors
     * @return the maximum load for a job assignment that minimizes the maximum load
     */
    public static int findLoad(int[] jobSize, int p) {
        // TODO: Implement this
        if (jobSize.length == 1) {
            return jobSize[0];
        }

        int start = 0;
        int end = 99999;
        int mid = (start + end)/2;
        for(int i = 0; i < 999999; i++){
            if((feasibleLoad(jobSize, mid - 1, p) == false) && (feasibleLoad(jobSize, mid, p) == true)){
                return mid;
            }
            if((feasibleLoad(jobSize, mid - 1, p) == true) && (feasibleLoad(jobSize, mid, p) == true)){
                end = mid;
            }
            if (feasibleLoad(jobSize, mid, p) == false){
                start = mid;
            }
            mid = (start + end)/2;
        }
        // Runtime = O(log n)
        return 0;
    }


    // These are some arbitrary testcases.
    public static int[][] testCases = {
            {1, 3, 5, 7, 9, 11, 10, 8, 6, 4},
            {67, 65, 43, 42, 23, 17, 9, 100},
            {4, 100, 80, 15, 20, 25, 30},
            {2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83},
            {7}
    };

    /**
     * Some simple tests for the findLoad routine.
     */
    public static void main(String[] args) {
        for (int p = 1; p < 30; p++) {
            System.out.println("Processors: " + p);
            for (int[] testCase : testCases) {
                System.out.println(findLoad(testCase, p));
            }
        }
    }
}
