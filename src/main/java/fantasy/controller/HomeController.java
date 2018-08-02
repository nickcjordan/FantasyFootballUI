package fantasy.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fantasy.Log;
import fantasy.enums.DraftType;
import fantasy.enums.Position;
import fantasy.exception.FalifaException;
import fantasy.io.StatsCleaner;
import fantasy.model.Draft;
import fantasy.model.NFL;

@Controller
public class HomeController extends BaseController {

    @RequestMapping(value = "/")
    public String init(Model model) {
    	properties = loadProperties("logic.properties");
    	errorMessage = null;
        NFL.resetPlayers();
    	NUMBER_OF_ROUNDS = get("numberOfRounds");
    	draftPicks = new ArrayList<>();
    	roundNum = 1;
    	pickNumber = 1;
    	draftOrderIndex = 0;
    	StatsCleaner.cleanupTags();
    	StatsCleaner.cleanupNickNotes();
    	
    	System.out.println("\n\n<^>     Ready to Draft     <^>\n\n");
        return "home";
    }
	
	// to hit this with "type" --> "http://localhost:8080/init?appRunType=type"
    @RequestMapping(value = "/start")
    public String start(@RequestParam(required=false, defaultValue="real") String appRunType, Model model) throws FalifaException {
    	mockDraftMode = false;
    	BaseController.draftType = DraftType.getDraftType(appRunType);
    	Log.deb(appRunType + " : " + draftType);
    	BaseController.draft = new Draft(draftType.getOrder());
        BaseController.currentDrafter = draft.getDrafters().get(0);
        
        model.addAttribute("progressPercent", getPercent());
        model.addAttribute("draftType", draftType);
    	model.addAttribute("draft", draft);
		model.addAttribute("currentDrafter", currentDrafter);
		model.addAttribute("suggestions", getSuggs(currentDrafter));
		model.addAttribute("roundNumber", roundNum);
		model.addAttribute("pickNumber", pickNumber);
		model.addAttribute("draftPicks", draftPicks);
		model.addAttribute("playerList", NFL.getAllAvailablePlayersByADPList());
		model.addAttribute("currentDraftedTeam", currentDrafter.getDraftedTeam());
      	model.addAttribute("strategy", strategyByRound.get(String.valueOf(roundNum)));
		for (Position position : Position.values()) {
			model.addAttribute("drafted" + position.getAbbrev(), currentDrafter.getDraftedTeam().getPlayersByPosition(position)); 
			model.addAttribute(position.getAbbrev() + "List", NFL.getAvailablePlayersByPositionAsList(position));
		}
        DraftController c = new DraftController();
        return (draftType.equals(DraftType.AUTO_DRAFT)) ? c.autoDraft(model) : (draftType.equals(DraftType.MOCK_DRAFT ))  ? c.mockDraft(model) : "dashboardPage";
    }

}
