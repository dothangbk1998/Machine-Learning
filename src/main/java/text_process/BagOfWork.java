package text_process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class BagOfWork {
	public static List<Double> TF(List<String> vector, List<String> dictionary) {
		SortedMap<String, Double> tfVector = new TreeMap<String, Double>();
		
		for (String key : dictionary) {
			tfVector.put(key, 0.0);
		}
		
		for (String word : vector) {
			tfVector.put(word, tfVector.get(word) + 1);
		}
		return tfVector.values().stream().collect(Collectors.toList());
	}
	
	public static List<List<Double>> TF_IDF(List<List<String>> vectors, List<String> dictionary) {
		int n = dictionary.size();
		List<List<Double>> listTfIdfVector = new LinkedList<List<Double>>();
		List<Integer> numberDocOfWord = new ArrayList<Integer>(dictionary.size());
		for(int i=0; i<n; i++) {
			numberDocOfWord.add(0);
		}
		
		for (List<String> vector : vectors) {
			List<Double> tfVector = TF(vector, dictionary);
			listTfIdfVector.add(tfVector);
			for(int i=0; i<tfVector.size(); i++) {
				if(tfVector.get(i) > 0.0) {
					String word = dictionary.get(i);
					int currentValue = numberDocOfWord.get(dictionary.indexOf(word));
					numberDocOfWord.set(dictionary.indexOf(word), currentValue + 1);
				}
			}
		}
		
		for(List<Double> vector : listTfIdfVector) {
			for(int i=0; i<n; i++) {
				Double tf = vector.get(i);
				Double tf_idf = tf * Math.log(n / numberDocOfWord.get(i));
				vector.set(i, Math.round(tf_idf * 10) / 10.0);
			}
		}
		
		return listTfIdfVector;
		
	}
}
