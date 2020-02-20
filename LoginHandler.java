package handler.basichandler;

import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import handler.travelHandler.TravelHandlerBasic;
import handler.travelHandler.TravelHandlerFactory;
import handler.userhandler.UserHandlerBasic;
import handler.userhandler.UserHandlerFactory;
import user.RightFactory;
import user.User;
import user.UserFactory;

/**
 * This class is a Management Bean. It allowed login
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
public class LoginHandler {
	private User user;
	private String username;
	private String password;
	@PersistenceContext
	private EntityManager em;
	@Resource
	private UserTransaction utx;

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/index.xhtml?faces -redirect=true";
	}

	public String login(UserHandlerBasic userHandlerBasic, TravelHandlerBasic travelHandlerBasic) {
		String search = "username";
		Query query = em.createQuery("select u from User u where u.username = :" + search);
		query.setParameter(search, username);
		@SuppressWarnings("unchecked")
		List<User> found = query.getResultList();
		if (found.size() == 1 && found.get(0).getLoginData().testPassword(password)) {
			user = found.get(0);
			UserFactory.getInstance(user.getRole()).setValues(user);
			UserHandlerFactory.setUserHandlerValues(userHandlerBasic, user);
			TravelHandlerFactory.setTravelHandlerValues(travelHandlerBasic,
					UserFactory.getProfileRightUser(user, RightFactory.getInstance().getProfileRight(user, user)));
			return "/home.xhtml?faces-redirect=true";
		} else {
			ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages",
					FacesContext.getCurrentInstance().getViewRoot().getLocale());
			String text = bundle.getString("cannotlogin");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage("loginform:username", new FacesMessage(FacesMessage.SEVERITY_ERROR, text, text));
			context.addMessage("loginform:password", new FacesMessage(FacesMessage.SEVERITY_ERROR, text, text));
			return null;
		}
	}

	public String deleteSelf(UserHandlerBasic userHandlerBasic, User user) {
		if (!user.equals(this.user)) {
			return userHandlerBasic.home();
		}
		return logout();
	}

	public void checkLoggedIn(ComponentSystemEvent cse) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (user == null) {
			context.getApplication().getNavigationHandler().handleNavigation(context, null,
					"/index.xhtml?faces-redirect=true");
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}