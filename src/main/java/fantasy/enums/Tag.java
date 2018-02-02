package fantasy.enums;

import fantasy.model.Player;

public enum Tag {

	RISK("!", 0),
	SLEEPER("$", 2),
	ROOKIE("R", 1),
	NEW_TEAM("@", 0),
	FAVORITE("*", 3),
	RISING("+", 1),
	FALLING("-", -1),
	INJURY_RISK("i", -1),
	BUST("B", -2);
	
	private String tag;
	private int shift;
	
	Tag(String tag, int shift) {
		this.tag = tag;
		this.shift = shift;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public int getShift() {
		return shift;
	}

	public void setShift(int shift) {
		this.shift = shift;
	}

	public static boolean isRookie(Player player) {
		return player.getTags().contains(ROOKIE.getTag());
	}
	
	public static boolean isSleeper(Player player) {
		return player.getTags().contains(SLEEPER.getTag());
	}
	
	public static boolean isRisk(Player player) {
		return player.getTags().contains(RISK.getTag());
	}
	
	public static boolean isNewTeam(Player player) {
		return player.getTags().contains(NEW_TEAM.getTag());
	}
	
	public static boolean isFavorite(Player player) {
		return player.getTags().contains(FAVORITE.getTag());
	}
	
	public static boolean isRising(Player player) {
		return player.getTags().contains(RISING.getTag());
	}
	
	public static boolean isFalling(Player player) {
		return player.getTags().contains(FALLING.getTag());
	}
	
	public static boolean isBust(Player player) {
		return player.getTags().contains(BUST.getTag());
	}
	
	public static boolean isInjuryRisk(Player player) {
		return player.getTags().contains(INJURY_RISK.getTag());
	}
	
}
