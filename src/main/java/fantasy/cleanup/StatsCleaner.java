package fantasy.cleanup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

import fantasy.Log;
import fantasy.comparator.AlphabetizedPlayerComparator;
import fantasy.model.NFL;
import fantasy.model.Player;

public class StatsCleaner {

	static String first = null;
	
	public static void cleanupTags() {
		try {
			TreeSet<Player> sorted = new TreeSet<Player>(new AlphabetizedPlayerComparator());
			BufferedReader in = new BufferedReader(new FileReader(("resources/tags.csv")));
			String line = null;
			while((line = in.readLine()) != null) {
				if (line.startsWith("#")) {
					first = line;
					continue; 
				}
				Player p = NFL.getPlayer(line.split(",")[0]);
				sorted.add(p);
			}
			in.close();         
			
		    BufferedWriter out = new BufferedWriter(new FileWriter("resources/tags.csv"));
		    out.write(first);
		    for (Player p : sorted) {
		    	out.newLine();
			    out.write(p.getPlayerName() + ", " + p.getTags());
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
			BufferedReader in = new BufferedReader(new FileReader(("resources/nicks_notes.csv")));
			String line = null;
			while((line = in.readLine()) != null) {
				Player p = NFL.getPlayer(line.split("\",\"")[0]);
				try {
					p.addNicksNotes(line.split("\",\"")[1]);
				} catch (Exception e) {
					Log.err("StatsCleaner.cleanupNickNotes() :: Error trying to cleanup Nick Notes");
					e.printStackTrace();
				}
				sorted.add(p);
			}
			in.close();         
			
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
