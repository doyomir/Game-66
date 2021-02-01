package enums;

public enum CardName {
	NINE(0), TEN(10), JACK(2), QUEEN(3), KING(4), ACE(11);
	
	private final int value;

	
	CardName(int value) {
		this.value = value;
		
	}

	public int getPoints() {
		return this.value;
	}
	
	
}
