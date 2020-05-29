//Collaborated with Ambrose Liew

import java.util.ArrayList;

public class Trie {

    final char WILDCARD = '.';
    final char STAR = '*';
    final char QUESTION = '?';
    final char PLUS = '+';
    final char EMPTY = '$';
    TrieNode root = new TrieNode(' ');
    TrieNode current;

    // TODO: Declare any instance variables you need here

    private class TrieNode {
        private final char letter;
        private TrieNode[] children = new TrieNode[257];

        TrieNode(char letter) {
            this.letter = letter;
        }

        public boolean hasChild(char c) {
            return children[c] != null;
        }
        public TrieNode getChild(TrieNode child) {
            return children[child.letter];
        }
        public void insertChild(TrieNode child) {
            children[child.letter] = child;
        }

        public boolean isLeaf(){
            return children == null;
        }

        public boolean isEnd() {
            return children[256] != null;
        }
        public void setEnd() {
            children[256] = new TrieNode(EMPTY);
        }
        // TODO: Create your TrieNode class here.
    }


    public Trie() {
        current = root;
    }

    // inserts string s into the Trie
    void insert(String s) {
        current = root;
        for (int i = 0; i < s.length(); i++) {
            // recurse down the string and keep moving the current down as we go
            TrieNode newLetter = new TrieNode(s.charAt(i));
            if (current.hasChild(s.charAt(i))) {
                // just move the current down when you can find the character
                current = current.getChild(newLetter);
            } else {
                // insert a new child when you cant find the character in the children of the current
                current.insertChild(newLetter);
                current = current.getChild(newLetter);
            }
        }
        current.setEnd();
    }

    // checks whether string s exists inside the Trie or not
    boolean contains(String s) {
        current = root;
        for (int i = 0; i < s.length(); i++) {
            if(current.hasChild(s.charAt(i)) == false) {
                return false;
                // if the current does not have a child with the same character as the string index, the string is not
                // in the trie
            }
            current = current.getChild(new TrieNode(s.charAt(i)));
        }
        if(current.isEnd()) {
            // the string must be ended by the '$'
            return true;
        }
        return false;
    }

    // Search for string with prefix matching the specified pattern sorted by lexicographical order.
    // Return results in the specified ArrayList.
    // Only return at most the first limit results.
    void prefixSearch(String s, ArrayList<String> results, int limit) {
        // TODO
        current = root;
        helper("", s, 0, results, limit, current);
        // using helper to recurse
//        for (int i = 0; i < results.size(); i++){
//            System.out.println(results.get(i));
//        }
    }


    void helper(String curr, String pref, int index, ArrayList<String> result,
                int limit, TrieNode current) {
        if (result.size() == limit) {
            // if result size is greater than limit then dont need to anymore work
        } else {
            for (int i = index; i < pref.length(); i++) {
                // move through the prefix and keep moving the current also
                char prior = pref.charAt(index);
                if (prior == WILDCARD) {
                    // special case when the prior == '.'
                    for (int j = 0; j < 256; j++) {
                        if (current.hasChild((char)j)) {
                            char preLetter = (char) j;
                            helper(curr + preLetter, pref, index + 1, result, limit, current.getChild(new TrieNode(preLetter)));
                        }

                    }
                }
                TrieNode newLetter = new TrieNode(prior);
                if (current.hasChild(prior)) {
                    current = current.getChild(newLetter);
                    curr = curr + prior;
                    index++;
                }
                // what happens if cannot find prefix letter?
            }
            if (index >= pref.length()) {
                for (int i = 0; i < 256; i++) {
                    char letter = (char) i;
                    TrieNode newLetter = new TrieNode(letter);
                    if (current.hasChild(letter)) {
                        helper(curr + letter, pref, index + 1, result, limit, current.getChild(newLetter));
                    }
                    if (current.isEnd()) {
                        if (result.contains(curr)) {
                            continue;
                        }
                        result.add(curr);
                    }
                }
            }
        }
    }

    // Search for string matching the specified pattern.
    // Return results in the specified ArrayList.
    // Only return at most limit results.
        // TODO:
    void patternSearch(String s, ArrayList<String> results, int limit) {
        findPattern( s, 0, root, results, limit, '!');
        // Use a helper to recurse
//        System.out.println("WORDS");
//        for (int i = 0; i < results.size(); i++) {
//            System.out.print(results.get(i)+ ", ");
//        }
//        System.out.println();
    }

    void helper2 (String str, ArrayList<String> results, int limit){
        for (int i = 0 ; i < str.length(); i++){
            if (str.charAt(i) == '*'){
                findPattern( str.substring(0, i - 1) + str.substring(i + 1), 0,
                        root, results, limit, '!');
                // passes in one imput without the preceeding character
                findPattern(str.substring(0, i) + str.substring(i + 1), 1,
                        root, results, limit, str.charAt(i - 1));
                // passes in one input with a repeating char in order to let the recursion happen
            }
        }
    }
    void findPattern(String str, int index, TrieNode current,
                     ArrayList<String> results, int limit, char repeatingChar) {
        //System.out.println(String.format("START: %s, %d, %c", str, index, repeatingChar));
        if ((results.size() >= limit)){
            // The results contain limit number of elements
        }
        if (str.equals("") && !results.contains("")) {
            // Special case: empty string
            results.add("");
        } else {
            //System.out.println(String.format("INSIDE: %s, %s, %d", str, currStr, index));
            if (contains(str) && !results.contains(str)){
                // check if the trie has the prefix in it and the results don't contain it already
                results.add(str);
            }
            if (! (str.length() == index)) {
                // if str.length() == index, the recursion is no longer needed
                char newL = str.charAt(index);
                if (newL == WILDCARD) {
                    if (index != str.length() - 1) {
                        // checking if the next index is the end of the string
                        char nextL = str.charAt(index + 1);
                        if (nextL == PLUS || nextL == STAR) {
                            // specific cases of .* and .+
                            if (nextL == STAR){
                                // if its a star run it one time without the preceeding character
                                findPattern( str.substring(0, index) + str.substring(index + 2),
                                        index, current, results, limit, repeatingChar);
                            }
                            findPattern(str.substring(0, index + 1) + str.substring(index + 2), index,
                                    current,  results, limit, '.');
                            ArrayList<TrieNode> progeny = new ArrayList<>();
                            for (int j = 0; j < 256; j++) {
                                if (current.children[j] != null) {
                                    // finding the character from the wildcard that we want to recurse with using +
                                    progeny.add(current.children[j]);
                                }
                            }
                            if (progeny.size() != 0) {
                                // if it is 0 then there is no use recursing anymore
                                for (int k = 0; k < progeny.size(); k++) {
                                    // hard coded tbh :-(
                                    if (!progeny.get(k).isLeaf()) {
                                        findPattern( str.substring(0, index + 1) + '.' + str.substring(index + 1), index,
                                                current, results, limit, '.');
                                        // call the findPattern for each and every child and add the repeating char as
                                        // '.'
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    for (int i = 0; i < 256; i++) {
                        if (current.children[i] != null) {
                            char anyLetter = (char) i;
                            // call the findPattern for each and every child that the wildcard contains
                            findPattern(
                                    str.substring(0, index) + anyLetter + str.substring(index + 1), index + 1,
                                    current.getChild(new TrieNode(anyLetter)), results, limit, repeatingChar);
                        }
                    }
                }
                if (newL == PLUS) {
                    findPattern( str.substring(0, index) + str.substring(index + 1), index, current,
                            results, limit, str.charAt(index - 1));
                    // remove the plus and add the repeating char into the function so that it can trigger my if cases
                }
                if (newL == QUESTION) {
                    findPattern( str.substring(0, index - 1) + str.substring(index + 1),
                            index - 1, current, results, limit, repeatingChar);
                    // call the function one time without the prev char
                    findPattern( str.substring(0, index) + str.substring(index + 1), index, current,
                            results, limit, repeatingChar);
                    // call it with the prev char
                }
                if (newL == STAR) {
                    //System.out.println(String.format("STAR: %s, %d", str,  index));
                    helper2(str, results, limit);
                    // call my helper2 function which provides multiple strings if there are more than 1 '*' in the
                    // prefix string
                }
                if (current.hasChild(repeatingChar)) {
                    //System.out.println(String.format("RECURSE: %s, %d", str, index));
                    findPattern( str.substring(0, index) + repeatingChar + str.substring(index),
                            index + 1, current.getChild(new TrieNode(repeatingChar)), results, limit, repeatingChar);
                    // call the findPattern function with a changed string which adds the repeated character in
                    // between the string
                }
                if (current.hasChild(newL)) {
                    findPattern( str, index + 1, current.getChild(new TrieNode(newL)),
                            results, limit, repeatingChar);
                    // call the findPattern function for the next letter of the index without changing the prefix
                }
            }
        }
    }

    // Simplifies function call by initializing an empty array to store the results.
    // PLEASE DO NOT CHANGE the implementation for this function as it will be used
    // to run the test cases.
    String[] prefixSearch(String s, int limit) {
        ArrayList<String> results = new ArrayList<String>();
        prefixSearch(s, results, limit);
        return results.toArray(new String[0]);
    }

    // Simplifies function call by initializing an empty array to store the results.
    // PLEASE DO NOT CHANGE the implementation for this function as it will be used
    // to run the test cases.
    String[] patternSearch(String s, int limit) {
        ArrayList<String> results = new ArrayList<String>();
        patternSearch(s, results, limit);
        return results.toArray(new String[0]);
    }

    public static void main(String[] args) {
        Trie t = new Trie();
//        t.insert("peter");
//        t.insert("papsi");
//        t.insert("piper");
//        t.insert("picked");
//        t.insert("peck");
//        t.insert("pek");
//        t.insert("of");
//        t.insert("pickled");
//        t.insert("peppers");
//        t.insert("pepers");
//        t.insert("pepppito");
//        t.insert("pepi");
//        t.insert("pik");
////
//        t.insert("abbde");
//        t.insert("acde");
//        t.insert("accde");
//        t.insert("acccde");
//        t.insert("accccde");
//
//
//        // ".*" and ".+" checks
//        t.insert("their");
//        t.insert("thheir");
//        t.insert("thheiir");
//        t.insert("teir");
//        t.insert("tr");
//        t.insert("tabacdr");
//        t.insert("tabbbacccdrrr");
//        t.insert("tabcd");

        // Code used to check patternSearch
//        t.insert("abcd");
//        t.insert("abxcd");
//        t.insert("abcdcd");
//        t.insert("");

        t.insert("");
        t.insert("a");
        t.insert("aa");
        t.insert("aaa");
        t.insert("aaaaaa");
        t.insert("aaaaaaa");
        t.insert("aaaaaaaaaaaa");
        t.insert("aaaaaaaaaaaaaaaaaaaa");
        t.insert("ab");
        t.insert("b");
        t.insert("aabaa");
        t.insert("abaaa");


        // Cases checked
        // "", ".*", ".+", ".?"
        // "a.", "a*", "a+", "a?"
        // "a.*", "a.+", "a.?"
        // "a*ba*", "a?ba?" -> is supposed to return "b"
        // "a?a+a?a+"

//        String[] result = t.patternSearch(".*", 10);
//        printArr(result);
//        String[] result1 = t.prefixSearch("", 5);
//        printArr(result1);
        String[] result2 = t.patternSearch("a*a", 10);
        printArr(result2);
//        String[] result3 = t.patternSearch("a.c.*", 10);
//        printArr(result3);
//        String[] result4 = t.patternSearch("a*ba*", 10);
//        printArr(result4);
// result2 should contain the same elements with result1 but may be ordered arbitrarily
    }

    private static void printArr(String[] arr) {
        if (arr.length != 0) {
            System.out.print("{ ");
            for (int i = 0; i < (arr.length - 1); i++) {
                System.out.print(arr[i] + ", ");
            }
            System.out.println(arr[arr.length - 1] + " }");
        } else {
            System.out.println("Error: Empty Array");
        }
    }

}
