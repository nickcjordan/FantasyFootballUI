package fantasy.builder;

import java.util.HashMap;
import java.util.Map.Entry;

import fantasy.model.Player;
import fantasy.model.Team;

public class TeamBuilder {
	
	static int id;
	
	static {
		setNameMappings();
		id = 0;
	}

	public static Team buildTeamFromInput(String line) {
		String[] in = line.split(",");
		return new Team(in[0], in[1], ++id);
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
	
	public static void addPlayerToTeam(Player player, Team team) {
			String pos = player.getPos();
	
			if (pos.equals("QB")){
				team.getQb().add(player);
			} if (pos.equals("RB")){
				team.getRb().add(player);
				checkIfHandcuff(player, team);
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
	
	private static void checkIfHandcuff(Player player, Team team) {
		if (team.getRb().size() > 1) {
			team.getRb().get(0).addHandcuff(player);
		}
	}

	
}
