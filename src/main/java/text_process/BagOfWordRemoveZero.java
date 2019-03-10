package text_process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class BagOfWordRemoveZero {
	public static SortedMap<String, Double> TF(List<String> vector) {
		SortedMap<String, Double> tfVector = new TreeMap<String, Double>();
				
		for (String word : vector) {
			if(tfVector.containsKey(word)) {
				tfVector.put(word, tfVector.get(word) + 1);
			}
			else {
				tfVector.put(word, 1.0);
			}
		}
		return tfVector;
	}
	
	public static List<SortedMap<String, Double>> TF_IDF(List<List<String>> vectors, List<String> dictionary) {
		int n = dictionary.size();
		List<SortedMap<String, Double>> listTfIdfVector = new LinkedList<SortedMap<String, Double>>();
		Map<String, Integer> numberDocOfWord = new HashMap<String, Integer>();
		for (String key : dictionary) {
			numberDocOfWord.put(key, 0);
		}
		
		for (List<String> vector : vectors) {
			SortedMap<String, Double> tfVector = TF(vector);
			listTfIdfVector.add(tfVector);
			for (String key : tfVector.keySet()) {
				numberDocOfWord.put(key, numberDocOfWord.get(key)+1);
			}
		}
		
		for(SortedMap<String, Double> vector : listTfIdfVector) {
			for (String key : vector.keySet()) {
				Double tf = vector.get(key);
				Double idf = Math.log((double)n/numberDocOfWord.get(key));
				vector.put(key, Math.round(tf * idf * 10)/10.0);
			}
		}
		
		return listTfIdfVector;
		
	}
}
