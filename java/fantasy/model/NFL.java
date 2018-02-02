package fantasy.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import fantasy.builder.NFLBuilder;
import fantasy.builder.PlayerBuilder;
import fantasy.builder.TeamBuilder;
import fantasy.comparator.PlayerRankComparator;
import fantasy.comparator.TeamIdComparator;
import fantasy.enums.Position;
import fantasy.exception.FalifaException;
import fantasy.utility.Print;

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
	
	public HashMap<String, Player> getPlayerMap(){
		return players;
	}
	
	public static  HashMap<String, Team> getTeamMap(){
		return teams;
	}
	
	public static List<Team> getTeamList(){
		ArrayList<Team> teamList = new ArrayList<Team>();
		for (Team team : getTeamMap().values()) {
			teamList.add(team);
		}
		Collections.sort(teamList, new TeamIdComparator());
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
		Collections.sort(byPosition, new PlayerRankComparator());
		return byPosition;
	}
	
	public static List<Player> getAllAvailablePlayersList(){
		List<Player> allAvailable = new ArrayList<Player>();
		for (Player player : players.values()){
			if (player.isAvailable()){
				allAvailable.add(player);
			}
		}
		Collections.sort(allAvailable, new PlayerRankComparator());
		return allAvailable;
	}
	
	public static List<Player> getAllPickedPlayersList(){
		List<Player> allPicked = new ArrayList<Player>();
		for (Player player : players.values()){
			if (!player.isAvailable()){
				allPicked.add(player);
			}
		}
		Collections.sort(allPicked, new PlayerRankComparator());
		return allPicked;
	}
	
	public static Player getPlayer(String name) throws FalifaException{
		return checkForNullPlayer(players.get(name), name);
	}
	
	public static Player getPlayer(int id) throws FalifaException {
		return checkForNullPlayer(playersById.get(id), String.valueOf(id));
	}

	private static Player checkForNullPlayer(Player p, String name) throws FalifaException {
		if (p != null) {
			return p;
		} else {
			throw new FalifaException("Player \"" + name + "\" could not be found.");
		}
	}
	
}
