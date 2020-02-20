package validator;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;

/**
 * This class checks for a valid credit card number
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
@FacesValidator(value = "creditCardValidator")
public class CreditCardValidator implements Validator {
	/*
	 * Checks for a valid credit card number
	 * 
	 * @return void
	 * 
	 * @throws ValidatorException
	 */
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String kknr = String.valueOf(value);
		List<Integer> kkintarray = new ArrayList<Integer>();
		FacesMessage message;
		for (int i = 0; i < kknr.length(); i++)
			kkintarray.add(kknr.charAt(i) - 48); // ASCII -> Integer
		if (!kknr.matches("[0-9]+")) {
			ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages",
					FacesContext.getCurrentInstance().getViewRoot().getLocale());
			String text = bundle.getString("onlynumbersallowed");
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, text, text);
			throw new ValidatorException(message);
		}
		if (kknr.length() < 12 || kknr.length() > 16) {
			ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages",
					FacesContext.getCurrentInstance().getViewRoot().getLocale());
			String text = bundle.getString("onlylengthbetween12and16allowed");
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, text, text);
			throw new ValidatorException(message);
		}
		if (!isValid(kkintarray)) {
			ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages",
					FacesContext.getCurrentInstance().getViewRoot().getLocale());
			String text = bundle.getString("notvalid");
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, text, text);
			throw new ValidatorException(message);
		}
	}

	private boolean isValid(List<Integer> digits) {
		int sum = 0;
		int length = digits.size();
		for (int i = 0; i < length; i++) {
			Integer digit = digits.get(length - i - 1);
			if (i % 2 == 1) {
				digit *= 2;
			}
			sum += digit > 9 ? digit - 9 : digit;
		}
		return sum % 10 == 0;
	}
}
