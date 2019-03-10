package text_process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import readfile.ReadData;

public class WorkExtraction {

	public static List<String> TokenizeLemmaNer(String text) {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner");

		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		Annotation document = new Annotation(text);

		pipeline.annotate(document);

		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		List<String> words = new ArrayList<String>();
		for (CoreMap sentence : sentences) {
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				String word = token.get(TextAnnotation.class);
				String ne = token.get(NamedEntityTagAnnotation.class);
								
				if (ne.equals("O")) {
					words.add(word);
				} else {
					words.add(ne);
				}
			}
		}
		return words;
	}
	
	public static List<String> RemoveNonCharactor(List<String> words){
		List<String> resultWords = new ArrayList<String>();
		
		for (String word : words) {
			if(word.matches("[a-zA-Z]+")) {
				resultWords.add(word.toLowerCase());
			}
		}
		return resultWords;
	}
	
	public static List<String> RemoveStopWords(List<String> words){
		String stopWordsText = ReadData.readFile("D:\\BK20182\\Project 2\\stopwords.txt");
		Set<String> stopwords = Arrays.stream(stopWordsText.split("\n")).collect(Collectors.toSet());
		
		List<String> result = words.stream().filter(word -> ! stopwords.contains(word)).collect(Collectors.toList());   
		
		return result;
	}

}
