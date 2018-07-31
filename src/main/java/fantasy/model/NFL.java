package fantasy.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import fantasy.Log;
import fantasy.builder.NFLBuilder;
import fantasy.comparator.AlphabetizedTeamComparator;
import fantasy.comparator.PlayerADPComparator;
import fantasy.enums.Position;

public class NFL {

	static HashMap<String, Player> players;
	static HashMap<Integer, Player> playersById;
	static HashMap<String, Team> teams;
	static HashMap<Integer, Team> teamsById;
	
	static {
		NFLBuilder builder = new NFLBuilder();
		players = builder.getPlayers();
		playersById = builder.getPlayersById();
		teams = builder.getTeams();
		teamsById = builder.getTeamsById();
		builder.addNotesToPlayers();
		builder.addTagsToPlayers();
	}
	
	public static HashMap<String, Player> getPlayerMap(){
		return players;
	}
	
	public static  HashMap<String, Team> getTeamMap(){
		return teams;
	}
	
	public static List<Team> getTeamList(){
		ArrayList<Team> teamList = new ArrayList<Team>();
		for (Team team : teamsById.values()) {
			teamList.add(team);
		}
		Collections.sort(teamList, new AlphabetizedTeamComparator());
		return teamList;
	}
	
	public static Team getTeam(int id) {
		return teamsById.get(id);
	}
	
	public Team getTeam(String name) {
		return teams.get(name);
	}
	
	public static List<Player> getAvailablePlayersByPositionAsList(Position position){
		List<Player> byPosition = new ArrayList<Player>();
		for (Player player : players.values()){
			if (player.getPosition().equals(position) && player.isAvailable()){
				byPosition.add(player);
			}
		}
		Collections.sort(byPosition, new PlayerADPComparator());
		return byPosition;
	}
	
	public static List<Player> getAllAvailablePlayersByADPList(){
		List<Player> allAvailable = new ArrayList<Player>();
		for (Player player : players.values()){
			if (player.isAvailable()){
				allAvailable.add(player);
			}
		}
		Collections.sort(allAvailable, new PlayerADPComparator());
		return allAvailable;
	}
	
	public static List<Player> getAllPickedPlayersList(){
		List<Player> allPicked = new ArrayList<Player>();
		for (Player player : players.values()){
			if (!player.isAvailable()){
				allPicked.add(player);
			}
		}
		Collections.sort(allPicked, new PlayerADPComparator());
		return allPicked;
	}
	
	public static Player getPlayer(String[] notes) {
		return (players.get(notes[1]) == null) ? players.get(extractTeamName(notes)) : players.get(notes[1]);
	}
	
	private static String extractTeamName(String[] stats) {
		if (stats[2].isEmpty()) {
			String[] split = stats[1].split(" ");
			stats[2] = split[split.length - 1];
		}
		return stats[2];
	}
	
	public static Player getPlayer(int id) {
		return playersById.get(id);
	}

	public static Player getPlayer(String p) {
		Player player = players.get(p);
		if (player == null) {
			Log.err("Player " + p + " not found in players map");
		}
		return player;
		/*return players.get(p);*/
	}

	public static void resetPlayers() {
		for(Player player : getPlayerMap().values()) {
			resetPlayer(player);
		}
	}

	private static void resetPlayer(Player player) {
		player.setAvailable(true);
		player.setRoundDrafted(0);
	}
	
}
