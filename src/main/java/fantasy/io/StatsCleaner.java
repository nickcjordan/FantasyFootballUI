package fantasy.io;

import static fantasy.constants.DataSourcePaths.PLAYERNOTES_CUSTOM_PATH;
import static fantasy.constants.DataSourcePaths.TAGS_CUSTOM_PATH;

import java.io.BufferedWriter;
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
			for (List<String> split : dataReader.getSplitLinesFromFile(TAGS_CUSTOM_PATH)) {
				try {
					Player p = NFL.getPlayer(split.get(0));
					sorted.add(p);
				} catch (Exception e) {
					Log.err("ERROR when cleaning up tags :: Player not found in NFL player list: " + split.get(0));
				}
			}
			
		    BufferedWriter out = new BufferedWriter(new FileWriter(TAGS_CUSTOM_PATH));
		    out.write(HEADER);
		    for (Player p : sorted) {
		    	out.newLine();
			    out.write(p.getPlayerName() + "," + p.getTags());
		    }
		    out.flush();
		    out.close();
		} catch(IOException ex) {
			Log.err(ex.getMessage());
		}
	}
	
	
	public static void cleanupNickNotes() {
		try {
			TreeSet<Player> sorted = new TreeSet<Player>(new AlphabetizedPlayerComparator());
			for (List<String> split : dataReader.getSplitLinesFromFile(PLAYERNOTES_CUSTOM_PATH)) {
				
				Player p = NFL.getPlayer(split.get(0));
				try {
					p.addNicksNotes(split.get(1));
				} catch (Exception e) {
					Log.err("StatsCleaner.cleanupNickNotes() :: Error trying to cleanup Nick Notes");
					e.printStackTrace();
				}
				sorted.add(p);
			}
			
		    BufferedWriter out = new BufferedWriter(new FileWriter(PLAYERNOTES_CUSTOM_PATH));
		    for (Player p : sorted) {
			    out.write(p.getPlayerName() + "\",\"" + p.getNickNotes());
			    out.newLine();
		    }
		    out.flush();
		    out.close();
		} catch(IOException ex) {
			Log.err(ex.getMessage());
		}
	}
	
	
	
	
	
	
}
