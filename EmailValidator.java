package validator;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * This class checks for a valid e-mail String
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
@FacesValidator(value = "emailValidator")
public class EmailValidator implements Validator {
	/**
	 * Checks for a valid e-mail String
	 * 
	 * @return void
	 * 
	 * @throws ValidatorException
	 */
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String kknr = String.valueOf(value);
		if (kknr.length() != 0 && !kknr.matches("^[^@]*@[^@.]*\\.[^@]*")) {
			ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages",
					FacesContext.getCurrentInstance().getViewRoot().getLocale());
			String text = bundle.getString("novalidemail");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, text, text));
		}
	}
}