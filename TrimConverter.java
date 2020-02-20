package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


/**
 * This class calls trim on input String 
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
@FacesConverter(value = "trimConverter")
public class TrimConverter implements Converter {
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null)
			return null;
		return value.trim();
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return (String) value;
	}
}