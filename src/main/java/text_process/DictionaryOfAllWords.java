package text_process;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class DictionaryOfAllWords {
	public static List<String> getDictionary(List<List<String>> listVector) {
		Set<String> dictionary = new HashSet<String>();
		for (List<String> vector : listVector) {
			dictionary.addAll(vector);
		}
		return dictionary.stream().sorted().collect(Collectors.toList());
	}
}
