package fantasy.gui.fantasy_gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

public class ScanTest extends TestCase {

	@Test
	public void test() throws FileNotFoundException {
		HashMap<String, String> map = new HashMap<String, String>();
		File f = new File("resources/text.txt");
		Scanner s = new Scanner(f);
		while (s.hasNextLine()) {
			String line = s.nextLine();
			String[] split = line.split(", ");
			String name = split[0];
			String tags = null;
			if (map.containsKey(name)) {
				tags = map.get(name);
				if (!tags.contains(split[1])) {
					tags = tags + split[1];
					map.put(name, tags);
				}
			} else {
				tags = split[1];
				map.put(name, tags);
			}
		}
		List<String> players = new ArrayList<String>();
		for (Entry<String, String> entry : map.entrySet()) {
			players.add(entry.getKey() + ", " + entry.getValue());
		}
		Collections.sort(players);
		s.close();
		for (String player : players) {
			System.out.println(player);
		}
	}
}
