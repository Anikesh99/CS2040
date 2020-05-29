/**
 * A class to encapsulate the code tree
 */

/**
 Discussed with:
 1. Ambrose Liew
 2. Ernest Lim
 3. Kendrew Chan
 */
public class UniformTree extends BTree implements ITree {

    // Constructor: builds a tree from a collection of words
    UniformTree(Byte[] datawords) {
        buildTree(datawords);
    }

    // Constructor: build the tree by reading in from a file
    UniformTree(ReadFileWrapper inFile) {
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
     *
     * @param datawords
     * @return
     */


    private void buildTree (Byte[] datawords){
        int middle;
        if (datawords.length % 2 == 0){
            middle = datawords.length / 2 - 1;
        } else {
            middle = datawords.length / 2;
        }
        // different middle indices for odd and even lengths
        TreeNode node = new TreeNode (datawords[middle]);
        // making the main root
        super.root = node;
        helper(datawords, node,0, datawords.length - 1); //  calling the recursive helper
    }

    private void helper (Byte[] dataWords, TreeNode node,int fIx, int lIx) {
        int length = lIx - fIx + 1; // Actual length is difference in indices + 1
        if (length == 2) {
            // base case
            node.setLeft (new TreeNode(dataWords[fIx]));
            node.setRight (new TreeNode(dataWords[lIx]));
        } else if (length > 2) {
            int middle;
            if (length % 2 == 0){
                middle = length / 2 - 1;
            } else {
                middle = length / 2;
            }
            // different middle value based on odd or even length
            int midInd = middle + fIx; // middle index is middle value + first index
            TreeNode left = new TreeNode (dataWords[(fIx + midInd) / 2]);
            TreeNode right = new TreeNode ((dataWords[(midInd + lIx + 1) / 2 ]));
            // making the left and right nodes
            node.setLeft (left);
            node.setRight (right);
            helper (dataWords, left, fIx, midInd); // recurse on left node
            helper (dataWords, right, midInd + 1, lIx); // recurse on right node
        }
    }

    /**
     * This function takes a code and looks it up in the tree.
     * If the result is a leaf (at the proper depth), it returns
     * the data byte associated with that leaf.  Otherwise it returns null.
     *
     * @param code
     * @param bits
     * @return
     */
    public Byte query (boolean[] code, int bits) {
        TreeNode tRoot = super.root;
        for (int i = 0; i < bits; i++){
            // bits is the length of code lol
            if (tRoot == null){
                //in case the root leaves the tree itself
                return null;
            }
            if (code[i]){
                // if true go right
                tRoot = tRoot.getRight();
            } else {
                // otherwise go left
                tRoot = tRoot.getLeft();
            }
        }
        if (tRoot.isLeaf()){
            // we only care if it is in a leaf otherwise the key doesnt matter
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
    public boolean[] queryCode (byte key) {
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
                for (int i = 0; i < length - 1; i++){
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