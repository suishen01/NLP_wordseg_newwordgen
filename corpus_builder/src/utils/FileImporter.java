package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileImporter {
	File file;
	long wordCount;
	
	public FileImporter(File _file) {
		this.file = _file;
		this.wordCount = 0;
	}
	
	public FileImporter(String fileName) {
		this.file = new File(fileName);
		this.wordCount = 0;
	}
	
	public List<String> importFile() throws Exception {
		InputStreamReader isr = new InputStreamReader(new FileInputStream(this.file), "UTF-8"); 
		BufferedReader fileBR = new BufferedReader(isr); 
		List<String> wordSet = new ArrayList<String>();         
		
		String word = null;     
		word = fileBR.readLine();
		while (!word.isEmpty()) {
			wordSet.add(word);   
			word = fileBR.readLine();
			this.wordCount++;
		}
		fileBR.close();
		return wordSet;
	}
	
	public long getWordCount() {
		return this.wordCount;
	}
}
