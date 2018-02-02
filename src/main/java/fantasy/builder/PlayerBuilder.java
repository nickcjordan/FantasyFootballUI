package fantasy.builder;

import fantasy.Log;
import fantasy.exception.FalifaException;
import fantasy.model.NFL;
import fantasy.model.Player;

public class PlayerBuilder {
	
	public static Player buildPlayer(String line) {
		Player player =  new Player(parseLineForPlayerStats(line));
		//Log.deb("PlayerBuilder.buildPlayer() :: PLAYER BUILT=[" + String.format("(%d) %s - %s", player.getId(), player.getPlayerName(), player.getTeamName()) + "]");
		return player;
	}
	
	public static String[] parseLineForPlayerStats(String line) {
		String[] cols = line.split(",");
		String[] edited = new String[cols.length];
		for (int i = 0; i < cols.length; i++){
			edited[i] = (cols[i].replaceAll("\"", "").isEmpty()) ? "NA" : cols[i].replaceAll("\"", "");
			//edited[i] = cols[i].replaceAll("\"", "");
		}
		//for(String x : edited) { Log.deb(x); }
		return edited;
	}
	
	public static String[] parseLineForNotes(String line) {
		String[] cols = line.split("\",\"");
		String[] edited = new String[cols.length];
		for (int i = 0; i < cols.length; i++){
			edited[i] = cols[i].replaceAll("\"", "");
		}
		return edited;
	}
	
	private static String getPrettyNotes(String[] words) {
		int i = 0;
		StringBuilder paragraph = new StringBuilder();
		while (i < words.length) {
			StringBuilder line = new StringBuilder();
			while (line.length() < 100 && i < words.length) {
				line.append(" " + words [i++]);
			}
			paragraph.append(line.toString() + "\n");
		}
		return paragraph.toString();
	}

	public static void addNote(String line) {
		//Log.deb("Adding notes for player: " + line);
		String[] notes = parseLineForNotes(line);
		if (notes.length > 5) {
			try {
				Player p = NFL.getPlayer(notes);
				p.setNotes(buildNotes(notes, 5));
			} catch (Exception e) {
				Log.err("ERROR could not add notes: PlayerBuilder.addnote() :: " + line + "\n");
				e.printStackTrace();
			}
		}
	}

	public static void addAdditionalNote(String line) {
		String[] notes = parseLineForNotes(line);
		try {
			NFL.getPlayer(notes[0]).addAdditionalNotes(buildNotes(notes, 1));
		} catch (Exception e) {
			Log.err(e.getMessage() + "... was trying to add additional notes.");
		}
	}

	private static String buildNotes(String[] notes, int start) {
		StringBuilder builder = new StringBuilder();
		for (int i = start; i < notes.length; i++) {
			String note = notes[i] == null ? "" : notes[i];
			builder.append(note.trim());
			if (i != notes.length-1) {
				builder.append(" ");
			}
		}
		return getPrettyNotes(builder.toString().split(" "));
	}

	public static void addTag(String nextLine) throws FalifaException {
		String[] details = nextLine.split(",");
		Player player = NFL.getPlayer(details[0].trim());
		player.setTags(details[1].trim());
	}

}
