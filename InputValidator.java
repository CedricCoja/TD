package validator;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * This class checks for a valid input String
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
@FacesValidator(value = "inputValidator")
public class InputValidator implements Validator {
	/**
	 * Checks for a valid e-mail String
	 * 
	 * @return void
	 * 
	 * @throws ValidatorException
	 */
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String kknr = String.valueOf(value);
		if (kknr.length() != 0 && !kknr.matches("^[A-Za-z0-9]*$")) {
			ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages",
					FacesContext.getCurrentInstance().getViewRoot().getLocale());
			String text = bundle.getString("novalidename")+"!\n"+ bundle.getString("lettersandnumbers");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, text, text));
		}
	}
}