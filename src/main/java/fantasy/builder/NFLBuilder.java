package fantasy.builder;

import static fantasy.constants.DataSourcePaths.ECR_FANTASYPROS_PATH;
import static fantasy.constants.DataSourcePaths.NFL_TEAM_NAMES_PATH;
import static fantasy.constants.DataSourcePaths.OLINERANK_TO_PLAYER_MAPPING_PATH;
import static fantasy.constants.DataSourcePaths.PLAYERNOTES_CUSTOM_PATH;
import static fantasy.constants.DataSourcePaths.PLAYERNOTES_EXPERTS_PATH;
import static fantasy.constants.DataSourcePaths.PLAYERS_TO_TARGET_PATH;
import static fantasy.constants.DataSourcePaths.PLAYER_PICTURES_PATH;
import static fantasy.constants.DataSourcePaths.PREVIOUS_SEASON_TARGET_SHARE_PATH;
import static fantasy.constants.DataSourcePaths.TAGS_CUSTOM_PATH;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

import fantasy.Log;
import fantasy.constants.CSVFieldMapping;
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
		for (List<String> split : dataReader.getSplitLinesFromFile(ECR_FANTASYPROS_PATH)) {
			if (split.get(CSVFieldMapping.POS.getIndex()).contains("TOL")) { // TOL == Total Offensive Line?
				continue;
			}
			try {
				Player player = PlayerBuilder.buildPlayer(split);
				players.put(player.getPlayerName(), player);
				TeamBuilder.addPlayerToTeam(player, teams.get(player.getTeamName()));
				playersById.put(player.getId(), player);
			} catch (Exception e) {
				Log.err("ERROR adding player to players list: " + split.get(CSVFieldMapping.PLAYER_NAME.getIndex()));
			}
		}
	}

	private void addTeamsToTeamLists() throws FileNotFoundException {
		for (List<String> split : dataReader.getSplitLinesFromFile(NFL_TEAM_NAMES_PATH)) {
			Team team = TeamBuilder.buildTeamFromInput(split);
			teams.put(team.getName(), team);
			teams.put(team.getAbbrev(), team);
			teamsById.put(team.getId(), team);
		}
	}

	public void addNotesToPlayers() throws FileNotFoundException {
		for (List<String> split : dataReader.getSplitLinesFromFile(PLAYERNOTES_EXPERTS_PATH)) {
			try {
				PlayerBuilder.addNote(split);
			} catch (Exception e) {
				Log.err("Could not add notes for " + e.getMessage());
			}
		}
		for (List<String> split : dataReader.getSplitLinesFromFile(PLAYERNOTES_CUSTOM_PATH)) {
			try {
				PlayerBuilder.addAdditionalNote(split);
			} catch (Exception e) {
				Log.err("Could not add notes for " + e.getMessage());
			}
		}
	}

	public void addTagsToPlayers() throws FileNotFoundException {
		for (List<String> split : dataReader.getSplitLinesFromFile(TAGS_CUSTOM_PATH)) {
			try {
				PlayerBuilder.addTag(split);
			} catch (Exception e) {
				Log.err("Could not set tags for " + split.get(0) + " :: " + e.getMessage());
			}
		}
	}

	public void addOLineRankingsToPlayers() throws FileNotFoundException {
		for (List<String> split : dataReader.getSplitLinesFromFile(OLINERANK_TO_PLAYER_MAPPING_PATH)) {
			try {
				PlayerBuilder.addOLineRankings(split);
			} catch (Exception e) {
				Log.err("Could not set o line rank for " + split.get(0) + " :: " + e.getMessage());
			}
		}
	}

	public void addTargetsToPlayers() throws FileNotFoundException {
		for (List<String> split : dataReader.getSplitLinesFromFile(PREVIOUS_SEASON_TARGET_SHARE_PATH)) {
			try {
				PlayerBuilder.addPlayerTargets(split);
			} catch (Exception e) {
				Log.err("Could not set targets for " + split.get(0) + " :: " + e.getMessage());
			}
		}
	}

	public void addPictureLinksToPlayers() throws FileNotFoundException {
		for (List<String> split : dataReader.getSplitLinesFromFile(PLAYER_PICTURES_PATH)) {
			try {
				PlayerBuilder.addPlayerPicLinks(split);
			} catch (Exception e) {
				Log.err("Could not set picture links for " + split.get(0) + " :: " + e.getMessage());
			}
		}
	}

	public void setPlayersToTarget() throws FileNotFoundException {
		for (String name : dataReader.getLinesFromFile(PLAYERS_TO_TARGET_PATH)) {
			try {
				PlayerBuilder.setPlayerAsATarget(name);
			} catch (Exception e) {
				Log.err("Could not set player as a target: " + name + " :: " + e.getMessage());
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

}
