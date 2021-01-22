package enums;

public enum CardColor {
	SPADE(4), HEART(3), DIAMOND(2), CLUB(1);

	private final int value;

	CardColor(int value) {
		this.value = value;

	}

	public int getPoints() {
		return this.value;
	}

}
