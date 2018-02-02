package fantasy.results;

import java.util.List;

import fantasy.enums.Position;
import fantasy.exception.FalifaException;
import fantasy.manager.DisplayManager;
import fantasy.manager.DraftManager;
import fantasy.model.Drafter;
import fantasy.model.NFL;
import fantasy.model.Player;
import fantasy.utility.Print;

public class DraftResultStatistics {

	private Drafter drafter;

	private int averageOverallRank;
	
	private int qbRank;
	private int rbRank;
	private int wrRank;
	private int teRank;
	private int kRank;
	private int dstRank;
	
	private boolean draftedHandcuff;

	public DraftResultStatistics(Drafter drafter) {
		super();
		this.drafter = drafter;
		this.averageOverallRank = findAverageOverallRank(drafter.getDraftedTeam().getPositionLists());
		this.draftedHandcuff = findIfDraftedHandcuff();
	}

	private boolean findIfDraftedHandcuff() {
		List<Player> rbs = drafter.getDraftedTeam().getPlayersByPosition(Position.RUNNINGBACK);
		try { 
			Player handcuff = NFL.getPlayer(rbs.get(0).getHandcuff());
			return ((handcuff != null) && (rbs.contains(handcuff)));
		} catch (FalifaException e) {return false;}
	}

	private int findAverageOverallRank(List<List<Player>> positions) {
		int sum = 0;
		for (List<Player> position : positions) {
			if (position.isEmpty()) continue;
			int positionAverage = findPositionAv(position);
			sum += positionAverage;
			setPosRank(positionAverage, position.get(0).getPosition());
		}
		return (sum/positions.size());
	}

	private int findPositionAv(List<Player> position) {
		int posSum = 0;
		for (Player player : position) {
			posSum += Integer.parseInt(player.getRank());
		}
		if (position.size() > 0) {
			return (posSum/position.size());
		} else {
			return 0;
		}
	}

	private void setPosRank(int posAv, Position pos) {
		switch (pos) {
		case QUARTERBACK 	: this.qbRank = posAv; break;
		case RUNNINGBACK 	: this.rbRank = posAv; break;
		case WIDERECEIVER 	: this.wrRank = posAv; break;
		case TIGHTEND			 	: this.teRank = posAv; break;
		case KICKER				 	: this.kRank = posAv; break;
		case DEFENSE			 	: this.dstRank = posAv; break;
		default: break;
		}
	}

	public Drafter getDrafter() {
		return drafter;
	}

	public int getAverageOverallRank() {
		return averageOverallRank;
	}

	public int getQbRank() {
		return qbRank;
	}

	public int getRbRank() {
		return rbRank;
	}

	public int getWrRank() {
		return wrRank;
	}

	public int getTeRank() {
		return teRank;
	}

	public int getkRank() {
		return kRank;
	}

	public int getDstRank() {
		return dstRank;
	}

	public boolean draftedHandcuff() {
		return draftedHandcuff;
	}

	public void printStats() {
		new DisplayManager().printTitle(drafter.getName() + "'s Draft Results");
		
		printStatLine("Average Overall Rank", getAverageOverallRank());
		printStatLine("QB Rank", getQbRank());
		printStatLine("RB Rank", getRbRank());
		printStatLine("WR Rank", getWrRank());
		printStatLine("TE Rank", getTeRank());
		printStatLine("K Rank", getkRank());
		printStatLine("DST Rank", getDstRank());
		
		System.out.println();
	}

	private void printStatLine(String text, int rank) {
		System.out.printf("    |   %-24s : %3s     |\n", text, rank);
		Print.dashed();
	}
	
}
