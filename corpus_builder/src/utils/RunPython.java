package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RunPython {
	String inputFile;
	String outputFile;
	String pythonPath;
	String pyScriptPath;
	String userdict;
	
	public RunPython() {
	}
	
	public RunPython(String _inputFile) {
		this.inputFile = _inputFile;
		this.outputFile = "segmented_" + _inputFile;
	}
	
	public RunPython(String _inputFile, String _userdict) {
		this.inputFile = _inputFile;
		this.outputFile = "segmented_" + _inputFile;
		this.userdict = _userdict;
	}
	
	public RunPython(String _pythonPath, String _pyScriptPath, String _inputFile, String _outputFile, String _userdict) {
		this.pythonPath = _pythonPath;
		this.pyScriptPath = _pyScriptPath;
		this.inputFile = _inputFile;
		this.outputFile = _outputFile;
		this.userdict = _userdict;
	}
	
	public void run() throws IOException, InterruptedException {
		String[] args;
		File file = new File(this.userdict);
		
		if (!file.exists()) {
			file.createNewFile();
		}
		
		if (this.pythonPath == null && this.pyScriptPath == null) {
			args = new String[] {"C:\\Users\\yangxin01\\AppData\\Local\\Programs\\Python\\Python36\\python.exe","D:\\corpus_builder\\corpus_builder\\NLPdemo_seg.py", this.inputFile, this.outputFile, this.userdict};
		} else {
			args = new String[] {this.pythonPath, this.pyScriptPath, this.inputFile, this.outputFile, this.userdict};
		}
		
        Process process = Runtime.getRuntime().exec(args);
        InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
            }
        } catch (IOException e) {
                e.printStackTrace();
        }

	}
}
