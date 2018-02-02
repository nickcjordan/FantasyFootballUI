package fantasy.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fantasy.comparator.PlayerRankComparator;
import fantasy.enums.Position;
import fantasy.enums.Tag;
import fantasy.manager.DraftManager;
import fantasy.model.NFL;
import fantasy.model.Player;
import fantasy.model.Team;
import fantasy.utility.Print;

public class ListManipulator {
	
	List<Player> suggestions;
	private Team team;

	public ListManipulator(List<Player> suggestions, Team team) {
		this.suggestions = suggestions;
		this.team = team;
	}

	public void movePlayerUp(Player player) {
		int current = suggestions.indexOf(player);
		if (current == 0) return;
		
		Player other = suggestions.get(current - 1);
		suggestions.set(current, other);
		suggestions.set(current - 1, player);
	}
	
	public void movePlayerDown(Player player) {
		int current = suggestions.indexOf(player);
		if (current == suggestions.size() - 1) return;
		
		Player other = suggestions.get(current + 1);
		suggestions.set(current, other);
		suggestions.set(current + 1, player);
	}
	
	void doTagLogic() {
		ArrayList<Player> copy = new ArrayList<Player>(suggestions);
		for (Player player : copy) {
			for (Tag tag : Tag.values()) {
				if (player.getTags().contains(tag.getTag())) {
					shiftPlayerByTag(tag, player);
				}
			}
		}
	}
	
	private void shiftPlayerByTag(Tag tag, Player player) {
		if (tag.getShift() > 0) {
			for (int i = 0; i < tag.getShift(); i++) {
				movePlayerUp(player);
			}
		} else if (tag.getShift() < 0) {
			for (int i = tag.getShift(); i < 0; i++) {
				movePlayerDown(player);
			}
		}
	}

	void removeTooFullPositions(Player player) {
		if (playerPositionIsFull(player) || isQbOrTeAndIsAlreadyPicked(player)) {
			suggestions.remove(player);
		}
	}

	private boolean isQbOrTeAndIsAlreadyPicked(Player player) {
		return (player.getPosition().equals(Position.QUARTERBACK) 
				||	(player.getPosition().equals(Position.TIGHTEND))) 
								&&	(team.getPlayersByPosition(player.getPosition()).size() > 0)
								&&	(DraftManager.getCurrentRoundNumber() < prop("te_qb_reintroduceRound"));
	}

	private boolean playerPositionIsFull(Player player) {
		return (team.getPlayersByPosition(player.getPosition()).size() >= prop(player.getPosition(), "Limit")); 
	}

	void removeTooEarlyPositions(Player player) {
		if (tooEarlyForPosition(player.getPosition())) {
			suggestions.remove(player);
		}
	}

	private boolean tooEarlyForPosition(Position position) {
		return (DraftManager.getCurrentRoundNumber() < prop(position, "Init"));
	}
	
	
	
	private int prop(Position pos, String prop) {
		return DraftManager.get(pos.getAbbrev().toLowerCase() + prop);
	}

	private int prop(String prop) {
		return DraftManager.get(prop);
	}
}
