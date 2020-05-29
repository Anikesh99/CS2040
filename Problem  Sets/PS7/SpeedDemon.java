import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class SpeedDemon {
    public int processData(String filename){
        // TODO: implement this method
//        StopWatch stopme = new StopWatch();
//        stopme.start();
        BufferedReader buffer;
        int counter = 0;
        try {
            buffer = new BufferedReader(new FileReader(filename));
            String line = buffer.readLine();
            int lines = Integer.parseInt(line);
            MyHashMap hash = new MyHashMap(lines);
            for (int i = 0; i < lines; i++){
                line = buffer.readLine();
                Arraysme arr = new Arraysme(line);
                if (hash.containsKey(arr) && hash.get(arr) != null){
                    hash.put(arr, hash.get(arr) + 1);
                } else {
                    hash.put(arr, 1);
                }
            }

//            Iterator it = hash.entrySet().iterator();
//            while (it.hasNext()){
//                Map.Entry pair = (Map.Entry)it.next();
//                int val = (int)pair.getValue();
//                if (val == 1){
//                    continue;
//                }
//                counter += val * (val - 1) / 2;
//                it.remove();
//            }
//            for (Map.Entry<Arraysme, Integer> entry: hash.entrySet()){
//                if (entry.getValue().equals(1)){
//                    continue;
//                }
//                counter += (int)entry.getValue() * ((int)entry.getValue() - 1) / 2;
//            }
            counter = hash.give();
//            stopme.stop();
//            System.out.println(stopme.getTime());
        } catch (IOException e){
            e.printStackTrace();
        }
        return counter;
    }

    public static void main(String[] args){
        SpeedDemon dataProcessor = new SpeedDemon();
        int answer = dataProcessor.processData(args[0]);
        System.out.println(answer);
    }
}
final class Arraysme {
    int[] arr = new int[128];

    Arraysme(String s) {
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
        return Arrays.equals(arr, ((Arraysme)obj).arr);
    }
}

class Entry {
    private final Arraysme key;
    private final Integer value;

    public Entry(Arraysme key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public Arraysme getKey() {
        return key;
    }

    public Integer getValue() {
        return value;
    }
}

class MyHashMap {
    private Entry[] buckets;

    public MyHashMap(int capacity) {
        buckets = new Entry[capacity * 47];
    }
    public boolean containsKey (Arraysme key){
        return buckets[index(key)] != null && buckets[index(key)].getKey().equals(key);
    }
    public Integer get(Arraysme key) {
        Entry entry = buckets[index(key)];
        return entry != null ? entry.getValue() : null;
    }
    public void put(Arraysme key, Integer value) {
        int bucket = index(key);
        if (buckets[bucket] != null){
            if (key.equals(buckets[bucket].getKey())){
                buckets[bucket] = new Entry(key, value);
            } else {
                while (buckets[bucket] != null && (buckets[bucket].getKey() != null ||
                        !buckets[bucket].getKey().equals(key))){
                    if (bucket >= buckets.length){
                        bucket = 0;
                        continue;
                    }
                    bucket++;
                }
//                if (buckets[bucket] == null){
//                    buckets[bucket] = new Entry(key, 1);
//                } else
                    if (buckets[bucket]!= null && buckets[bucket].getValue() != null){
                    int val = (int) buckets[bucket].getValue() + 1;
                    buckets[bucket] = new Entry(key, val);
                }
//                else {
//                    buckets[bucket] = new Entry(key, 1);
//                }
            }
        } else {
            buckets[bucket] = new Entry(key, value);
        }

    }

    public int index(Arraysme key) {
        return Math.abs(key.hashCode() % buckets.length);
    }

    public int give(){
        int returnVal = 0;
        for (int i = 0; i < buckets.length; i++){
            if (buckets[i] != null && !buckets[i].equals(1)){
                int add = (int) buckets[i].getValue();
                returnVal += add * (add - 1) / 2;
            }
        }
        return returnVal;
    }
}