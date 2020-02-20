package user;

import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

/**
 * Enum Class for Gender
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
public enum Gender {
	NOTHING(""), MR("mr"), MRS("mrs"), DIVERS("divers");
	private final String label;

	private Gender(String label) {
		this.label = label;
	}

	/**
	 * Get the label from Object
	 * 
	 * @return String
	 */
	public String getLabel() {
		ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages",
				FacesContext.getCurrentInstance().getViewRoot().getLocale());
		if (!label.equals("")) {
			return bundle.getString(label);
		}
		return label;
	}
}
