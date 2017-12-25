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
		
		System.out.println("��ʼ�ִ�");
		System.out.println("---------------------------------------");
		RunPython rp = new RunPython(inputFile, newWordsDict);
		rp.run();
		
		FileImporter stopWordFI = new FileImporter("stopWords.txt");
		FileImporter inputFI = new FileImporter("segmented_demo.txt");

		System.out.println("��ȡͣ�ô�");
		System.out.println("---------------------------------------");
		List<String> stopWords = stopWordFI.importFile();
		
		System.out.println("��ʼ��ȡ�ִ��ļ�");
		System.out.println("---------------------------------------");
		List<String> inputWords = inputFI.importFile();
		long inputWC = inputFI.getWordCount();
		
		System.out.println("��ʼ�����´�");
		System.out.println("---------------------------------------");
		CorpusBuilder cb = new CorpusBuilder(inputWords, stopWords, inputWC, 5, CorpusBuilder.TYPE.MULTI);
		List<String> newWords = cb.build();
		
		System.out.println("---------------------------------------");
		for (String str : newWords) {
			System.out.println(str);
		}
		
		System.out.println("��ʼд���´ʿ�");
		System.out.println("---------------------------------------");
		FileExporter fe = new FileExporter(newWordsDict);
		fe.exportFile(newWords);
	}
}
