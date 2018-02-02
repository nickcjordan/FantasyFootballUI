package fantasy.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import fantasy.builder.TeamBuilder;
import fantasy.comparator.DraftRankComparator;
import fantasy.enums.Position;
import fantasy.manager.DisplayManager;

public class Team {

	String name;
	List<Player> qb;
	List<Player> rb;
	List<Player> wr;
	List<Player> te;
	List<Player> k;
	List<Player> d;
	int id;
	String mascot;
	DisplayManager display;
	List<Player> draftedPlayersInOrder;
	HashMap<Position, List<Player>> playersByPosition;
	
	
	public Team(String name, String mascot, int id) {
		super();
		this.name = name;
		qb = new ArrayList<Player>();
		rb = new ArrayList<Player>();
		wr = new ArrayList<Player>();
		te = new ArrayList<Player>();
		k = new ArrayList<Player>();
		d = new ArrayList<Player>();
		this.id = id;
		display = new DisplayManager();
		this.mascot = mascot;
		playersByPosition = buildPlayersByPosition();
	}

	@Override
	public String toString() {
		if (getNumberOfPlayersOnTeam() == 0) {
			return display.getTitle(name + " " + mascot) + "\n\t" + name + "'s team is empty...";
		} else {
			return display.getTitle(name + " " + mascot) + "\n"
					+ display.getHeader()
					+ "\n| QB:\t\t\t\t\t\t\t    |" 
					+ getAsString(qb)
					+ "\n| RB:\t\t\t\t\t\t\t    |" 
					+ getAsString(rb)
					+ "\n| WR:\t\t\t\t\t\t\t    |" 
					+ getAsString(wr)
					+ "\n| TE:\t\t\t\t\t\t\t    |" 
					+ getAsString(te)
					+ "\n| K:\t\t\t\t\t\t\t    |" 
					+ getAsString(k)
					+ "\n| DST:\t\t\t\t\t\t\t    |" 
					+ getAsDefenseString(d) 
					+ "\n" + display.getDivider();
		}
										
	}
	
	private String getAsDefenseString(List<Player> defense) {
		StringBuilder line = new StringBuilder();
		for (Player player : defense){
			line.append("\n" + display.getDivider());
			line.append("\n" + player.fullStats());
		}
		return line.toString();
	}

	public int getNumberOfPlayersOnTeam() {
		return qb.size() + rb.size() + wr.size() + te.size() + k.size() + d.size();
	}

	private String getAsString(List<Player> players) {
		StringBuilder line = new StringBuilder();
		for (Player player : players){
			line.append("\n" + display.getDivider());
			line.append("\n" + player.fullStats());
		}
		if (!players.isEmpty() && players.get(0).getPosition() != Position.DEFENSE) {
			line.append("\n" + display.getDivider());
		}
		return line.toString();
	}

	public List<Player> getAllInDraftedOrder() {
		if (draftedPlayersInOrder == null) {
			draftedPlayersInOrder = createDraftedPlayersInOrder();
		}
		return draftedPlayersInOrder;
	}
	
	public List<Player> getPlayersByPosition(Position position) {
		return playersByPosition.get(position);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void addQb(Player player){
		qb.add(player);
	}
	
	public void addRb(Player player){
		rb.add(player);
	}
	
	public void addWr(Player player){
		wr.add(player);
	}
	
	public void addTe(Player player){
		te.add(player);
	}
	
	public void addK(Player player){
		k.add(player);
	}
	
	public void addD(Player player){
		d.add(player);
	}
	
	public String getMascot() {
		return mascot;
	}

	public void setMascot(String mascot) {
		this.mascot = mascot;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Player> getQb() {
		return qb;
	}

	public void setQb(List<Player> qb) {
		this.qb = qb;
	}

	public List<Player> getRb() {
		return rb;
	}

	public void setRb(List<Player> rb) {
		this.rb = rb;
	}

	public List<Player> getWr() {
		return wr;
	}

	public void setWr(List<Player> wr) {
		this.wr = wr;
	}

	public List<Player> getTe() {
		return te;
	}

	public void setTe(List<Player> te) {
		this.te = te;
	}

	public List<Player> getK() {
		return k;
	}

	public void setK(List<Player> k) {
		this.k = k;
	}

	public List<Player> getD() {
		return d;
	}

	public void setD(List<Player> d) {
		this.d = d;
	}

	
	
	private List<Player> createDraftedPlayersInOrder() {
		List<Player> allPlayers = new ArrayList<Player>();
		allPlayers.addAll(qb);
		allPlayers.addAll(rb);
		allPlayers.addAll(wr);
		allPlayers.addAll(te);
		allPlayers.addAll(k);
		allPlayers.addAll(d);
		Collections.sort(allPlayers, new DraftRankComparator());
		return allPlayers;
	}

	private HashMap<Position, List<Player>> buildPlayersByPosition() {
		HashMap<Position, List<Player>> players = new HashMap<Position, List<Player>>();
		players.put(Position.QUARTERBACK, qb);
		players.put(Position.RUNNINGBACK, rb);
		players.put(Position.WIDERECEIVER, wr);
		players.put(Position.TIGHTEND, te);
		players.put(Position.KICKER, k);
		players.put(Position.DEFENSE, d);
		return players;
	}

	public List<List<Player>> getPositionLists() {
		List<List<Player>> positions = new ArrayList<List<Player>>();
		positions.add(getQb());
		positions.add(getRb());
		positions.add(getWr());
		positions.add(getTe());
		positions.add(getK());
		positions.add(getD());
		return positions;
	}
	
	
}
