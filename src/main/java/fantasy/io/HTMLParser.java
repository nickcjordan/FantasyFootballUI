package fantasy.io;

import static fantasy.constants.DataSourcePaths.PLAYER_NOTES_HTML_PATH;
import static fantasy.constants.DataSourcePaths.PPR_RANKINGS_HTML_PATH;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jaunt.Element;
import com.jaunt.UserAgent;

import fantasy.Log;
import fantasy.builder.TeamBuilder;
import fantasy.model.NFL;
import fantasy.model.Player;
import fantasy.model.Team;

public class HTMLParser {

	public void addNotesFromHtml() {
		UserAgent u = new UserAgent();
		try {
			u.open(new File(PLAYER_NOTES_HTML_PATH));
			Element notesWrapper = u.doc.findFirst("<div id=\"notes-wrapper\">");
			for (Element textElement : notesWrapper.findEvery("<td class=\"text\">")) {
				String name = textElement.findFirst("<span class=\"title\">").findFirst("<a href>").getText();
				name = name.replaceAll("\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t", " ");
				Player p = null;
				try {
					p = NFL.getPlayer(name);
				} catch (Exception e) {
					String mascot = name.split(" ")[1];
					try {
						p = NFL.getPlayer(TeamBuilder.getTeamNameByMascot(mascot));
					} catch (Exception ex) {
						
					}
				}
				String notes = textElement.findFirst("<div class=\"player-note\">").getText();
				notes = notes.replaceAll("\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t", " ");
				if (notes != null && !notes.equals("null")) {
					p.addAdditionalNotes(notes);
				} else {
					System.out.println();
				}
			}
		} catch (Exception e) {
			Log.err("Could not add notes from HTML: " + e.getMessage());
		} 
	}

	public List<Player> getPlayersFromHtml() {
		int currentTier = 0;
		List<Player> players = new ArrayList<Player>();
		Map<String, String> tierPositionMap = new HashMap<String, String>();
		int tierStartIndex = 1;
		UserAgent u = new UserAgent();
		try {
			String name = "";
			u.open(new File(PPR_RANKINGS_HTML_PATH));
			Element tbody = u.doc.findFirst("<tbody>");
			try {
				for (Element row : tbody.findEvery("<tr>")) { name = "";
					if (row.hasAttribute("style") && row.getAt("style").contains("display: none")) { 
						continue; 
					} // if empty first row, skip
					else if (row.hasAttribute("class") && row.getAt("class").contains("tier-row")) { // set tier number
						currentTier++;
						String tierText = row.findFirst("<td>").getText();
						String tierNum = tierText.split(" ")[1];
						tierPositionMap.put(tierNum, String.valueOf(tierStartIndex));
					} 
					else if (row.hasAttribute("class") && row.getAt("class").contains("player-row")) { // build player
						Player p = new Player();
						tierStartIndex++;
						p.setTier(currentTier);
						Iterator<Element> iter = row.findEvery("<td>").iterator();
						if (iter.hasNext()) { p.setRank(iter.next().getText()); }
						if (iter.hasNext()) { 
								iter.next();
						} 
						if (iter.hasNext()) { 
							Element namesElement = iter.next();
							p.setPlayerName(namesElement.findFirst("<span class=\"full-name\">").getText()); 
							name = p.getPlayerName();
							try {
								p.setTeamName(namesElement.findFirst("<a href=\"/nfl/players/free-agents.php\">").getText());
								System.out.println();
							} catch(Exception e) {
								try {
									p.setTeamName(namesElement.findFirst("<small class=\"grey\">").getText());
									System.out.println();
								} catch (Exception e1) {
									p.setTeamName(p.extractTeamNameFromDSTPlayer(name));
									System.out.println();
								}
								
							}
						}
						if (iter.hasNext()) { p.setPosAndPosRank(iter.next().getText()); }
						if (iter.hasNext()) { p.setBye(iter.next().getText()); }
						if (iter.hasNext()) { p.setBest(iter.next().getText()); }
						if (iter.hasNext()) { p.setWorst(iter.next().getText()); }
						if (iter.hasNext()) { p.setAvg(iter.next().getText()); }
						if (iter.hasNext()) { p.setStd_dev(iter.next().getText()); }
						if (iter.hasNext()) { p.setAdp(p.getCorrectAdp(iter.next().getText())); }
						if (iter.hasNext()) { p.setVersus(iter.next().getText()); }
						p.finishBuildingPlayer();
						players.add(p);
						Log.info("Player built: " + p.getPlayerName() + " :: " + p.getId());
					} else {
						Log.info("No useful info found on <tr> line of html: " + row.toString());
					}
				}
			} catch (Exception e) {
				Log.err("Error building player from html:" + name + "\n" + e.getMessage());
			}
		} catch (Exception e) {
			Log.err("Could not add notes from HTML: " + e.getMessage());
		} 
		return players;
	}
}
