package fantasy.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fantasy.comparator.PlayerRankComparator;
import fantasy.enums.Position;
import fantasy.manager.DisplayManager;
import fantasy.manager.DraftManager;
import fantasy.model.Drafter;
import fantasy.model.NFL;
import fantasy.model.Player;
import fantasy.model.Team;
import fantasy.utility.Print;

public class LogicHandler {

	Drafter drafter;
	DisplayManager display;
	List<Player> suggestions;
	int numOfRounds;
	private Team team;
	ListManipulator manipulator;
	
	public LogicHandler(Drafter drafter, DisplayManager display) {
		this.drafter = drafter;
		this.display = display;
		this.team = drafter.getDraftedTeam();
	}


	/*
	 * 				LOGIC TO ADD:
	 * 
	 * 		- add unknown factor of being a rookie
	 * 		- add ability to flag players
	 * 		- give rb priority (maybe in the comparator?)
	 * 		- add class to collect stats about drafted team:
	 * 			-- average rank of players drafted
	 * 			-- total draft rank
	 * 			-- strength of each position in team
	 * 		- display draft board by position
	 * 		- find stats for and add player age/how long in league
	 * 		- jy
	 */
	
	
	public List<Player> getMySuggestions() {
		suggestions = new ArrayList<Player>(NFL.getAllAvailablePlayersList());
		manipulator = new ListManipulator(suggestions, team);
		
		for (Player player : NFL.getAllAvailablePlayersList()) {
			manipulator.removeTooEarlyPositions(player);
			manipulator.removeTooFullPositions(player);
		}
		
		manipulator.doTagLogic();
		checkForEmptyPositions();
		
		return suggestions;
	}
	

	public void provideLogic() {
		System.out.println(drafter.getDraftedTeam());
		showMySuggestions();
	}
	
	private void showMySuggestions() {
		display.printTitle("SUGGESTIONS");
		display.printHeader();
		for (Player p : getMySuggestions().subList(0, 10)) {
			System.out.println(p.fullStats());
			display.printDivider();
		}
	}
	
	public Player getPlayerForMockDrafter() {
//		DraftManager.sleep(1);
		return getMySuggestions().get(0);
	}
	
	void checkForEmptyPositions() {
		List<Player> players = new ArrayList<Player>();
		for (Position position : Position.values()) {
			addIfPositionIsEmpty(players, position, prop(position, "Warn"));
		}
		if (!players.isEmpty()) {
			Collections.sort(players, new PlayerRankComparator());
			suggestions = players;
		}
	}
	
	private void addIfPositionIsEmpty(List<Player> players, Position position, int roundToWorry) {
		if ((DraftManager.getCurrentRoundNumber() >= DraftManager.getNumberOfRounds() - roundToWorry) && positionIsEmpty(position) ) {
			players.addAll(NFL.getAvailablePlayersByPositionAsList(position));
		}
	}
	
	private boolean positionIsEmpty(Position position) {
		return team.getPlayersByPosition(position).isEmpty();
	}
	
	private int prop(Position pos, String prop) {
		return DraftManager.get(pos.getAbbrev().toLowerCase() + prop);
	}
	
}
