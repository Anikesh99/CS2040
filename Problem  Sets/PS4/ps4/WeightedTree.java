import java.util.*;
/**
 Discussed with:
 1. Ambrose Liew
 2. Ernest Lim
 3. Kendrew Chan
 */

/**
 * A class to encapsulate the code tree
 */
public class WeightedTree extends BTree implements ITree{
    /**
     * Builds the tree
     * @param data
     * @return
     */
    WeightedTree(HashMap<Byte, Integer> data){
        buildTree(data);
    }

    // Build the tree by reading in from a file
    WeightedTree(ReadFileWrapper inFile){
        readTree(inFile);
    }

    /*************************************************
     *
     * These are the routines to implement.
     * They build the tree from an array of words,
     * and query the tree.
     *
     *************************************************/
    /**
     * Builds the tree
     * @param data
     * @return
     */
    private void buildTree (HashMap<Byte, Integer> data){
        Map <Byte, Integer> unsortedMap = data;
        Map <Byte, Integer> treeMap = new TreeMap <> (unsortedMap);
        // a treeMap sorts a map by its definition
        List <Pair> sorted = new ArrayList<>();
        // using a arrayList of pairs
        Iterator<Byte> iterator = treeMap.keySet().iterator();
        while (iterator.hasNext()){
            byte now = iterator.next();
            sorted.add (new Pair (now, data.get(now)));
            // adding the elements to an arrayList of pairs to make it easy to iterate through
        }

        int[] middle = finder(sorted, 0, sorted.size() - 1);
        //find the main root
        Pair curr = new Pair(sorted.get(middle[0]).data, middle[1]); // current node data and weight
        TreeNode root = new TreeNode(curr.data, curr.weight);
        super.root = root;
        // initialize the main root
        helper(sorted, root,0, sorted.size() - 1); // start that recursion
    }

    private int[] finder(List<Pair> list, int fIx, int lIx) {
        if (fIx == lIx){
            //if first and last are the same just return the first index and its weight
            return new int[]{fIx, list.get(fIx).weight};
        } else {
            int total = 0;
            for (int i = fIx; i <= lIx; i++) {
                // find the total weight of the tree
                total += list.get(i).weight;
            }
            int current = 0;
            int index = fIx;
            for (int i = fIx; i <= lIx; i++) {
                if (i == lIx){
                    // check if the i value is reaching the max one and return i - 1
                    // to prevent overflow
                    i--;
                    int[] array = {i, total};
                    return array;
                }
                current += list.get(i).weight;
                if (current < (2.0 / 3.0) * total) {
                    // keep increasing index if current weight less than 2/3 total weight
                    index++;
                } else if(index == fIx) {
                    // if the fIx.weight is greater than the 2/3 limit then return the index
                    // as it is
                    break;
                } else {
                    // we don't want to include the weight of the element that pushes the current
                    // over the limit
                    index --;
                    break;
                }
            }
            int[] array = {index, total};
            return array;
        }
    }

    private void helper(List<Pair> list, TreeNode currRoot, int fIx, int lIx) {
        // recurse on the helper alone because I didn't want to keep re-updating my root
        int length = lIx - fIx;
        if (length <= 1) {
            //base case
            TreeNode leftNode = new TreeNode(list.get(fIx).data, list.get(fIx).weight);
            TreeNode rightNode = new TreeNode(list.get(lIx).data, list.get(lIx).weight);
            currRoot.setLeft(leftNode);
            currRoot.setRight(rightNode);
        } else if (length > 1) {
            // for some reason I can't see need to make this an else if and not else
            int index = finder(list, fIx, lIx)[0];
            int[] leftP = finder(list, fIx, index);
            // getting the pair for the left part of the tree
            Pair left = list.get(leftP[0]);
            // the pair of left middle point
            TreeNode leftNode = new TreeNode(left.data, leftP[1]); // leftP[1] gives the total weight of the left
            //making that left node
            int[] rightP = finder(list, index + 1, lIx);
            Pair right = list.get(rightP[0]);
            //same as left
            TreeNode rightNode = new TreeNode(right.data, rightP[1]);
            currRoot.setLeft(leftNode);
            currRoot.setRight(rightNode);
            // set the left and right for the treeRoot

            helper(list, leftNode, fIx, finder(list, fIx, lIx)[0]); // recurse on left subtree
            helper(list, rightNode, finder(list, fIx, lIx)[0] + 1, lIx); // recurse on right one
        }
    }

    /**
     * This function takes a code and looks it up in the tree.
     * If the result is a leaf (at the proper depth), it returns
     * the data byte associated with that leaf.  Otherwise it returns null.
     * @param code
     * @param bits
     * @return
     */
    public Byte query(boolean[] code, int bits) {
        TreeNode tRoot = super.root;
        for (int i = 0; i < bits; i++){
            // bits is the length of code lol
            if(tRoot == null){
                //in case the root leaves the tree itself
                return null;
            }
            if (code[i]){
                //if true go right
                tRoot = tRoot.getRight();
            } else {
                //otherwise go left
                tRoot = tRoot.getLeft();
            }
        }
        if (tRoot.isLeaf()){
            //we only care if it is in a leaf otherwise the key doesn't matter
            return tRoot.data;
        }
        return null;
    }

    /**
     * Returns the codeword associated with the symbol key
     * or null if not in the tree.
     * @param key
     * @return
     */
    public boolean[] queryCode(byte key) {
        int length = 1;
        int index = 0; // start my index to keep count at 0
        boolean[] code = new boolean[length]; //initial length assigned to my list
        TreeNode tRoot = super.root;

        while (!tRoot.isLeaf()) {
            if (index == length){
                // if the index is equal to the length then we are running out of space
                // so make a new array for storing the next bit and I know I can use array
                // lists but I kinda got lazy
                length++;
                boolean[] newBools = new boolean[length];
                for(int i = 0; i < length - 1; i++){
                    newBools[i] = code[i];
                }
                code = newBools;
            }
            if (key > tRoot.data) {
                // If greater then need to go right
                tRoot = tRoot.getRight();
                code[index] = true;
                index++;
            } else {
                //If less then need to go left
                tRoot = tRoot.getLeft();
                code[index] = false;
                index++;
            }
        }
        if (key == tRoot.data) {
            //return the boolean array when you find the key
            return code;
        }
        return null;
    }
}
