package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class FileExporter {
	File file;
	
	public FileExporter(File _file) {
		this.file = _file;
	}
	
	public FileExporter(String fileName) {
		this.file = new File(fileName);
	}
	
	public void exportFile(List<String> list) throws Exception {
		try {
			if (!this.file.exists()) {
				this.file.createNewFile();
			}

			FileOutputStream writerStream = new FileOutputStream(this.file, true);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));
			for (String str : list) {
				bw.write(str + " 3 " + "/v");
				bw.newLine();
			}
			bw.flush();
			bw.close();

			System.out.println("Done");

		  	} catch (IOException e) {
		  		e.printStackTrace();
		  	}
	}
}
