package fantasy.menu;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import fantasy.enums.Position;
import fantasy.exception.FalifaException;
import fantasy.manager.DisplayManager;
import fantasy.manager.DraftManager;
import fantasy.model.Draft;
import fantasy.model.Drafter;
import fantasy.model.NFL;
import fantasy.model.Player;
import fantasy.model.Team;
import fantasy.utility.Print;

public class MainMenu {
	
	DisplayManager display;
	MenuPrompter prompt;
	private Draft draft;

	public MainMenu(Draft draft, DisplayManager display) {
		this.draft = draft;
		this.display = display;
		prompt = new MenuPrompter(display, draft);
	}

	public void goToMainMenu(int roundNum) {
		do { printMenu();
		} while (stillInMainMenu(roundNum));
	}
	
	private boolean stillInMainMenu(int roundNum) {
		switch(prompt.promptForMenuOption(7)) {
			case 0 : 	return false;
			case 1 : 	showAvailableByPositionSubmenu(); break;
			case 2 :	displayTopAvailable(); break;
			case 3 :	displayNflTeam(); break;
			case 4 : 	promptForPlayerNotes(); break;
			case 5 :	displayDrafterTeam(); break;
			case 6 :	display.showAllPicked(); break;
			case 7 :	display.printDraftBoard(roundNum); break;
			default : return false;
		}
		return true;
	}

	private void printMenu() {
		display.printTitle("MAIN MENU");
		System.out.println(getPadded("    |    Hit <Enter> to Draft Player") + "|");
		Print.dashed();
		System.out.println(getPadded("    | 1)  Display Available By Position ->") + "|");
		System.out.println(getPadded("    | 2)  Display Top Available...") + "|");
		System.out.println(getPadded("    | 3)  Display an NFL Team...") + "|");
		System.out.println(getPadded("    | 4)  Display a Player's Notes...") + "|");
		System.out.println(getPadded("    | 5)  Display a Drafter's Team...") + "|");
		System.out.println(getPadded("    | 6)  Display All Drafted Players") + "|");
		System.out.println(getPadded("    | 7)  Display Draft Board") + "|");
		Print.dashed();
	}
	
	private void showAvailableByPositionSubmenu() {
		printSubmenu();
		switch(prompt.promptForMenuOption(6)) {
			case 0 : 	break;
			case 1 : 	display.showAvailableByPosition(Position.QUARTERBACK); break;
			case 2 :	display.showAvailableByPosition(Position.RUNNINGBACK); break;
			case 3 :	display.showAvailableByPosition(Position.WIDERECEIVER); break;
			case 4 :	display.showAvailableByPosition(Position.TIGHTEND); break;
			case 5 :	display.showAvailableByPosition(Position.KICKER); break;
			case 6 :	display.showAvailableByPosition(Position.DEFENSE); break;
			default : break;
		}
	}
	
	private void printSubmenu() {
		Print.dashed2();
		System.out.println("\t" + getPadded("    | Hit <Enter> to Return to Main Menu") + "|");
		Print.dashed2();
		System.out.println("\t" + getPadded("    | 1)  Display Available Quarterbacks") + "|");
		System.out.println("\t" + getPadded("    | 2)  Display Available Runningbacks") + "|");
		System.out.println("\t" + getPadded("    | 3)  Display Available Wide Receivers") + "|");
		System.out.println("\t" + getPadded("    | 4)  Display Available Tight Ends") + "|");
		System.out.println("\t" + getPadded("    | 5)  Display Available Kickers") + "|");
		System.out.println("\t" + getPadded("    | 6)  Display Available Defenses") + "|");	
		Print.dashed2();
	}
	
	private void displayTopAvailable() {
		display.showTopPicks(prompt.promptForNumberOfPlayers());
	}

	
	private void displayDrafterTeam() {
		display.printTitle("DRAFTERS");
		for (Drafter drafter : draft.getDrafters()) {
			System.out.println(getPadded("    |  " + drafter.getDraftOrderNumber() + ") " + drafter.getName()));
		}
		Print.dashed();
		System.out.println(prompt.promptForDrafterTeam());
	}

	private void displayNflTeam() {
		display.printTitle("NFL TEAMS");
		for (Team team : NFL.getTeamList()) {
			printNflTeamLine(team);
		}
		Print.dashed();
		System.out.println(prompt.promptForNflTeam());
	}
	
	public void promptForPlayerNotes() {
		printPlayerNotes(prompt.promptForPlayer());
	}
	
	private void printPlayerNotes(Player player) {
		display.printTitle(player.getPlayerName() + " NOTES");
		if (player.getNotes() != null) {
			System.out.println(player.getNotes() + "\n");
		} else {
			System.out.println("\nPlayer has no notes.");
		}
	}

	private void printNflTeamLine(Team team) {
		String formattedId = String.format("%-4s", team.getId() + ") ");
		String formattedName = " " + String.format("%-3s", team.getName()) + " - ";
		String line = "    | " + formattedId + formattedName + team.getMascot();
		System.out.println(getPadded(line) + "|");
	}
	
	
//	private void printMenu() {
//		display.printTitle("MAIN MENU");
//		System.out.println(getPadded("    |     Hit <Enter> to Draft Player") + "|");
//		System.out.println(getPadded("    |  1)  Display Available Quarterbacks") + "|");
//		System.out.println(getPadded("    |  2)  Display Available Runningbacks") + "|");
//		System.out.println(getPadded("    |  3)  Display Available Wide Receivers") + "|");
//		System.out.println(getPadded("    |  4)  Display Available Tight Ends") + "|");
//		System.out.println(getPadded("    |  5)  Display Available Kickers") + "|");
//		System.out.println(getPadded("    |  6)  Display Available Defenses") + "|");
//		System.out.println(getPadded("    |  7)  Display NFL Team...") + "|");
//		System.out.println(getPadded("    |  8)  Display Drafter's Team...") + "|");
//		System.out.println(getPadded("    |  9)  Display All Drafted Players") + "|");
//		System.out.println(getPadded("    | 10) Display a Player's Notes...") + "|");
//		System.out.println(getPadded("    | 11) Display Draft Board") + "|");
//
//		Print.dashed();
//	}
	
	private String getPadded(String line) {
		return String.format("%-43s", line);
	}
}



