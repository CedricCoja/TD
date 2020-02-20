package handler.basichandler;

import java.util.LinkedList;
import java.util.List;

import javax.faces.context.FacesContext;

/**
 * This class is a Management Bean which keeps track of all visited pages
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
public class ReturnHandler {
	private List<String> pages = new LinkedList<String>();

	/**
	 * Adds the last visited page
	 * 
	 */
	public void visitPage() {
		String value = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		if (pages.isEmpty() || !pages.get(pages.size() - 1).equals(value)) {
			pages.add(value);
			if (100 <= pages.size()) {
				pages.remove(0);
			}
		}
	}

	public String lastPage() {
		if (pages.isEmpty()) {
			return "/home.xhtml";
		}
		return pages.get(pages.size() - 1);
	}

	public String back() {
		if (pages.size() < 2) {
			return "/home.xhtml";
		}
		String value = pages.get(pages.size() - 2);
		pages.remove(pages.size() - 1);
		pages.remove(pages.size() - 1);
		return value;
	}
}