package fantasy.enums;

public enum Position {

	QUARTERBACK("QB", "QUARTERBACK"),
	RUNNINGBACK("RB", "RUNNINGBACK"),
	WIDERECEIVER("WR", "WIDE RECEIVER"),
	TIGHTEND("TE", "TIGHT END"),
	KICKER("K", "KICKER"),
	DEFENSE("DST", "DEFENSE");
	
	private String abbrev;
	private String name;

	
	Position(String abbrev, String name){
		this.abbrev = abbrev;
		this.name = name;
	}
	
	public String getAbbrev(){
		return abbrev;
	}
	
	public String getName(){
		return name;
	}

}
