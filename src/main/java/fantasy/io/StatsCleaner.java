package fantasy.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.TreeSet;

import fantasy.Log;
import fantasy.comparator.AlphabetizedPlayerComparator;
import fantasy.model.NFL;
import fantasy.model.Player;

public class StatsCleaner {
	
	private static final String HEADER = "#	RISK(\"!\"), SLEEPER(\"$\"), ROOKIE(\"R\"), NEW_TEAM(\"@\"), FAVORITE(\"*\"), RISING(\"+\"), FALLING(\"-\"), INJURY_RISK(\"i\"), BUST(\"B\")";

	static String first = null;
	private static DataFileReader dataReader = new DataFileReader();
	
	public static void cleanupTags() {
		try {
			TreeSet<Player> sorted = new TreeSet<Player>(new AlphabetizedPlayerComparator());
			
			
//			BufferedReader in = new BufferedReader(new FileReader(("resources/tags.csv")));
//			String line = null;
//			while((line = in.readLine()) != null) {
//				if (line.startsWith("#")) { continue; }
			for (List<String> split : dataReader.getSplitLinesFromFile("resources/tags.csv")) {
				Player p = NFL.getPlayer(split.get(0));
				sorted.add(p);
			}
			
		    BufferedWriter out = new BufferedWriter(new FileWriter("resources/tags.csv"));
		    out.write(HEADER);
		    for (Player p : sorted) {
		    	out.newLine();
			    out.write(p.getPlayerName() + "," + p.getTags());
		    }
		    out.flush();
		    out.close();
		}
		catch(IOException ex) {
			Log.err(ex.getMessage());
		}
	}
	
	
	public static void cleanupNickNotes() {
		try {
			TreeSet<Player> sorted = new TreeSet<Player>(new AlphabetizedPlayerComparator());
			
//			BufferedReader in = new BufferedReader(new FileReader(("resources/nicks_notes.csv")));
//			String line = null;
//			while((line = in.readLine()) != null) {
			for (List<String> split : dataReader.getSplitLinesFromFile("resources/nicks_notes.csv")) {
				
				Player p = NFL.getPlayer(split.get(0));
				try {
					p.addNicksNotes(split.get(1));
				} catch (Exception e) {
					Log.err("StatsCleaner.cleanupNickNotes() :: Error trying to cleanup Nick Notes");
					e.printStackTrace();
				}
				sorted.add(p);
			}
			
		    BufferedWriter out = new BufferedWriter(new FileWriter("resources/nicks_notes.csv"));
		    for (Player p : sorted) {
			    out.write(p.getPlayerName() + "\",\"" + p.getNickNotes());
			    out.newLine();
		    }
		    out.flush();
		    out.close();
		}
		catch(IOException ex) {
			Log.err(ex.getMessage());
		}
	}
	
	
	
	
	
	
}
