package fantasy.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import fantasy.builder.NFLBuilder;
import fantasy.builder.TeamBuilder;
import fantasy.logic.LogicHandler;
import fantasy.menu.MainMenu;
import fantasy.menu.MenuPrompter;
import fantasy.model.Draft;
import fantasy.model.DraftOrder;
import fantasy.model.Drafter;
import fantasy.model.NFL;
import fantasy.model.Player;
import fantasy.utility.Print;

public class DraftManager {
	
	 int pickNumber = 1;
	 static int roundNum = 0;
	 static int NUMBER_OF_ROUNDS;
	 final String PROPERTIES_FILE_NAME = "logic.properties";
	
	 boolean mockDraftMode;
	
	static Properties properties;
	Draft draft;
	DisplayManager display;
	MainMenu menu;
	MenuPrompter prompt;
	
	public DraftManager() {
		draft = new Draft(DraftOrder.order);
		display = new DisplayManager(draft);
		menu = new MainMenu(draft, display);
		prompt = new MenuPrompter(display, draft);
		properties = loadProperties();
		NUMBER_OF_ROUNDS = get("numberOfRounds");
	}
	
	public DraftManager(Draft draft, boolean mock) {
		this.draft = draft;
		display = new DisplayManager(draft);
		menu = new MainMenu(draft, display);
		prompt = new MenuPrompter(display, draft);
		properties = loadProperties();
		NUMBER_OF_ROUNDS = get("numberOfRounds");
		this.mockDraftMode = mock;
	}
	
	public void startDraft() {
		for (int i = 1; i <= NUMBER_OF_ROUNDS; i++){
			roundNum = i;
			startRound();
			draft.reverseOrder();
		}
		display.printEnding(NUMBER_OF_ROUNDS);
	}

	private void startRound() {
		for (Drafter drafter : draft.getDrafters()){
			Player player = resolvePlayerToDraft(drafter);
			player.setRoundDrafted(roundNum);
			draftPlayer(drafter, player);
			pickNumber++;
		}
	}

	private Player resolvePlayerToDraft(Drafter drafter) {
		LogicHandler logic = new LogicHandler(drafter, display);
		if (inMockDraftMode(drafter)) {
			return logic.getPlayerForMockDrafter();
		} else {
			System.out.println("\t\t~~ Pick Number " + pickNumber + " ~~");
			logic.provideLogic();
			menu.goToMainMenu(roundNum);
			return prompt.promptToPickPlayer();
		}
	}

	private boolean inMockDraftMode(Drafter drafter) {
		return (mockDraftMode && !drafter.getName().equals("Nick J"));
	}

	private void draftPlayer(Drafter drafter, Player draftedPlayer) {
		System.out.printf("\tFor pick number %-2d in round %-2d :: %-7s selects %-3s - %s\n", pickNumber, roundNum, drafter.getName(), draftedPlayer.getPos(), draftedPlayer.getPlayerName());
		TeamBuilder.addPlayerToTeam(draftedPlayer, drafter.getDraftedTeam());
		draftedPlayer.markUnavailable();
	}
	
	private Properties loadProperties() {
		Properties prop = new Properties();
		try {
    		prop.load(new FileInputStream(new File(PROPERTIES_FILE_NAME)));
    	} catch (IOException ex) {
    		Print.error("Could not load " + PROPERTIES_FILE_NAME);
        }
		return prop;
	}
	
	public static int get(String property) {
		return Integer.parseInt(properties.getProperty(property));
	}
	

	public Draft getDraft() {
		return draft;
	}

	public DisplayManager getDisplayManager() {
		return display;
	}

	public int getPickNumber() {
		return pickNumber;
	}

	public DisplayManager getDisplay() {
		return display;
	}

	public MainMenu getMenu() {
		return menu;
	}

	public MenuPrompter getPrompt() {
		return prompt;
	}
	
	public static int getNumberOfRounds() {
		return NUMBER_OF_ROUNDS;
	}
	
	public static int getCurrentRoundNumber() {
		return roundNum;
	}

	public void sleep(int seconds) {
		try { TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}

/*
	-add full list of players?
	-add ability to go back if you accidentaly do the wrong option
	-add *** highlight flag to player to mark that I like them
	-add confirmation for drafting player
	-add result of draft pick
	-ability to check number of players still available at given position


*/