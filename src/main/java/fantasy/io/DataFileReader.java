package fantasy.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.util.StringUtils;

public class DataFileReader {
	
	public List<List<String>> getSplitLinesFromFile(String fileName) throws FileNotFoundException {
		List<List<String>> splitLinesList = new ArrayList<>();
		Scanner scanner = new Scanner(new File(fileName));
		if (scanner.hasNextLine()) {
			scanner.nextLine(); //move past header
		}
		while(scanner.hasNextLine()){
			String line = scanner.nextLine();
			if ((!StringUtils.isEmpty(line)) && (!line.equals("\"\""))){
				List<String> split = splitAndCleanupLine(line);
				splitLinesList.add(split);
			}
		}
		scanner.close();
		return splitLinesList;
	}

	private List<String> splitAndCleanupLine(String line) {
		List<String> splitLine = new ArrayList<String>();
		for (String text : line.split(",")) {
			String edited = (text == null || StringUtils.isEmpty(text.trim()) || text.trim().equals("\"\"")) ? "-" : text.trim();
			if (edited.startsWith("\"")) {
				edited = edited.substring(1); //remove beginning quote
			}
			if (edited.endsWith("\"")) {
				edited = edited.substring(0, edited.length() - 1); //remove trailing quote
			}
			splitLine.add(edited);
		}
		return splitLine;
	}

	public List<String> getLinesFromFile(String fileName) throws FileNotFoundException {
		List<String> names = new ArrayList<String>();
		Scanner scanner = new Scanner(new File(fileName));
		if (scanner.hasNextLine()) {
			scanner.nextLine(); //move past header
		}
		while(scanner.hasNextLine()){
			String line = scanner.nextLine();
			if ((!StringUtils.isEmpty(line)) && (!line.equals("\"\""))){
				names.add(line);
			}
		}
		scanner.close();
		return names;
	}

}
