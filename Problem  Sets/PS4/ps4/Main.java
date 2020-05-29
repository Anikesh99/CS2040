import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Byte[] datawords = {'h', 'e', 'l', 'l', 'o', 'w', 'o', 'r', 'l', 'd', '!'};
        UniformTree tree = new UniformTree(datawords);
        tree.printTree();
//        Byte key = 'e';
//        boolean[] arr = tree.queryCode(key);
//        System.out.println("Searched: " + tree.query(arr, arr.length));
//        HashMap<Byte, Integer> data = new HashMap<Byte, Integer>();
//        data.put((byte)'a', 5);
//        data.put((byte)'z', 3);
//        data.put((byte)'g', 5);
//        data.put((byte)'h', 10);
//        data.put((byte)'l', 11);
//        data.put((byte)'k', 2);
//        data.put((byte)'m', 20);
//        data.put((byte)'x', 2);
//        new WeightedTree(data).printTree();

//        HashMap<Byte, Integer> hm = new HashMap<>();
//        hm.put((byte) '!', 1);
//        hm.put((byte) 'd', 1);
//        hm.put((byte) 'e', 1);
//        hm.put((byte) 'h', 1);
//        hm.put((byte) 'r', 1);
//        hm.put((byte) 'w', 1);
//        hm.put((byte) 'l', 3);
//        hm.put((byte) 'o', 2);
//        new WeightedTree(hm).printTree();

//        HashMap<Byte, Integer> hm = new HashMap<>();
//        hm.put((byte) 't', 1);
//        hm.put((byte) 'h', 1);
//        hm.put((byte) 'e', 1);
//        hm.put((byte) ' ', 3);
//        hm.put((byte) 'Q', 1);
//        hm.put((byte) 'u', 1);
//        hm.put((byte) 'i', 1);
//        hm.put((byte) 'c', 1);
//        hm.put((byte) 'k', 1);
//        hm.put((byte) 'b', 1);
//        hm.put((byte) 'r', 1);
//        hm.put((byte) 'o', 2);
//        hm.put((byte) 'w', 1);
//        hm.put((byte) 'n', 1);
//        hm.put((byte) 'F', 1);
//        hm.put((byte) 'x', 1);
//        new WeightedTree(hm).printTree();
//        test("helloworld");
     }
    static boolean test(String str) {
        HashMap<Byte, Integer> input = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            byte b = (byte)str.charAt(i);
            input.put(b, input.getOrDefault(b, 0) + 1);
        }
//        System.out.println(input.keySet());

        boolean isgood = true;
        WeightedTree testTree = new WeightedTree(input);
        testTree.printTree();
        for (int i = 0; i < str.length(); i++) {
            byte b = (byte)str.charAt(i);
//            System.out.println((char)b);
            boolean[] arr = testTree.queryCode(b);
//            for (int j = 0; j < arr.length; j++) {
//                System.out.print(arr[i] + ", ");
//            }
            isgood = isgood && testTree.query(arr, arr.length) == b;
        }
        System.out.println(isgood);
        return isgood;
    }
}
