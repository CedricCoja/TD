package handler.profilhandler;

import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import handler.addresshandler.AddressHandler;
import handler.addresshandler.AddressHandlerFactory;
import handler.friendhandler.FriendHandlerBasic;
import handler.friendhandler.FriendHandlerFactory;
import user.Gender;
import user.PayInfoTyp;
import user.ProfileRightUser;
import user.User;
import user.UserDAO;

/**
 * This class is a Management Bean.
 * Which managened a Profil of a user.
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
public class ProfilHandler extends ProfilHandlerBasic {
	@PersistenceContext
	private EntityManager em;
	@Resource
	private UserTransaction utx;
	private String repeat;
	private String password;
	private String oldPassword;

	@Override
	public User delete() {
		ProfileRightUser profileRightUser = getProfileRightUser();
		if (!profileRightUser.getViewProfileRight().isCanDelete()) {
			return null;
		}
		User user;
		if ((user = profileRightUser.getUser()) == null) {
			return null;
		}
		UserDAO.delete(em, utx, user);
		return getProfileRightUser().getUser();
	}

	/**
	 * Returns payinfo.xhtml
	 * 
	 * @return String
	 */
	@Override
	public String editCreditCard() {
		getProfileRightUser().reloadUser(em);
		if (!getProfileRightUser().getViewProfileRight().isPayInfoInformation()) {
			return null;
		}
		return "/profil/payinfo.xhtml?faces-redirect=true";
	}

	@Override
	public String save() {
		User user;
		if ((user = getProfileRightUser().getUser()) == null) {
			return null;
		}
		UserDAO.save(em, utx, user);
		return cancel();
	}

	@Override
	public String cancel() {
		getProfileRightUser().reloadUser(em);
		return "/profil/showprofil.xhtml?faces-redirect=true";
	}

	/**
	 * Returns addresses.xhtml and set values in AddressHandler
	 * 
	 * @return String
	 */
	@Override
	public String showAddresses(AddressHandler addressHandler) {
		getProfileRightUser().reloadUser(em);
		if (!getProfileRightUser().getViewProfileRight().isAddressInformation()) {
			return null;
		}
		AddressHandlerFactory.setAddressHandlerValues(addressHandler, getProfileRightUser());
		return "/profil/addresses.xhtml?faces-redirect=true";
	}

	@Override
	public Gender[] getGenderValues() {
		return Gender.values();
	}

	@Override
	public PayInfoTyp[] getPayInfoTypeValues() {
		return PayInfoTyp.values();
	}

	/**
	 * Returns changePassword.xhtml
	 * 
	 * @return String
	 */
	@Override
	public String changePassword() {
		getProfileRightUser().reloadUser(em);
		return "/profil/changePassword.xhtml?faces-redirect=true";
	}

	/**
	 * Change Password from a User.
	 * 
	 * @return String
	 */
	@Override
	public String savePassword() {
		getProfileRightUser().reloadUser(em);
		if (!password.equals(repeat)) {
			ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages",
					FacesContext.getCurrentInstance().getViewRoot().getLocale());
			String text = bundle.getString("passwordnotmatch");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage("changepassword:password", new FacesMessage(FacesMessage.SEVERITY_ERROR, text, text));
			context.addMessage("changepassword:repeat", new FacesMessage(FacesMessage.SEVERITY_ERROR, text, text));
			return null;
		}
		if (!getProfileRightUser().changePassword(password, oldPassword)) {
			ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages",
					FacesContext.getCurrentInstance().getViewRoot().getLocale());
			String text = bundle.getString("wrongpassword");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage("changepassword:oldPassword", new FacesMessage(FacesMessage.SEVERITY_ERROR, text, text));
			return null;
		}
		save();
		return "/profil/showprofil.xhtml?faces-redirect=true";

	}

	/**
	 * Upload an image to the profile.
	 * 
	 * 
	 */
	@Override
	public void upload() {
		if (getProfileRightUser().getProfilePicture() != null) {
			FacesMessage message = new FacesMessage("Succesful");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		getProfileRightUser().save(utx, em);
	}

	/**
	 * Returns friends.xhtml and set values in FriendHandlerBasic
	 * 
	 * @return String
	 */
	@Override
	public String showFriendPage(FriendHandlerBasic friendHandlerBasic) {
		getProfileRightUser().reloadUser(em);
		FriendHandlerFactory.setFriendHandlerFactory(friendHandlerBasic, getProfileRightUser(), em, utx);
		return "/profil/friends.xhtml?faces-redirect=true";
	}

	@Override
	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	@Override
	public String getRepeat() {
		return "";
	}

	@Override
	public String getPassword() {
		return "";
	}

	@Override
	public String getOldPassword() {
		return "";
	}

}