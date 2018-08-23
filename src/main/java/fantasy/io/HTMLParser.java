package fantasy.io;

import static fantasy.constants.DataSourcePaths.PLAYER_NOTES_HTML_PATH;
import static fantasy.constants.DataSourcePaths.PPR_RANKINGS_HTML_PATH;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.jaunt.Element;
import com.jaunt.UserAgent;

import fantasy.Log;
import fantasy.model.NFL;
import fantasy.model.Player;

public class HTMLParser {

	public void addNotesFromHtml() {
		UserAgent u = new UserAgent();
		try {
			u.open(new File(PLAYER_NOTES_HTML_PATH));
			Element notesWrapper = u.doc.findFirst("<div id=\"notes-wrapper\">");
			for (Element textElement : notesWrapper.findEvery("<td class=\"text\">")) {
				Player p = NFL.getPlayer(textElement.findFirst("<span class=\"title\">").findFirst("<a href>").getText());
				p.setNotes(textElement.findFirst("<div class=\"player-note\">").getText());
			}
		} catch (Exception e) {
			Log.err("Could not add notes from HTML: " + e.getMessage());
		} 
	}

//	public void getPlayersFromHtml() {
//		Map<String, String> tierPositionMap = new HashMap<String, String>();
//		int tierStartIndex = 1;
//		UserAgent u = new UserAgent();
//		try {
//			u.open(new File(PPR_RANKINGS_HTML_PATH));
//			Element tbody = u.doc.findFirst("tbody");
//			for (Element row : tbody.findEvery("tr")) {
//				if (row.getAt("style").contains("display: none")) { // if empty first row
//					continue;
//				} else if (row.getAt("class").contains("tier-row")) {
//					String tierText = row.findFirst("tr").getText();
//					String tierNum = tierText.split(" ")[1];
//					tierPositionMap.put(String.valueOf(tierStartIndex), tierNum);
//				} else if (row.getAt("class").contains("player-row")) {
//					String playerTier = String.valueOf(tierStartIndex++);
//				}
//				
//					
//				Player p = NFL.getPlayer(textElement.findFirst("<span class=\"title\">").findFirst("<a href>").getText());
//				p.setNotes(textElement.findFirst("<div class=\"player-note\">").getText());
//			}
//		} catch (Exception e) {
//			Log.err("Could not add notes from HTML: " + e.getMessage());
//		} 
//	}
	
	

}
