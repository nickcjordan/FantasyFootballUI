package fantasy.builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.springframework.util.StringUtils;

import fantasy.Log;
import fantasy.enums.CSVFieldMapping;
import fantasy.io.DataFileReader;
import fantasy.model.Player;
import fantasy.model.Team;

public class NFLBuilder {
	
	HashMap<String, Player> players;
	HashMap<Integer, Player> playersById;
	HashMap<String, Team> teams;
	HashMap<Integer, Team> teamsById;
	private DataFileReader dataReader;
	
	public NFLBuilder() {
		players = new HashMap<String, Player>();
		teams = new HashMap<String, Team>();
		playersById = new HashMap<Integer, Player>();
		teamsById = new HashMap<Integer, Team>();
		dataReader = new DataFileReader();
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
		for (List<String> split : dataReader.getSplitLinesFromFile("resources/master_players.csv")) {
			if (split.get(CSVFieldMapping.POS.getIndex()).contains("TOL")) { //TOL == Total Offensive Line?
				continue;
			}
			try {
				Player player = PlayerBuilder.buildPlayer(split);
				players.put(player.getPlayerName(), player);
				TeamBuilder.addPlayerToTeam(player, teams.get(player.getTeamName()));
				playersById.put(player.getId(), player);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void addTeamsToTeamLists() throws FileNotFoundException {
		for (List<String> split : dataReader.getSplitLinesFromFile("resources/team_names.csv")) {
			Team team = TeamBuilder.buildTeamFromInput(split);
			teams.put(team.getName(), team);
			teams.put(team.getAbbrev(), team);
			teamsById.put(team.getId(), team);
		}
	}

	public void addNotesToPlayers() {
		try {
			for (List<String> split : dataReader.getSplitLinesFromFile("resources/FantasyPros_notes.csv")) {
				PlayerBuilder.addNote(split);
			}
			for (List<String> split : dataReader.getSplitLinesFromFile("resources/nicks_notes.csv")) {
				PlayerBuilder.addAdditionalNote(split);
			}
		} catch (FileNotFoundException e) {
			Log.err("Could not add notes : " + e.getMessage());
		}
	}

	public void addTagsToPlayers() {
		try {
			for (List<String> split : dataReader.getSplitLinesFromFile("resources/tags.csv")) {
				PlayerBuilder.addTag(split);
			}
		} catch (Exception e) {
			Log.err("Could not set tags : " );
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
