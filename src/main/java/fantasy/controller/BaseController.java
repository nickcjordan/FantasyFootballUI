package fantasy.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Controller;

import fantasy.Log;
import fantasy.enums.DraftType;
import fantasy.io.DataFileReader;
import fantasy.logic.LogicHandler;
import fantasy.model.Draft;
import fantasy.model.DraftPick;
import fantasy.model.Drafter;
import fantasy.model.Player;
import fantasy.model.RoundSpecificStrategy;

@Controller
public class BaseController {
	
	public static int pickNumber = 1;
	public static int roundNum = 0;
	public static int NUMBER_OF_ROUNDS;
	public static Properties properties = new Properties();
	
	public static int draftOrderIndex = 0;
	public static Drafter currentDrafter;
	
	public static boolean mockDraftMode;
	
	public static Draft draft;
	public static DraftType draftType;
	public static List<DraftPick> draftPicks;
	public static String errorMessage = null;
	
	public static Map<String, RoundSpecificStrategy> strategyByRound;
	public static List<Player> currentRoundHandcuffs;
	
	
	static {
		Properties prop = new Properties();
		try {
    		prop.load(new FileInputStream(new File("src/main/resources/data/logic.properties")));
    		strategyByRound = getStrategyFromFile();
    	} catch (IOException ex) {
    		Log.err("Error pulling data from file");
        }
	}
	
	protected static Properties loadProperties(String PROPERTIES_FILE_NAME) {
		Properties prop = new Properties();
		try {
    		prop.load(new FileInputStream(new File(PROPERTIES_FILE_NAME)));
    	} catch (IOException ex) {
    		Log.err("Could not load " + PROPERTIES_FILE_NAME);
        }
		return prop;
	}
	
	private static Map<String, RoundSpecificStrategy> getStrategyFromFile() throws FileNotFoundException {
		Map<String, RoundSpecificStrategy> map = new HashMap<String, RoundSpecificStrategy>();
		for (List<String> split : new DataFileReader().getSplitLinesFromFile("src/main/resources/data/draftStrategyByRound.csv")) {
			RoundSpecificStrategy strategy = buildStrategy(split);
			map.put(strategy.getRound(), strategy);
		}
		return map;
	}

	private static RoundSpecificStrategy buildStrategy(List<String> split) {
		RoundSpecificStrategy strategy = new RoundSpecificStrategy();
		strategy.setRound(split.get(0));
		strategy.setStrategyText(split.get(1));
		strategy.setTargetPositions(split.get(2));
		strategy.setTargetPlayers(buildListOfTargetPlayers(split));
		return strategy;
	}

	private static List<String> buildListOfTargetPlayers(List<String> split) {
		List<String> players = new ArrayList<String>();
		for (int i = 3; i < split.size(); i++) {
			players.add(split.get(i));
		}
		return players;
	}

	public static double getPercent() {
		double percent = 0;
		if (pickNumber == 1) {
			return 0;
		}
		percent = (((pickNumber - 1) / 192.0) * 100);
		percent = (double)Math.round(percent * 100d) / 100d;
		//Log.deb("Draft completion = " + percent);
		return percent;
	}
	
	List<Player> getSuggs(Drafter drafter) {
		return new LogicHandler(drafter).getMySuggestions();
	}
    
	public static int get(String property) {
		try {
			return Integer.parseInt(properties.getProperty(property));
		} catch (Exception e) {
			throw e;
		}
	}
	
	public int getPickNumber() {
		return pickNumber;
	}

	public static int getCurrentRoundNumber() {
		return roundNum;
	}
	
	public void moveToNextDrafter() {
		BaseController.currentDrafter = draft.getDrafters().get(++draftOrderIndex);
	}

	public Drafter getCurrentDrafter() {
		return currentDrafter;
	}

	public boolean isMockDraftMode() {
		return mockDraftMode;
	}

	public static int getRoundNum() {
		return roundNum;
	}

	public static int getNumberOfRounds() {
		return NUMBER_OF_ROUNDS;
	}

	public static Properties getProperties() {
		return properties;
	}

	public int getDraftOrderIndex() {
		return draftOrderIndex;
	}

	public Draft getDraft() {
		return draft;
	}

	public DraftType getDraftType() {
		return draftType;
	}
	
	public boolean inMockDraftMode(Drafter drafter) {
		return (mockDraftMode && !drafter.getName().equals("Nick J"));
	}
	
}