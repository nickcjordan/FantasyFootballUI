package fantasy.comparator;

import java.util.Comparator;

import fantasy.model.Team;

public class TeamIdComparator implements Comparator<Team>{
	
	public int compare(Team t1, Team t2){
		return ((Integer) t1.getId()).compareTo((Integer)t2.getId());
	}
}
