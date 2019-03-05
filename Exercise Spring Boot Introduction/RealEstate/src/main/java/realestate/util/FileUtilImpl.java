package realestate.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtilImpl implements FileUtil {
	
	@Override
	public String readFile(String filePath) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		
		StringBuilder result = new StringBuilder();
		String line = null;
		
		while ((line = reader.readLine()) != null) {
			result.append(line).append(System.lineSeparator());
		}
		return result.toString().trim();
	}
}
