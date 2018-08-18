package fantasy.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fantasy.Log;
import fantasy.builder.PlayerBuilder;
import fantasy.builder.TeamBuilder;
import fantasy.comparator.DraftSelectionOrderComparator;
import fantasy.comparator.UserDraftOrderComparator;
import fantasy.enums.Position;
import fantasy.exception.FalifaException;
import fantasy.logic.LogicHandler;
import fantasy.model.DraftPick;
import fantasy.model.Drafter;
import fantasy.model.NFL;
import fantasy.model.Player;
import fantasy.results.ResultsProcessor;

@Controller
public class DraftController extends BaseController {

	@RequestMapping(value = "/pickPlayer")
    public String doPickForDrafter(@RequestParam(defaultValue="") String playerId, Model model) throws FalifaException {
		errorMessage = null;
		Player player = NFL.getPlayer(resolvePlayerId(playerId));
		Log.deb("doPickForDrafter :: picking player " + player.getPlayerName());
		if (!player.isAvailable()) {
			Log.err("doPickForDrafter : " + currentDrafter.getName() + " :: player " + player.getPlayerName() + " is not available");
			addAttributes(model);
			errorMessage = player.getPlayerName() + " is not available";
			return "pages/dashboardPage";
		}
		doBaseDraft(model, player);
    	return draftHasCompleted() ? prepareResults(model) : mockDraftMode ? mockDraft(model) : "pages/dashboardPage";
    }

    public String mockDraft(Model model) throws FalifaException {
    	if (currentDrafter.getName().equals("Nick J")) {
    		return "pages/dashboardPage";
		}
		Player player = new LogicHandler(currentDrafter).getAiPick();
		doBaseDraft(model, player);
    	return draftHasCompleted() ? prepareResults(model) : mockDraft(model);
    }
    
    public String autoDraft(Model model) throws FalifaException {
    	LogicHandler logic = new LogicHandler(currentDrafter);
    	Player player = (currentDrafter.getName().equals("Nick J")) ? logic.getMySuggestions().get(0) : logic.getAiPick();
    	doBaseDraft(model, player);
    	return draftHasCompleted() ? prepareResults(model) : autoDraft(model);
    }

    private String prepareResults(Model model) {
    	ResultsProcessor.processResults(draft);
    	ArrayList<Drafter> orderedDrafters = new ArrayList<Drafter>(draft.getDrafters());
    	Collections.sort(orderedDrafters, new UserDraftOrderComparator());
    	model.addAttribute("drafterResults", orderedDrafters);
    	return "pages/resultsPage";
    }
    
	private void doBaseDraft(Model model, Player player) {
		Log.info("Player picked = " + player.getPlayerName());
		PlayerBuilder.setHandcuffsForSelectedPlayer(player);
		draftPicks.add(draftPlayer(currentDrafter, player));
		Collections.sort(draftPicks, new DraftSelectionOrderComparator());
		checkIfEndOfRound();
		moveToNextDrafter();
		setCorrectHandcuffsForCurrentDrafter();
        addAttributes(model);
	}
    
	private void setCorrectHandcuffsForCurrentDrafter() {
		Drafter current = BaseController.currentDrafter;
		List<Player> players = new ArrayList<Player>();
		for (Player drafted : current.getDraftedTeam().getAllInDraftedOrder()) {
			for (Player handcuff : drafted.getBackups()) {
				players.add(handcuff);
			}
		}
		BaseController.currentRoundHandcuffs = players;
	}

	private int resolvePlayerId(String playerId) {
		LogicHandler logic = new LogicHandler(currentDrafter);
		int blankId = currentDrafter.getName().equals("Nick J") ? logic.getMySuggestions().get(0).getId() : logic.getAiPick().getId();
		return (playerId.isEmpty()) ? blankId:  Integer.parseInt(playerId);
	}

	private void addAttributes(Model model) {
		model.addAttribute("progressPercent", getPercent());
		model.addAttribute("draft", draft);
        model.addAttribute("currentDrafter", currentDrafter);
        model.addAttribute("currentRoundHandcuffs", BaseController.currentRoundHandcuffs);
        
        model.addAttribute("playersSortedBySuggestions", getSuggs(currentDrafter));
		model.addAttribute("playersSortedByAdp", NFL.getAllAvailablePlayersByADP());
		model.addAttribute("playersSortedByRank", NFL.getAllAvailablePlayersByRank());
        
        model.addAttribute("roundNumber", ((roundNum < NUMBER_OF_ROUNDS) ? roundNum : NUMBER_OF_ROUNDS));
        model.addAttribute("pickNumber", pickNumber);
        model.addAttribute("currentDraftedTeam", currentDrafter.getDraftedTeam());
        model.addAttribute("draftPicks", draftPicks);
      	model.addAttribute("drafters", getCorrectlyOrderedDrafterList());
      	model.addAttribute("strategy", strategyByRound.get(String.valueOf(roundNum)));
      	model.addAttribute("draftersPickNumberList", new LogicHandler(currentDrafter).getDraftPickIndexList());
        for (Position position : Position.values()) {
        	model.addAttribute(position.getAbbrev() + "List", NFL.getAvailablePlayersByPositionAsList(position));
        }
	}

	public static List<Drafter> getCorrectlyOrderedDrafterList() {
		List<Drafter> drafterList = new ArrayList<Drafter>(draft.getDrafters());
      	if (!draft.getOrderedNames()[0].equals(drafterList.get(0).getName())) {
      		Collections.reverse(drafterList);
      	}
		return drafterList;
	}
    
	public DraftPick draftPlayer(Drafter drafter, Player draftedPlayer) {
		draftedPlayer.setRoundDrafted(roundNum);
		TeamBuilder.addPlayerToDraftersTeam(draftedPlayer, drafter.getDraftedTeam());
		draftedPlayer.markUnavailable();
		for (Player handcuff : draftedPlayer.getBackups()) {
			handcuff.setAsHandcuff();
		}
		return new DraftPick(pickNumber, roundNum, drafter, draftedPlayer);
	}
    
	private boolean draftHasCompleted() {
		return (pickNumber > (NUMBER_OF_ROUNDS * draft.getOrderedNames().length));
	}

	private boolean checkIfEndOfRound() {
		pickNumber++;
		if (draftOrderIndex == draft.getDrafters().size() - 1) {
    		draft.reverseOrder();
    		draftOrderIndex = -1;
    		if (roundNum != NUMBER_OF_ROUNDS) {
    			roundNum++;
    		}
    		return true;
		} else {
			return false;
		}
	}

}
