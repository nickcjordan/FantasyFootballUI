package fantasy.comparator;

import java.util.Comparator;

import fantasy.model.Player;

public class PlayerDraftedOrderComparator implements Comparator<Player> {

	public int compare(Player p1, Player p2) {
		return Integer.valueOf(p1.getRoundDrafted()).compareTo(Integer.valueOf(p2.getRoundDrafted()));
	}

}
