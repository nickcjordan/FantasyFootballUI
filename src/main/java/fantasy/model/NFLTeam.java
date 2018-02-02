package fantasy.model;

public class NFLTeam extends Team {

	public static int ind = 1;
	String abbrev;
	String city;
	
	public NFLTeam(String city, String name, String abbrev) {
		super(name, city, ind++, abbrev);
		this.abbrev = abbrev;
		this.city = city;
		this.fullName = city + " " + String.format("%-15s", name);
	}

	public static int getInd() {
		return ind;
	}

	public static void setInd(int ind) {
		NFLTeam.ind = ind;
	}

	public String getAbbrev() {
		return abbrev;
	}

	public void setAbbrev(String abbrev) {
		this.abbrev = abbrev;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
}
