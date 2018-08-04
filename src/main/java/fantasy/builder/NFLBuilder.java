package fantasy.builder;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

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

	private void addPlayersToPlayerLists() throws FileNotFoundException {
		for (List<String> split : dataReader.getSplitLinesFromFile("resources/master_players.csv")) {
			if (split.get(CSVFieldMapping.POS.getIndex()).contains("TOL")) { // TOL == Total Offensive Line?
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

	public void addNotesToPlayers() throws FileNotFoundException {
		for (List<String> split : dataReader.getSplitLinesFromFile("resources/FantasyPros_notes.csv")) {
			try {
				PlayerBuilder.addNote(split);
			} catch (Exception e) {
				Log.err("Could not add notes : " + e.getMessage());
			}
		}
		for (List<String> split : dataReader.getSplitLinesFromFile("resources/nicks_notes.csv")) {
			try {
				PlayerBuilder.addAdditionalNote(split);
			} catch (Exception e) {
				Log.err("Could not add notes : " + e.getMessage());
			}
		}
	}

	public void addTagsToPlayers() throws FileNotFoundException {
		for (List<String> split : dataReader.getSplitLinesFromFile("resources/tags.csv")) {
			try {
				PlayerBuilder.addTag(split);
			} catch (Exception e) {
				Log.err("Could not set tags: " + split.get(0));
			}
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

	public void addOLineRankingsToPlayers() throws FileNotFoundException {
		for (List<String> split : dataReader.getSplitLinesFromFile("resources/o-line_to_player_rankings.csv")) {
			try {
				PlayerBuilder.addOLineRankings(split);
			} catch (Exception e) {
				Log.err("Could not set o line rank: " + split.get(0));
			}
		}
	}

	public void addTargetsToPlayers() throws FileNotFoundException {
		for (List<String> split : dataReader.getSplitLinesFromFile("resources/targets.csv")) {
			try {
				PlayerBuilder.addPlayerTargets(split);
			} catch (Exception e) {
				Log.err("Could not set targets: " + split.get(0));
			}
		}
	}

}
