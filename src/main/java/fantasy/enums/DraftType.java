package fantasy.enums;

import fantasy.Log;
import fantasy.controller.BaseController;

public enum DraftType {
	
	REAL_DRAFT("real"),
	AUTO_DRAFT("auto"),
	MOCK_DRAFT("mock");

	private String[] order;
	private String type;
	
	DraftType(String type) {
		this.type = type;
		this.order = getDraftOrder(type);
	}

	private String[] getDraftOrder(String t) {
		switch(t) {
			case "real"		: return realOrder();
			case "auto"	: return getAuto();
			case "mock"	: return getMock();
		}
		Log.err("did not find order: " + t);
		return null;
	}

	private String[] getMock() {
		return new String[]{
				"Chris T",
				"Chris R",
				"Will",
				"Matt",
				"Nick J",
				"Ryan",
				"Josh",
				"Mason",
				"Nick W",
				"Austin",
				"Dan",
				"Scott"
		};
	}
		
	private String[] getAuto() {
		return new String[]{
				"Chris T",
				"Chris R",
				"Will",
				"Matt",
				"Nick J",
				"Ryan",
				"Josh",
				"Mason",
				"Nick W",
				"Austin",
				"Dan",
				"Scott"
		};
	}
	
	private String[] realOrder() {
		return new String[]{
				"Brittany",
				"Diane",
				"Mom",
				"Heather",
				"Dianes Dad",
				"Dad",
				"Nick J", 
				"Michael",
				"Chris",
				"Claire"
		};

//	private String[] realOrder() {
//		return new String[]{
//				"Mason",
//				"Scott",
//				"Nick J",
//				"Austin",
//				"Nick W",
//				"Chris R",
//				"Will",
//				"Dan",
//				"Matt",
//				"Josh",
//				"Chris T",
//				"Ryan"
//		};
	}

	public String[] getOrder() {
		return order;
	}

	public void setOrder(String[] order) {
		this.order = order;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public static DraftType getDraftType(String type) {
		for (DraftType dt : DraftType.values()) {
			if (dt.getType().equals(type)) {
				if (type.equals("mock") || type.equals("auto")) {
					BaseController.mockDraftMode = true;
				}
				return dt;
			}
		}
		Log.err("Could not find DraftType of \"" + type + "\".");
		return null;
	}
	
}
