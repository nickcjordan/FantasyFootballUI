package fantasy.model;

import static fantasy.enums.CSVFieldMapping.ADP;
import static fantasy.enums.CSVFieldMapping.AVG;
import static fantasy.enums.CSVFieldMapping.BEST;
import static fantasy.enums.CSVFieldMapping.BYE;
import static fantasy.enums.CSVFieldMapping.PLAYER_NAME;
import static fantasy.enums.CSVFieldMapping.POS;
import static fantasy.enums.CSVFieldMapping.RANK;
import static fantasy.enums.CSVFieldMapping.STD_DEV;
import static fantasy.enums.CSVFieldMapping.TEAM_NAME;
import static fantasy.enums.CSVFieldMapping.VERSUS_ADP;
import static fantasy.enums.CSVFieldMapping.WORST;

import java.util.ArrayList;
import java.util.List;

import fantasy.controller.BaseController;
import fantasy.enums.Position;

public class Player {

	String rank;				
	String playerName;	
	String teamName;	
	String pos;				
	String bye;				
	String best;				
	String worst;			
	String avg;				
	String std_dev;		
	String adp;				
	String versus;			
	String pos_rank;
	boolean available;
	int id;
	String handcuffs;
	Position position;
	String notes;
	int roundDrafted;
	private String tags;
	ArrayList<Player> backups;
	int tier;
	private String nickNotes;

	
	public Player(List<String> split) {
		this.rank = split.get(RANK.getIndex());
		this.teamName = extractTeamName(split);
		setPosAndPosRank(split.get(POS.getIndex()));
		this.playerName = getRealName(split);
		this.bye = split.get(BYE.getIndex());
		this.best = split.get(BEST.getIndex());
		this.worst = split.get(WORST.getIndex());
		this.avg = split.get(AVG.getIndex());
		this.std_dev = split.get(STD_DEV.getIndex());
		this.adp = getCorrectAdp(split.get(ADP.getIndex()));
		this.id = Integer.parseInt(this.rank);
		this.versus = split.get(VERSUS_ADP.getIndex());
		this.available = true;
		this.tags = "";
		this.backups = new ArrayList<Player>();
		this.tier = setTier();
	}
	
	private String getRealName(List<String> split) {
		return (split.get(POS.getIndex()).contains("D")) ? split.get(TEAM_NAME.getIndex()) : split.get(PLAYER_NAME.getIndex());
	}

	private String extractTeamName(List<String> split) {
		if (split.get(TEAM_NAME.getIndex()).isEmpty() || split.get(TEAM_NAME.getIndex()).equals("NA")) {
			String[] splitText = split.get(PLAYER_NAME.getIndex()).split(" ");
			split.set(TEAM_NAME.getIndex(), splitText[splitText.length - 1]);
		}
		return split.get(TEAM_NAME.getIndex());
	}

	private int setTier() {
		for (int i = 1; i <= 13; i++) {
			int index = Integer.parseInt(BaseController.getProperties().getProperty("tier" + i));
			if (Integer.parseInt(rank) < index) {
				return i;
			}
		}
		return 14;
	}

	public int getTier() {
		return tier;
	}

	public Player() {
		super();
		setEmptyFields();
	}

	private void setEmptyFields() {
		this.rank = "";
		this.teamName = "";
		this.pos = "";
		this.pos_rank = "";
		this.playerName = ""; 
		this.bye = "";
		this.best = "";
		this.worst = "";
		this.avg = "";
		this.std_dev = ""; 
		this.adp = "";
		this.versus = "";
		this.tags = "";
	}

	private String getCorrectAdp(String s) {
		if (s.contains(".0")) {
			s = s.replace(".0", "");
		}
		return (s.equals("NA") || s.trim().equals("")) ? this.rank : s;
	}

	public String checkForHandcuff() {
		return (handcuffs == null) ? " - " : handcuffs;
	}
	
	public String getShort() {
		return "(" + id + ") " + getNameAndTags(); 
	}
	
	public String getNameAndId() {
		return "(" + id + ") " + getPlayerName(); 
	}
	
	public void addAdditionalNotes(String addition) {
		this.notes = this.notes + "   [" + addition + "]";
	}
	
	public void addNicksNotes(String notes) {
		if (this.nickNotes == null) {
			this.nickNotes = notes;
		} else if (!this.getNickNotes().contains(notes)) {
			this.nickNotes = this.nickNotes + " :: " + notes;
		}
	}
	
	public String getNickNotes() {
		return nickNotes;
	}

	private void setPosAndPosRank(String position) {
		int ind = -1;
		for (int i = 0; i < position.length(); i++){
			if (Character.isDigit(position.charAt(i))){
				ind = i;
				break;
			}
		}
		setPos_rank(position.substring(ind));
		setPos(position.substring(0, ind));
		setPosition();
	}

	private void setPosition() {
		for (Position p : Position.values()) {
			if (pos.equals(p.getAbbrev())) {
				position = p;
			}
		}
	}

	public String getHandcuffs() {
		return handcuffs;
	}

	public void setHandcuffs(String handcuffs) {
		this.handcuffs = handcuffs;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return playerName;
	}
	
	public String getHandcuff() {
		return handcuffs;
	}

	public void addHandcuff(Player cuff) {
		backups.add(cuff);
		if (handcuffs != null) {
			if (handcuffs.length() < 150) {
				handcuffs = handcuffs + ", " + cuff.getShort();
			}
		} else {
			handcuffs = cuff.getShort();
		}
	}

	public Position getPosition() {
		return position;
	}

	public int getRoundDrafted() {
		return roundDrafted;
	}

	public void setRoundDrafted(int roundDrafted) {
		this.roundDrafted = roundDrafted;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String stats(){
		return playerName + "   (" + pos_rank + "/" + rank + ") [" + pos + ", " + teamName + ", bye:" + bye + "]";
	}
	
	public String getNameAndTags() {
		return (tags == null || tags.isEmpty()) ? playerName : playerName + " [" + tags + "]";
	}
	
	public String fullStats(){
		return String.format("|%3d | %-24s| %-3s | %3s/%-3s | %-4s | %-3s |%s", id, getNameAndTags(), pos, pos_rank, rank, teamName, bye, checkForHandcuff());
	}

	public boolean isAvailable() {
		return available;
	}

	public String getPos_rank() {
		return pos_rank;
	}

	public void setPos_rank(String pos_rank) {
		this.pos_rank = pos_rank;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String player) {
		this.playerName = player;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String team) {
		this.teamName = team;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getBye() {
		return bye;
	}

	public void setBye(String bye) {
		this.bye = bye;
	}

	public String getBest() {
		return best;
	}

	public void setBest(String best) {
		this.best = best;
	}

	public String getWorst() {
		return worst;
	}

	public void setWorst(String worst) {
		this.worst = worst;
	}

	public String getAvg() {
		return avg;
	}

	public void setAvg(String avg) {
		this.avg = avg;
	}

	public String getStd_dev() {
		return std_dev;
	}

	public void setStd_dev(String std_dev) {
		this.std_dev = std_dev;
	}

	public String getAdp() {
		return adp;
	}

	public void setAdp(String adp) {
		this.adp = adp;
	}

	public String getVersus() {
		return versus;
	}

	public void setVersus(String versus) {
		this.versus = versus;
	}
	
	public void markUnavailable(){
		this.available = false;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getTags() {
		return tags;
	}

	public ArrayList<Player> getBackups() {
		return backups;
	}

	public void setBackups(ArrayList<Player> backups) {
		this.backups = backups;
	}
	
	

}
