package handler.basichandler;

import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import javax.validation.constraints.Size;

import user.Role;
import user.User;
import user.UserFactory;

/**
 * This class is a Management Bean. 
 * Which creates new User for the System. 
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
public class SignUpHandler {
	@Size(min=6, max=30)
	private String username;
	@Size(min=6, max=30)
	private String password;
	@Size(min=6, max=30)
	private String secPassword;
	@PersistenceContext
	private EntityManager em;
	@Resource
	private UserTransaction utx;

	public String addUser() {
		if (password.equals(secPassword)) {
			try {
				String search = "username";
				Query query = em.createQuery("select u from User u where u.username = :" + search);
				query.setParameter(search, username);
				if (query.getResultList().size() == 0) {
					utx.begin();
					User user = UserFactory.getInstance(Role.CUSTOMER).create(username, password);
					em.persist(user);
					utx.commit();
					return "/index.xhtml?faces-redirect=true";
				} else {
					ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages",
							FacesContext.getCurrentInstance().getViewRoot().getLocale());
					String text = bundle.getString("usertake");
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage("signupform:username",
							new FacesMessage(FacesMessage.SEVERITY_ERROR, text, text));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages",
					FacesContext.getCurrentInstance().getViewRoot().getLocale());
			String text = bundle.getString("passwordnotmatch");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage("signupform:password", new FacesMessage(FacesMessage.SEVERITY_ERROR, text, text));
			context.addMessage("signupform:repeat", new FacesMessage(FacesMessage.SEVERITY_ERROR, text, text));
		}
		return "/signup.xhtml";
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

	public String getSecPassword() {
		return secPassword;
	}

	public void setSecPassword(String secPassword) {
		this.secPassword = secPassword;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public UserTransaction getUtx() {
		return utx;
	}

	public void setUtx(UserTransaction utx) {
		this.utx = utx;
	}

}