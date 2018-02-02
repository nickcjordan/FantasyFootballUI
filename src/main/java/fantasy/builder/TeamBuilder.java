package fantasy.builder;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import fantasy.Log;
import fantasy.model.Draft;
import fantasy.model.NFL;
import fantasy.model.NFLTeam;
import fantasy.model.Player;
import fantasy.model.Team;

public class TeamBuilder {
	
	public static HashMap<String, String> mascotToTeamNameMapping = new HashMap<String, String>();
	public static HashMap<String, String> teamNameToMascotMapping = new HashMap<String, String>();

	static int id;
	
	static {
		setNameMappings();
		id = 0;
	}

	public static Team buildTeamFromInput(String line) {
		String[] in = line.split(",");
		return new NFLTeam(in[0], in[1], in[2]);
	}
	
	public static String getProperTeamName(String teamName) {
		String name = teamName.length() > 3 ? mascotToTeamNameMapping.get(teamName) : teamName;
		return name == null ? teamName : name;
	}

	public static String getMascotByTeamName(String teamName) {
		return teamNameToMascotMapping.get(teamName);
	}
	
	public static String getTeamNameByMascot(String mascot) {
		return mascotToTeamNameMapping.get(mascot);
	}
	
	private static void setNameMappings() {
		mascotToTeamNameMapping.put("Dolphins", "MIA");
		mascotToTeamNameMapping.put("Raiders", "OAK");
		mascotToTeamNameMapping.put("Cowboys", "DAL");
		mascotToTeamNameMapping.put("Packers", "GB");
		mascotToTeamNameMapping.put("Texans", "HOU");
		mascotToTeamNameMapping.put("Broncos", "DEN");
		mascotToTeamNameMapping.put("Saints", "NO");
		mascotToTeamNameMapping.put("Titans", "TEN");
		mascotToTeamNameMapping.put("Lions", "DET");
		mascotToTeamNameMapping.put("Steelers", "PIT");
		mascotToTeamNameMapping.put("Bears", "CHI");
		mascotToTeamNameMapping.put("Vikings", "MIN");
		mascotToTeamNameMapping.put("Jaguars", "JAC");
		mascotToTeamNameMapping.put("Panthers", "CAR");
		mascotToTeamNameMapping.put("Buccaneers", "TB");
		mascotToTeamNameMapping.put("Chiefs", "KC");
		mascotToTeamNameMapping.put("Chargers", "LAC");
		mascotToTeamNameMapping.put("49ers", "SF");
		mascotToTeamNameMapping.put("Ravens", "BAL");
		mascotToTeamNameMapping.put("Colts", "IND");
		mascotToTeamNameMapping.put("Bills", "BUF");
		mascotToTeamNameMapping.put("Cardinals", "ARI");
		mascotToTeamNameMapping.put("Browns", "CLE");
		mascotToTeamNameMapping.put("Redskins", "WAS");
		mascotToTeamNameMapping.put("Falcons", "ATL");
		mascotToTeamNameMapping.put("Giants", "NYG");
		mascotToTeamNameMapping.put("Jets", "NYJ");
		mascotToTeamNameMapping.put("Rams", "LAR");
		mascotToTeamNameMapping.put("Seahawks", "SEA");
		mascotToTeamNameMapping.put("Bengals", "CIN");
		mascotToTeamNameMapping.put("Eagles", "PHI");
		mascotToTeamNameMapping.put("Patriots", "NE");
		mascotToTeamNameMapping.put("Free Agent", "FA");
		
		for (Entry<String, String> entry : mascotToTeamNameMapping.entrySet()) {
			teamNameToMascotMapping.put(entry.getValue(), entry.getKey());
		}
	}

	public static void addPlayerToTeam(Player player, Team team) {
			String pos = player.getPos();
			//Log.deb(player.getNameAndId());
	
			if (pos.equals("QB")){
				team.getQb().add(player);
				checkIfHandcuff(player, team.getQb());
			} if (pos.equals("RB")){
				team.getRb().add(player);
				checkIfHandcuff(player, team.getRb());
			} if (pos.equals("WR")){
				team.getWr().add(player);
				checkIfHandcuff(player, team.getWr());
			} if (pos.equals("TE")){
				team.getTe().add(player);
				checkIfHandcuff(player, team.getTe());
			} if (pos.equals("K")){
				team.getK().add(player);
				checkIfHandcuff(player, team.getK());
			} if (pos.equals("DST")){
				team.getD().add(player);
				checkIfHandcuff(player, team.getD());
			}
	}
	
	public static void addPlayerToDraftersTeam(Player player, Team team) {
		String pos = player.getPos();

		if (pos.equals("QB")){
			team.getQb().add(player);
		} if (pos.equals("RB")){
			team.getRb().add(player);
		} if (pos.equals("WR")){
			team.getWr().add(player);
		} if (pos.equals("TE")){
			team.getTe().add(player);
		} if (pos.equals("K")){
			team.getK().add(player);
		} if (pos.equals("DST")){
			team.getD().add(player);
		}
}
	
	private static void checkIfHandcuff(Player player, List<Player> positionList) {
		if (positionList.size() > 1) {
			positionList.get(0).addHandcuff(player);
		}
	}

	
}
