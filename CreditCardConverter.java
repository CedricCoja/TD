package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * This class removes space and - from input
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
@FacesConverter(value = "creditCardConverter")
public class CreditCardConverter implements Converter {
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return null;
		}
		String s = value;
		s = value.replace(" ", "");
		s = value.replace("-", "");
		return s;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return (String) value;
	}
}