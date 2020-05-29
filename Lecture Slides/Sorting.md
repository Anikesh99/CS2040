Bubble Sort

repeat(until no swaps)

- Runtime O(n)  best case scenario (already sorted)
- Runtime O(n^2) average case scenario
- Runtime O(n^2) worst case scenario (reversed)
- Loop invariant - After every iteration the largest element is at the end



Selection Sort

Find minimum element A[j] in A[j....n]

- Runtime O(n^2) (all cases)
- Loop invariant the smallest j elements are always in place after j iterations



Insertion Sort

- Loop Invariant the first j elements are always sorted
- Runtime O(n^2) (Worst case) (reversed)
- Runtime O(n) (Best case) (sorted)



All these algorithms are in place



Merge Sort

Divide-and-conquer

- Runtime O(nlogn)
- Merge has a runtime of O(n)



Techniques for solving recurrences

1. Guess and verify (via induction)
2. Draw recursion tree
3. Use Master theorm



MergeSort analysis

- cn(merge) = T(n/2)(recursive sort) + T(n/2) = T(n/4) + T(n/4) + T(n/4) + T(n/4) = ......  = 2T(n/2) + cn. Hence total cost is cnlogn where n is the number of elements and log n is the height of the tree
- 