package fantasy.model;

import fantasy.builder.TeamBuilder;
import fantasy.enums.Position;
import fantasy.utility.Print;


public class Player {

	String rank;				//0
	String playerName;	//1
	String teamName;	//2
	String pos;				//3
	String bye;				//4
	String best;				//5
	String worst;			//6
	String avg;				//7
	String std_dev;		//8
	String adp;				//9
	String versus;			//10
	String pos_rank;
	boolean available;
	int id;
	String handcuffs;
	Position position;
	String notes;
	int roundDrafted;
	private String tags;
	
	
	public Player(String[] stats, int id) {
		super();
		this.rank = stats[0];
		this.teamName = TeamBuilder.getProperTeamName(stats[2]);
		setPosAndPosRank(stats[3]);
		this.playerName = appendForDefense(stats[1], this.pos);
		this.bye = stats[4];
		this.best = stats[5];
		this.worst = stats[6];
		this.avg = stats[7];
		this.std_dev = stats[8];
		this.adp = stats[9];
		this.versus = stats[10];
		this.available = true;
		this.id = id;
		this.tags = "";
	}
	
	private String checkForHandcuff() {
		return (handcuffs == null) ? "" : "  handcuffs=" + handcuffs;
	}
	
	public String getShort() {
		return "(" + id + ") " + getNameAndTags(); 
	}
	
	public void addAdditionalNotes(String addition) {
		this.notes = this.notes + "\n" + addition;
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

	private String appendForDefense(String name, String pos) {
		return (pos.equals(Position.DEFENSE.getAbbrev())) ? name.concat(" - " + pos) : name;
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
		handcuffs = (handcuffs == null) ? cuff.getShort() : handcuffs + ", " + cuff.getShort();
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

	public void setAvailability(boolean available) {
		this.available = available;
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
	
	

}
