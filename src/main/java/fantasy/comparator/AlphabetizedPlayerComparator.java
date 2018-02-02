package fantasy.comparator;

import java.util.Comparator;

import fantasy.model.Player;
import fantasy.model.Team;

public class AlphabetizedPlayerComparator implements Comparator<Player>{
	
	public int compare(Player p1, Player p2){
		return (p1.getPlayerName()).compareTo(p2.getPlayerName());
	}
}
