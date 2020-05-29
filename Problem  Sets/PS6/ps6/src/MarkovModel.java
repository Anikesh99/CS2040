import java.util.*;

public class MarkovModel {

	// Use this to generate random numbers as needed
	private Random generator = new Random();
	private int order;
	private Map outerMap;
	private String text;
	// This is a special symbol to indicate no character
	public static final char NOCHARACTER = (char) 0;

	public MarkovModel(int order, long seed) {
		// Initialize your class here
		this.order = order;
		// Initialize the random number generator
		generator.setSeed(seed);
	}

	public void initializeText(String text) {
		// Build the Markov model here
		this.text = text;
		outerMap = new HashMap<String, HashMap<String, Integer>>(); // intialize your outermap
		String[] arrayOfWords = text.split(" "); // split the string into its individual words
		for (int i = 0; i < arrayOfWords.length - order; i++) {
			String curr = "";
			for (int j = 0; j < order; j++) {
				curr += arrayOfWords[i + j] + " ";
				// get all the possible strings of size order
			}
			String nextWord = arrayOfWords[i + order];
			// the next word after the string
			if (outerMap.containsKey(curr)) {
				HashMap<String, Integer> innerMap = (HashMap<String, Integer>) outerMap.get(curr);
				if (innerMap.containsKey(nextWord)) {
					// if the next word is in the map just change the frequency of that word
					int occurrence = innerMap.get(nextWord);
					innerMap.put(nextWord, occurrence + 1);
				} else {
					// add next word in
					innerMap.put(nextWord, 1);
				}
				outerMap.put(curr, innerMap);
			} else {
				// if the next word is not in the map add it in
				Map<String, Integer> innerMap = new HashMap<>();
				innerMap.put(nextWord, 1);
				outerMap.put(curr, innerMap);
			}
		}
	}

	public int getFrequency(String kgram) {
		if (!outerMap.containsKey(kgram)){
			return 0;
		}
		int counter = 0;
		String curr = "";
		for (int i = 0; i < text.length(); i++){
			curr += text.charAt(i);
			if (curr.length() == kgram.length()){
				if (kgram.equals(curr)){
					counter++;
				}
				curr = curr.substring(1);
			}
		}
		return counter;
	}



	public int getFrequency(String kgram, String s) {
		HashMap<Character, Integer> innerMap = (HashMap<Character, Integer>) outerMap.get(kgram);
		if (this.getFrequency(kgram) == 0 || innerMap.get(s) == null){
			return 0;
		}
		return innerMap.get(s);
	}

	/**
	 * Generates the next character from the Markov Model.
	 * Return NOCHARACTER if the kgram is not in the table, or if there is no
	 * valid character following the kgram.
	 */
	public String nextWord(String kgram) {
		// See the problem set description for details
		// on how to make the random selection.
		if (outerMap.isEmpty() || !outerMap.containsKey(kgram)) {
			// If its empty return an empty string
			return "";
		}
		HashMap<String, Integer> innerMap = (HashMap<String, Integer>) outerMap.get(kgram);
		if (innerMap.keySet().isEmpty()) {
			// if your string is not in the map
			return "";
		}
		List<String> list = new ArrayList<>(innerMap.keySet());
		int len = list.size();
		for (int i = 0; i < len; i++) {
			// put all the strings in a list
			String s = list.get(i);
			int rep = innerMap.get(s) - 1;
			for (int j = 0; j < rep; j++) {
				list.add(s);
			}
		}
		Collections.sort(list);
		// sort it
		int luckyNum = generator.nextInt(list.size());
		return list.get(luckyNum); // return the string at the lucky index
	}

	public static void main(String[] args) {
		String text = "ab ab ab ba ab ba ab";
		int order = 2;
		int seed = 100;
		String kgram = "ab";
		int expected = 7;

		MarkovModel testModel = new MarkovModel(order, seed);
		testModel.initializeText(text);
		int freq = testModel.getFrequency(kgram, "ba");
		System.out.println(freq);
		//assertEquals(expected, freq);
	}
}