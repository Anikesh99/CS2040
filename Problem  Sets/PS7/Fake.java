import java.util.Arrays;

public class Fake{
        int[] arr = new int[128];

        Fake(String s) {
            for (int i = 0; i < s.length(); i++) {
                arr[s.charAt(i)]++;
            }
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(arr);
        }

        @Override
        public boolean equals(Object obj) {
            return Arrays.equals(arr, ((Fake)obj).arr);
        }
}
