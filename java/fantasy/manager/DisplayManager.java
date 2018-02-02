package fantasy.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.sun.xml.internal.ws.util.StringUtils;

import fantasy.comparator.DraftOrderComparator;
import fantasy.comparator.PlayerRankComparator;
import fantasy.enums.Position;
import fantasy.model.Draft;
import fantasy.model.Drafter;
import fantasy.model.NFL;
import fantasy.model.Player;
import fantasy.model.Team;
import fantasy.results.DraftResultStatistics;
import fantasy.results.ResultsProcessor;

public class DisplayManager {
	
	private Draft draft;

	public DisplayManager(Draft draft) {
		this.draft = draft;
	}

	public DisplayManager() {	}

	public void showAllAvailable(){
		printTitle("ALL AVAILABLE");
		printHeader();
		for (Player player : NFL.getAllAvailablePlayersList()){
			System.out.println(player.fullStats());
		}
		printDivider();
	}
	
	public void showAllPicked(){
		printTitle("ALL PICKED");
		if (NFL.getAllPickedPlayersList().size() > 0) {
			printHeader();
			for (Player player : NFL.getAllPickedPlayersList()){
				System.out.println(player.fullStats());
			}
			printDivider();
		} else {
			System.out.println("\tNo players have been drafted...");
		}
	}
	
	public void showAvailableByPosition(Position pos){
		printTitle(pos.getName() + "S");
		printHeader();
		for (Player player : NFL.getAvailablePlayersByPositionAsList(pos)){
			System.out.println(player.fullStats());
		}
		printDivider();
	}
	
	public void showTopPicks(int number){
		List<Player> players = NFL.getAllAvailablePlayersList();
		printTitle("TOP RANKED AVAILABLE");
		printHeader();
		for (int i = 0; i < Math.min(number, players.size()); i++){
			System.out.println(players.get(i).fullStats());
			printDivider();
		}
	}
	
	public void printTitle(String header){
		System.out.println(getTitle(header));
	}
	
	public String getTitle(String header) {
		StringBuilder builder = new StringBuilder("\n    ****************************************");
		appendSpaces(builder, header);
		builder.append("\n    ****************************************");
		return builder.toString();
	}

	public void printHeader(){
		System.out.println(getHeader());
	}
	
	private void appendSpaces(StringBuilder builder, String header) {
		String line = "\n    *";
		for (int i = 0; i < getSpaceNumber(header); i++){
			line += " ";
		}
		line += header;
		builder.append(String.format("%-44s", line));
		builder.append("*");
	}

	private int getSpaceNumber(String header){
		return (40-header.length())/2;
	}
	
	public void printDivider() {
		System.out.println(getDivider());
	}
	
	public String getDivider() {
		return "+====+-------------------------+-----+---------+------+-----+";
	}

	public String getHeader() {
		return getDivider() + "\n" + String.format("|%-3s| %-24s| %-3s | %-7s | %-4s | %-3s |\n", " ID ",  "          Name", "Pos", "Pos/Rnk", "Team", "Bye") + getDivider();
	}
	
	public String promptForResponse(String header) {
		Scanner in = new Scanner(System.in);
		System.out.print(header);
		String line = in.nextLine();
		System.out.println();
		return line;
	}
	
	public void printDraftBoard(int roundNum) {
		List<Drafter> drafters = new ArrayList<Drafter>(draft.getDrafters());
		Collections.sort(drafters, new DraftOrderComparator());
		printDraftBoardHeader(drafters);
		for (int i = 0; i < roundNum; i++) {
			printDraftBoardIndex(i);
			for (Drafter drafter : drafters) {
				if (drafter.getDraftedTeam().getAllInDraftedOrder().size() > i) {
					System.out.printf(" %-28s |", drafter.getDraftedTeam().getAllInDraftedOrder().get(i).getShort());
				} else {
					System.out.printf(" %-28s |", "");
				}
			}
			printLongDivider();
		}
	}

	private void printDraftBoardIndex(int index) {
		System.out.printf("| %2d |", index + 1);
	}

	private void printDraftBoardHeader(List<Drafter> drafters) {
		printLongDivider();
		System.out.print("||||||");
		for (Drafter drafter : drafters) {
			System.out.printf("          %-19s |", drafter.getName());
		}
		printLongDivider();
	}
	
	private void printResultsHeader(List<Drafter> drafters) {
		printResultsDivider();
		System.out.print("||||||||||||||||||||");
		for (Drafter drafter : drafters) {
			System.out.printf("   %-7s |", drafter.getName());
		}
		printResultsDivider();
	}
	
	
	private void printResultsDivider() {
		int num = draft.getDrafters().size();
		System.out.print("\n+------------------+");
		for (int i = 0; i < num; i++) {
			System.out.print("-----------+");
		}
		System.out.println();
	}

	private void printLongDivider() {
		int num = draft.getDrafters().size();
		System.out.print("\n+----+");
		for (int i = 0; i < num; i++) {
			System.out.print("------------------------------+");
		}
		System.out.println();
	}

	public void printEnding(int num) {
		printTitle("DRAFT RESULTS");
		printDraftBoard(num);
		ResultsProcessor.processResults(draft);
		printDraftResultStatistics();
//		for (Drafter drafter : draft.getDrafters()) {
//			drafter.getDraftResultStats().printStats();
//		}
	}

	private void printDraftResultStatistics() {
		List<Drafter> drafters = new ArrayList<Drafter>(draft.getDrafters());
		Collections.sort(drafters, new DraftOrderComparator());
		printResultsHeader(drafters);
		
		printHead("Avg Overall Rank");
		for (Drafter drafter : drafters) {	printBox(String.valueOf(drafter.getDraftResultStats().getAverageOverallRank())); }
		printResultsDivider();
		
		printHead("Avg QB Rank");
		for (Drafter drafter : drafters) {	printBox(String.valueOf(drafter.getDraftResultStats().getQbRank())); }
		printResultsDivider();
		
		printHead("Avg RB Rank");
		for (Drafter drafter : drafters) {	printBox(String.valueOf(drafter.getDraftResultStats().getRbRank())); }
		printResultsDivider();
		
		printHead("Avg WR Rank");
		for (Drafter drafter : drafters) {	printBox(String.valueOf(drafter.getDraftResultStats().getWrRank())); }
		printResultsDivider();
		
		printHead("Avg TE Rank");
		for (Drafter drafter : drafters) {	printBox(String.valueOf(drafter.getDraftResultStats().getTeRank())); }
		printResultsDivider();
		
		printHead("Avg K Rank");
		for (Drafter drafter : drafters) {	printBox(String.valueOf(drafter.getDraftResultStats().getkRank())); }
		printResultsDivider();
		
		printHead("Avg DST Rank");
		for (Drafter drafter : drafters) {	printBox(String.valueOf(drafter.getDraftResultStats().getDstRank())); }
		printResultsDivider();

	}

	private void printBox(String stat) {
		System.out.printf("    %3s    |", stat);
	}
	
	private void printHead(String head) {
		System.out.printf("| %-16s |", head);
	}
	
}
	