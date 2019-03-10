package main;

import java.util.ArrayList;
import java.util.List;

import readfile.ReadData;
import text_process.WorkExtraction;

public class CreateVector implements Runnable {
	private List<String> listFilePath;
	private int fromIndex;
	
	private List<List<String>> vectors;
	
	public CreateVector(List<String> listFilePath, List<List<String>> vectors, int fromIndex) {
		this.listFilePath = listFilePath;
		this.vectors = vectors;
		this.fromIndex = fromIndex;
		
	}
	
	@Override
	public void run() {
		
		for(int i=0; i< listFilePath.size(); i++) {
			String text = ReadData.readFile(this.listFilePath.get(i));
			
			List<String> afterTokenizeLemmaNer = WorkExtraction.TokenizeLemmaNer(text);
			List<String> afterRemoveNonCharactor = WorkExtraction.RemoveNonCharactor(afterTokenizeLemmaNer);
			List<String> afterRemoveStopWords = WorkExtraction.RemoveStopWords(afterRemoveNonCharactor);
						
			this.vectors.set(i+fromIndex, afterRemoveStopWords);
		}
		
		
	}

}
