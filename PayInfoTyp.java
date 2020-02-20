package user;

/**
 * Enum Class for PayInfoTyp
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
public enum PayInfoTyp {
	MASTER("MasterCard"), VISA("Visa"), AMEX("American Express");
	private final String label;
	private PayInfoTyp(String label) {
		this.label=label;
	}
	public String getLabel() {
		return label;
	}

}
