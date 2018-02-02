package fantasy.logic;

import java.util.ArrayList;
import java.util.List;

import fantasy.Log;
import fantasy.controller.BaseController;
import fantasy.model.Drafter;
import fantasy.model.NFL;
import fantasy.model.Player;
import fantasy.model.Team;

public class LogicHandler {

	Drafter drafter;
	List<Player> suggestions;
	int numOfRounds;
	private Team team;
	ListManipulator manipulator;
	
	public LogicHandler(Drafter drafter) {
		this.drafter = drafter;
		this.team = drafter.getDraftedTeam();
		this.suggestions = new ArrayList<Player>(NFL.getAllAvailablePlayersByADPList());
		this.manipulator = new ListManipulator(suggestions, team);
	}


	/*
	 * 				LOGIC TO ADD:
	 * 
	 * 		- give rb priority (maybe in the comparator?)
	 * 		- find stats for and add player age/how long in league
	 */
	
	
	public List<Player> getMySuggestions() {
		for (Player player : NFL.getAllAvailablePlayersByADPList()) {
			manipulator.removeTooEarlyPositions(player);
			manipulator.removeTooFullPositions(player);
		}
		manipulator.doTagLogic();
		suggestions = manipulator.checkForEmptyPositions();
		return suggestions;
	}
	
	public Player getAiPick() {
		for (Player player : NFL.getAllAvailablePlayersByADPList()) {
			manipulator.removeTooEarlyPositions(player);
			manipulator.removeTooFullPositions(player);
		}
		suggestions = manipulator.checkForEmptyPositions();
		if (BaseController.mockDraftMode) {
			return suggestions.get(RandomIndexGenerator.generate());
		}
		return suggestions.get(0);
	}
	
}
