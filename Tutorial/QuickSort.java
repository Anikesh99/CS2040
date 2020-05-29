class QuickSort{
        private int[] array;
        
        public QuickSort(int[] array){
                this.array = array;
        }

        public void sort(){
            if(array == null || array.length == 0){
                    return;
             }
             quickSort(0, array.length - 1);
        }
             
        public void quickSort(int lower, int higher){
                int pivot = (lower + higher)/2;
                int i = lower;
                int j = higher;
                while (i<=j){
                        while(array[i] < pivot){
                                i++;
                        } 
                        while (array[j] > pivot){
                                j--;
                        }
                        if(i <= j){
                                int temp = array[i];
                                array[i] = array[j];
                                array[j] = temp;
                                i++;
                                j--;
                         }
                  }
                  if(lower < j){
                          quickSort(lower, j);
                  }
                  if (i < higher){
                          quickSort(i, higher);
                  }
         }

         public static void main(String args[]){
                 int[] input = {23,24,25,12,13,4,2,60};
                 QuickSort sorter = new QuickSort(input);
                 sorter.sort();
                 for(int i = 0; i < input.length; i++){
                         System.out.println(input[i]);
                  }
         }
}



                        

