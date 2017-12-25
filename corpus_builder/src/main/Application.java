package main;

import java.util.List;

import corpus.CorpusBuilder;
import utils.FileExporter;
import utils.FileImporter;
import utils.RunPython;

public class Application {
	public static void main(String[] args) throws Exception {
		String inputFile = "demo.txt";
		String newWordsDict = "userdict.txt";
		
		System.out.println("开始分词");
		System.out.println("---------------------------------------");
		RunPython rp = new RunPython(inputFile, newWordsDict);
		rp.run();
		
		FileImporter stopWordFI = new FileImporter("stopWords.txt");
		FileImporter inputFI = new FileImporter("segmented_demo.txt");

		System.out.println("读取停用词");
		System.out.println("---------------------------------------");
		List<String> stopWords = stopWordFI.importFile();
		
		System.out.println("开始读取分词文件");
		System.out.println("---------------------------------------");
		List<String> inputWords = inputFI.importFile();
		long inputWC = inputFI.getWordCount();
		
		System.out.println("开始生成新词");
		System.out.println("---------------------------------------");
		CorpusBuilder cb = new CorpusBuilder(inputWords, stopWords, inputWC, 5, CorpusBuilder.TYPE.MULTI);
		List<String> newWords = cb.build();
		
		System.out.println("---------------------------------------");
		for (String str : newWords) {
			System.out.println(str);
		}
		
		System.out.println("开始写入新词库");
		System.out.println("---------------------------------------");
		FileExporter fe = new FileExporter(newWordsDict);
		fe.exportFile(newWords);
	}
}
