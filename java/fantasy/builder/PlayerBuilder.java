package fantasy.builder;

import fantasy.exception.FalifaException;
import fantasy.model.NFL;
import fantasy.model.Player;
import fantasy.utility.Print;

public class PlayerBuilder {
	
	static int id = 1;
	
	public static Player buildPlayer(String line) {
		return new Player(parseLineForPlayerStats(line), id++);
	}
	
	public static String[] parseLineForPlayerStats(String line) {
		String[] cols = line.split(",");
		String[] edited = new String[cols.length];
		for (int i = 0; i < cols.length; i++){
			edited[i] = (cols[i].replaceAll("\"", "").isEmpty()) ? "NA" : cols[i].replaceAll("\"", "");
		}
		return edited;
	}
	
	public static String[] parseLineForNotes(String line) {
		String[] cols = line.split(",");
		for (String s : cols){
			s = s.replaceAll("\"", "");
		}
		return cols;
	}
	
	private static String getPrettyNotes(String[] words) {
		int i = 0;
		StringBuilder paragraph = new StringBuilder();
		while (i < words.length) {
			StringBuilder line = new StringBuilder();
			while (line.length() < 80 && i < words.length) {
				line.append(" " + words [i++]);
			}
			paragraph.append(line.toString() + "\n");
		}
		return paragraph.toString();
	}

	public static void addNote(String line) {
		String[] notes = parseLineForNotes(line);
		if (notes.length > 5) {
			try {
				Player player = NFL.getPlayer(notes[1]);
				String note = buildNotes(notes, 5);
				player.setNotes(note);
			} catch (Exception e) {
				Print.error(e.getMessage());
			}
		}
	}

	public static void addAdditionalNote(String line) {
		String[] notes = parseLineForNotes(line);
		try {
			NFL.getPlayer(notes[0]).addAdditionalNotes(buildNotes(notes, 1));
		} catch (Exception e) {
			Print.error(e.getMessage() + ".. was trying to add additional notes.");
		}
	}

	private static String buildNotes(String[] notes, int start) {
		StringBuilder builder = new StringBuilder();
		for (int i = start; i < notes.length; i++) {
			String note = notes[i] == null ? "" : notes[i];
			builder.append(note.trim());
			if (i != notes.length-1) {
				builder.append(", ");
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
