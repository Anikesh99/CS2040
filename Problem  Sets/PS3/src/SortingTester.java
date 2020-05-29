public class SortingTester {

	public static boolean helper(ISort sorter, int size){
		KeyValuePair[] array = input(size);
//		StopWatch time = new StopWatch();
//		time.start();
		sorter.sort(array);

		// sorting the array
		for(int i = 0; i < size - 1; i++){
			if(array[i].getKey() > array[i + 1].getKey()){
				// if array[i+1] has a key lesser than array[i] the array isn't sorted
				return false;
			}
		}
		return true;
	}
	public static boolean checkSort(ISort sorter, int size){
		//TODO: implement this.
		for(int i = 1; i < 10; i++){
			//Looping the helper 10 times in order to generate a large enough sample size to see which sorts are fake
			if(helper(sorter, size) == false){
				return false;
			}
		}
		return true;
	}

	public static KeyValuePair[] input(int size){
		KeyValuePair[] array = new KeyValuePair[size];
		for(int i = 0; i < size; i++){
			//getting random input for the keys in range 0 to size and pairing it with the value of i
			array[i] = new KeyValuePair((int)(Math.random() * (size + 1)), i);
		}
		return array;
	}

	public static boolean isStable(ISort sorter, int size){
		//TODO: implement this.
		KeyValuePair[] array = input(size);

		sorter.sort(array); // sorting the array

		for (int i = 0; i < size - 1; i++){
			if (array[i].getKey() == array[i + 1].getKey()){
				if (array[i].getValue() > array[i + 1].getValue()){
					// since the index values have been assigned in order from 0 to size - 1
					return false;
				}
			}
		}
		return true;
  }


	public static void main(String[] args){
		//TODO: implement some sorting tests:
		SorterE sorter = new SorterE();
		System.out.println(isStable(sorter, 10000));
	}
}