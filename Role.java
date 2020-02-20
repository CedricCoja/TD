package user;

import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

/**
 * Enum Class for Role
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */

public enum Role {

	ADMIN("admin"), CUSTOMER("customer"), EMPLOYEE("employee");

	private final String label;

	private Role(String label) {
		this.label = label;
	}

	public String getLabel() {
		ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages",
				FacesContext.getCurrentInstance().getViewRoot().getLocale());
		if (!label.equals("")) {
			return bundle.getString(label);
		}
		return label;
	}

}