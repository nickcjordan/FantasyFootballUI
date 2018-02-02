package fantasy.menu;

import fantasy.manager.DisplayManager;
import fantasy.manager.DraftManager;
import fantasy.model.Draft;
import fantasy.model.NFL;
import fantasy.model.Player;
import fantasy.model.Team;
import fantasy.utility.Print;

public class MenuPrompter {

	DisplayManager display;
	private Draft draft;
	
	public MenuPrompter(DisplayManager display, Draft draft) {
		this.display = display;
		this.draft = draft;
	}

	public Player promptToPickPlayer() {
		Player player = promptForPlayer();
		if (player.isAvailable()){
			return player;
		} else {
			System.out.println("Sorry, " + player.getPlayerName() + " is unavailable. Try again...");
			return promptToPickPlayer();
		}
	}
	
	public Player promptForPlayer() {
		String line = display.promptForResponse("\n\tEnter Player Name or ID: ");
		try {
			return (line.length() > 3) ? NFL.getPlayer(line) : NFL.getPlayer(Integer.parseInt(line));
		} catch (Exception e) {
			Print.error(e.getMessage());
		}
		System.out.println("Sorry, that player is unavailable. Try again...");
		return promptToPickPlayer();
	}

	Team promptForDrafterTeam() {
		int option = Integer.parseInt(display.promptForResponse("\n\tEnter Team ID: "));
		if (option > 12 || option < 1) {
			System.out.println("Sorry, \"" + option + "\" is not a valid option. Try again...");
			return promptForDrafterTeam();
		} else {
			return draft.getDrafters().get(option-1).getDraftedTeam();
		}
	}

	Team promptForNflTeam() {
		int id = Integer.parseInt(display.promptForResponse("\n\tEnter Team ID: "));
		if (id > 33 || id < 1) {
			System.out.println("Sorry, \"" + id + "\" is not a valid option. Try again...");
			return promptForNflTeam();
		} else {
			return NFL.getTeam(id);
		}
	}

	int promptForMenuOption(int max) {
		String response = display.promptForResponse("\n\tEnter an option: ");
		if (response.isEmpty()) {
			return 0;
		}
		try {
			int option = Integer.parseInt(response);
			if (option >= 0 && option <= max) {
				return option;
			} 
		} catch (Exception e) {}
			System.out.println("Sorry, that is not a valid option. Try again...");
			return promptForMenuOption(max);
	}

	public int promptForNumberOfPlayers() {
		String response = display.promptForResponse("\n\tEnter number of players to show: ");
		try {
			return (response.isEmpty()) ? 0 : Integer.parseInt(response);
		} catch (Exception e) {}
			System.out.println("Please enter a valid option. Try again...");
			return promptForNumberOfPlayers();
	}
}
