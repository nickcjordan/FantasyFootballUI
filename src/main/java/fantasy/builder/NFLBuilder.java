package fantasy.builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import org.springframework.util.StringUtils;

import fantasy.Log;
import fantasy.enums.Position;
import fantasy.model.NFLTeam;
import fantasy.model.Player;
import fantasy.model.Team;

public class NFLBuilder {
	
	HashMap<String, Player> players;
	HashMap<Integer, Player> playersById;
	HashMap<String, Team> teams;
	HashMap<Integer, Team> teamsById;
	
	public NFLBuilder() {
		players = new HashMap<String, Player>();
		teams = new HashMap<String, Team>();
		playersById = new HashMap<Integer, Player>();
		teamsById = new HashMap<Integer, Team>();
		buildTeams();
	}
	
	private void buildTeams() {
		try { 
			addTeamsToTeamLists();
			addPlayersToPlayerLists();
		} catch (FileNotFoundException e) {
			Log.err("NFL could not be built.");
		}
	}
	
	private void addPlayersToPlayerLists() throws FileNotFoundException{
		Scanner scanner = new Scanner(new File("resources/master_players.csv"));
		scanner.nextLine(); //move past header
		String line = "";
		while(scanner.hasNextLine()){
			line = scanner.nextLine();
			if ((!StringUtils.isEmpty(line)) && (!line.equals("\"\"")) && (!line.contains("TOL"))){ // TOL is for offensive line?
				buildAndAddPlayer(line);
			}
		}
		scanner.close();
	}
	
	private void buildAndAddPlayer(String line) {
		Player player = PlayerBuilder.buildPlayer(line);
		players.put(player.getPlayerName(), player);
		TeamBuilder.addPlayerToTeam(player, teams.get(player.getTeamName()));
		playersById.put(player.getId(), player);
	}
	
	private void addTeamsToTeamLists() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("resources/team_names.csv"));
		while(scanner.hasNextLine()){
			buildAndAddTeam(scanner.nextLine());
		}
		scanner.close();
	}

	private Team buildAndAddTeam(String line) {
		Team team = TeamBuilder.buildTeamFromInput(line);
		teams.put(team.getName(), team);
		teams.put(team.getAbbrev(), team);
		teamsById.put(team.getId(), team);
		return team;
	}

	public void addNotesToPlayers() {
		try { //handle expert notes
			Scanner notes = new Scanner(new File("resources/FantasyPros_notes.csv"));
			while(notes.hasNextLine()){
				PlayerBuilder.addNote(notes.nextLine());
			}
			notes.close();
			//handle personal notes
			notes = new Scanner(new File("resources/nicks_notes.csv"));
			while(notes.hasNextLine()){
				PlayerBuilder.addAdditionalNote(notes.nextLine());
			}
			notes.close();
		} catch (FileNotFoundException e) {
			Log.err("Could not add notes : " + e.getMessage());
		}
	}

	public void addTagsToPlayers() {
		try {
		Scanner tags = new Scanner(new File("resources/tags.csv"));
		tags.nextLine(); // move past header
		while(tags.hasNextLine()){
			PlayerBuilder.addTag(tags.nextLine());
		}
		tags.close();
		} catch (Exception e) {
			Log.err("Could not set tags : " + e.getMessage());
		}
	}

	public HashMap<String, Player> getPlayers() {
		return players;
	}

	public HashMap<Integer, Player> getPlayersById() {
		return playersById;
	}

	public HashMap<String, Team> getTeams() {
		return teams;
	}

	public HashMap<Integer, Team> getTeamsById() {
		return teamsById;
	}

	
}
