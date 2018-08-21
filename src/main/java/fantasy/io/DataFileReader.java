package fantasy.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.jaunt.Document;
import com.jaunt.Element;
import com.jaunt.JNode;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;

import fantasy.constants.DataSourcePaths;

public class DataFileReader {

	private static Logger log = Logger.getLogger(DataFileReader.class);

	public void downloadFileFromUrl(String sourceUrl, String destPath) {
		try (InputStream in = new URL(sourceUrl).openStream()) {
			Files.copy(in, new File(destPath).toPath());
			log.info("Successfully downloaded file to " + destPath);
		} catch (Exception e) {
			log.error("ERROR downloading file to " + destPath + " :: " + e.getMessage());
		}
	}

	public List<List<String>> getSplitLinesFromFile(String fileName, boolean skipHeader, String regex) throws FileNotFoundException {
		List<List<String>> splitLinesList = new ArrayList<>();
		Scanner scanner = new Scanner(new File(fileName));
		if (skipHeader && scanner.hasNextLine()) {
			scanner.nextLine(); // move past header
		}
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if ((!StringUtils.isEmpty(line)) && (!line.equals("\"\""))) {
				List<String> split = splitAndCleanupLine(line, regex);
				splitLinesList.add(split);
			}
		}
		scanner.close();
		return splitLinesList;
	}

	private List<String> splitAndCleanupLine(String line, String regex) {
		List<String> splitLine = new ArrayList<String>();
		for (String text : line.split(regex)) {
			String edited = (text == null || StringUtils.isEmpty(text.trim()) || text.trim().equals("\"\"")) ? "-" : text.trim();
			splitLine.add(edited.replaceAll("\"", ""));
		}
		return splitLine;
	}

	public List<String> getLinesFromFile(String fileName) throws FileNotFoundException {
		List<String> names = new ArrayList<String>();
		Scanner scanner = new Scanner(new File(fileName));
		if (scanner.hasNextLine()) {
			scanner.nextLine(); // move past header
		}
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if ((!StringUtils.isEmpty(line)) && (!line.equals("\"\""))) {
				names.add(line);
			}
		}
		scanner.close();
		return names;
	}

	public String getDataFilePathFromFantasyProsWebsite(String url) {
		try {
			UserAgent userAgent = new UserAgent(); // create new userAgent (headless browser)
			userAgent.visit(url); // send request
			String xml = userAgent.doc.innerXML();
			String html = userAgent.doc.innerHTML();
			userAgent.openContent(html);
			Element dropdown = userAgent.doc.findFirst("fp-dropdown-menu higher");
			List<Element> dropdownOptions = dropdown.getChildElements();
			Element linkDropdown = dropdownOptions.get(3);
			Element link = linkDropdown.getChildElements().get(0);
			String path = link.getAtString("href");
			return path;
		} catch (JauntException e) { // if an HTTP/connection error occurs, handle JauntException.
			System.err.println(e);
		}
		return null;
	}

}
