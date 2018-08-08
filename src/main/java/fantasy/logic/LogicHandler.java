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
		this.suggestions = new ArrayList<Player>(NFL.getAllAvailablePlayersByADP());
		this.manipulator = new ListManipulator(suggestions, team);
	}


	public List<Player> getMySuggestions() {
		for (Player player : NFL.getAllAvailablePlayersByADP()) {
			manipulator.removeTooEarlyPositions(player);
			manipulator.removeTooFullPositions(player);
		}
		manipulator.doTagLogic();
		suggestions = manipulator.checkForEmptyPositions();
		return suggestions;
	}
	
	public Player getAiPick() {
		for (Player player : NFL.getAllAvailablePlayersByADP()) {
//			manipulator.removeTooEarlyPositions(player); removing from AI functionality
			manipulator.removeTooFullPositions(player);
		}
		suggestions = manipulator.checkForEmptyPositions();
		if (BaseController.mockDraftMode) {
			return suggestions.get(RandomIndexGenerator.generate());
		}
		return suggestions.get(0);
	}
	
	public List<Integer> getDraftPickIndexList() {
		List<Integer> list = new ArrayList<Integer>();
		int nextRoundNum = BaseController.roundNum + 1;
		for (int i = nextRoundNum; i <= BaseController.getNumberOfRounds(); i++) {
			if (i%2 == 0) { // if round is even
				list.add((i * BaseController.draft.getDrafters().size()) - (drafter.getDraftOrderNumber() - 1));
			} else { // if round is odd
				list.add(((i-1) * BaseController.draft.getDrafters().size() ) + drafter.getDraftOrderNumber());
			}
		}
		return list;
	}
	
	
	
}
