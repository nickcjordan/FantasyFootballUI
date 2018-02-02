package fantasy.comparator;

import java.util.Comparator;

import fantasy.model.Drafter;
import fantasy.model.Player;

public class DraftOrderComparator implements Comparator<Drafter> {

	public int compare(Drafter p1, Drafter p2) {
		return Integer.valueOf(p1.getDraftOrderNumber()).compareTo(Integer.valueOf(p2.getDraftOrderNumber()));
	}

}
