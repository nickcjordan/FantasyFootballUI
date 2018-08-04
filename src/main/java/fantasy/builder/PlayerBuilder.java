package fantasy.builder;

import java.util.List;

import org.springframework.util.StringUtils;

import fantasy.Log;
import fantasy.exception.FalifaException;
import fantasy.model.NFL;
import fantasy.model.Player;

public class PlayerBuilder {
	
	public static Player buildPlayer(List<String> split) {
		Player player =  null;
		try {
			player =  new Player(split);
			Log.deb("PlayerBuilder.buildPlayer() :: PLAYER BUILT=[" + String.format("(%d) %s - %s", player.getId(), player.getPlayerName(), player.getTeamName()) + "]");
		} catch (Exception e) {
			Log.err("ERROR parsing line:\n" + e.getMessage());
		}
		return player;
	}
	
	public static void addNote(List<String> split) {
		if (split.size() > 1) {
			try {
				Player p = NFL.getPlayer(split.get(0));
				p.setNotes(buildNotes(split, 1));
			} catch (Exception e) {
				Log.err("ERROR could not add notes: PlayerBuilder.addnote() :: " + split.get(0) + "\n");
				e.printStackTrace();
			}
		}
	}

	public static void addAdditionalNote(List<String> split) {
		try {
			NFL.getPlayer(split.get(0)).addAdditionalNotes(buildNotes(split, 1));
		} catch (Exception e) {
			Log.err(e.getMessage() + "... was trying to add additional notes.");
		}
	}

	private static String buildNotes(List<String> split, int start) {
		StringBuilder builder = new StringBuilder();
		for (int i = start; i < split.size(); i++) {
			String note = split.get(i) == null ? "" : split.get(i);
			builder.append(note.trim());
			if (i != split.size()-1) {
				builder.append(" ");
			}
		}
		return getPrettyNotes(builder.toString().split(" "));
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

	public static void addTag(List<String> split) throws FalifaException {
		Player player = NFL.getPlayer(split.get(0));
		player.setTags(player.getTags()+split.get(1));
	}

	public static void addOLineRankings(List<String> split) {
		Player player = NFL.getPlayer(split.get(0));
		player.setOline_rank(split.get(1));
		player.setOline_passScore(split.get(2));
		player.setOline_runScore(split.get(3));
		player.setOline_avgScore(split.get(4));
	}

	public static void addPlayerTargets(List<String> split) {
		Player player = NFL.getPlayer(split.get(0));
		if (player.getPlayerName().equals("Mark Ingram") || split.get(0).equals("Mark Ingram")) {
			System.out.println();
		}
		player.setTotalTargets(split.get(1));
		player.setAvgTargets(split.get(2));
	}

}
