package enums;

public enum CardNames {
	NINE(0), TEN(10), JACK(2), QUEEN(3), KING(4), ACE(11);
	
	private final int value;

	
	CardNames(int value) {
		this.value = value;
		
	}

	public int getPoints() {
		return this.value;
	}
	
	
}
