package validator;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * This class checks for a valid plz for a address
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
@FacesValidator(value = "addressValidator")
public class AddressValidator implements Validator {

	/*
	 * Checks for a valid plz
	 * 
	 * @return void
	 * 
	 * @throws ValidatorException
	 */
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String plz = String.valueOf(value);
		if (plz.matches("[^0-9]")) {
			ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages",
					FacesContext.getCurrentInstance().getViewRoot().getLocale());
			String text = bundle.getString("onlynumbersallowed");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, text, text));
		}

		if (plz.length() != 5) {
			ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages",
					FacesContext.getCurrentInstance().getViewRoot().getLocale());
			String text = bundle.getString("onlyfivenumbersallowed");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, text, text));
		}
	}
}
