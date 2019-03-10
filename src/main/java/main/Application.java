package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.SortedMap;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.PropertiesUtils;
import jdbcconnect.FileDao;
import readfile.ReadData;
import text_process.BagOfWordRemoveZero;
import text_process.DictionaryOfAllWords;


public class Application {

	public static void main(String[] args) throws IOException {
		
		long startTime = System.currentTimeMillis();
		
		ReadData rd = new ReadData("D:\\BK20182\\Project 2\\20newsgroupDBtest");
		List<String> listFileName = rd.getAllFile();
		System.out.println("fileName: " + listFileName.subList(0, 5));
		
		List<String> listFilePath = rd.getAllFilePath();
		System.out.println("filePath: " + listFilePath.subList(0, 5));
		
		
		List<String> listFolder = rd.getFolderForFile();
		System.out.println("Folder: " + listFolder.subList(0, 5));
		
		
		List<List<String>> vectors = new ArrayList<List<String>>(listFileName.size());
		for(int i=0; i<listFileName.size(); i++) {
			vectors.add(null);
		}

		
		int numberOfThread = 4;
		int numberOfFile = listFileName.size();
		int filePerThread = numberOfFile / numberOfThread;
		
		List<Thread> threads = new ArrayList<Thread>();
		for(int i=0; i<numberOfThread; i++) {
			int fromIndex = i * filePerThread;
			int endIndex = (i+1)*filePerThread;
			if(endIndex < numberOfFile) {
				endIndex = numberOfFile;
			}
			
			CreateVector cv = new CreateVector(listFilePath.subList(fromIndex, endIndex),
												vectors, fromIndex);
			threads.add(new Thread(cv));
		}
		
		for (Thread thread : threads) {
			thread.run();
		}
		try {
			for(Thread thread : threads) {
				thread.join();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		long endTime = System.currentTimeMillis();
		long secondsDuration = (endTime - startTime) / 1000;
		
		System.out.println("**********************************");
		System.out.println("Time: " + secondsDuration);
		
		//vectors.forEach(word -> System.out.println(word));
		System.out.println("**********************************");
		System.in.read();
		
		
		List<String> dictionary = DictionaryOfAllWords.getDictionary(vectors);
		
		System.out.println("Dictionary: \n" + dictionary);
		System.out.println(dictionary.size());
		System.out.println("**********************************");
		System.in.read();
		
		
		List<SortedMap<String, Double>> list_TF_IDF_Vector = BagOfWordRemoveZero.TF_IDF(vectors, dictionary);
		for(int i=0; i<list_TF_IDF_Vector.size(); i++) {
			SortedMap<String, Double> vector = list_TF_IDF_Vector.get(i);
			System.out.print("[");
			for (String key : vector.keySet()) {
				System.out.print(key + ": " + vector.get(key) + "; ");
			}
			System.out.println("]");
			
		}
				
		System.out.println("End");
	}

}
