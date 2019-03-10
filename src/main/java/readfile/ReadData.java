package readfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadData {
	private Path rootPath;
	
	public ReadData(String rootPath) {
		if(Files.exists(Paths.get(rootPath))) {
			this.rootPath = Paths.get(rootPath);
		}
		else {
			System.out.println("Path not exits");
		}
	}
	
	public List<String> getAllFolder(){
		List<String> folders = new ArrayList<String>();
		try {
			Files.list(this.rootPath).forEach(folder -> folders.add(folder.getFileName().toString()));	
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return folders;
	}
	
	public List<String> getAllFile(){
		List<String> folders = this.getAllFolder();
		List<String> files = new ArrayList<String>();
		try {
			for (String folder : folders) {
				ArrayList<String> filesInFolder = new ArrayList<String>();
				Files.list(Paths.get(this.rootPath.toString(), folder))
						.forEach(file -> filesInFolder.add(folder + "-" + file.getFileName().toString()));
				files.addAll(filesInFolder);
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return files;
	}
	
	public List<String> getAllFilePath(){
		List<String> pathFiles = new ArrayList<String>();
		List<String> listFolders = this.getAllFolder();
		
		try {
			for (String folder : listFolders) {
				List<String> filesInFolder = new ArrayList<String>();
				Files.list(Paths.get(this.rootPath.toString(), folder))
						.forEach(file -> filesInFolder.add(file.toString()));
				pathFiles.addAll(filesInFolder);
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return pathFiles;
	}
	
	public List<String> getFolderForFile(){
		List<String> pathFiles = new ArrayList<String>();
		List<String> listFiles = this.getAllFile();
		
		for (String file : listFiles) {
			String folder = file.split("-")[0];
			pathFiles.add(folder);
		}
		return pathFiles;
	}
	
	public static String readFile(String filePath) {
		StringBuffer document = new StringBuffer();
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"utf-8"))){
			String inValue;
			while((inValue = br.readLine()) != null) {
				document.append(inValue);
				document.append("\n");
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return document.toString();
	}
}
