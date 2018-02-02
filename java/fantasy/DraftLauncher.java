package fantasy;

import fantasy.manager.DraftManager;
import fantasy.model.Draft;

public class DraftLauncher {

	public static void main(String args[]){
		DraftManager manager = (args.length != 0) ? new DraftManager(new Draft(args), true) : new DraftManager();
		manager.startDraft();
	}
	
}
