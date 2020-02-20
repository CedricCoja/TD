package handler.userhandler;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.primefaces.event.RowEditEvent;

import handler.addresshandler.AddressHandler;
import handler.basichandler.LoginHandler;
import handler.friendhandler.FriendHandlerBasic;
import handler.friendhandler.FriendHandlerFactory;
import handler.profilhandler.ProfilHandler;
import handler.profilhandler.ProfilHandlerBasic;
import handler.profilhandler.ProfilHandlerFactory;
import handler.travelHandler.TravelHandler;
import handler.travelHandler.TravelHandlerFactory;
import user.ProfileRightUser;
import user.RightFactory;
import user.Role;
import user.Travel;
import user.User;
import user.UserDAO;
import user.UserFactory;
/**
 * This class is a Management Bean.
 * Which managened the login user.
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
public class UserHandler extends UserHandlerBasic {

	@PersistenceContext
	private EntityManager em;
	@Resource
	private UserTransaction utx;
	private DataModel<ProfileRightUser> users;
	private DataModel<Travel> travels;
	private String searchUsername;

	@Override
	public String showAddresses(ProfilHandler profilHandler, AddressHandler addressHandler) {
		setUser(UserDAO.loadUser(em, getUser()));
		ProfilHandlerFactory.setProfilHandlerValues(profilHandler, users.getRowData());
		return profilHandler.showAddresses(addressHandler);
	}

	public String showTravel(TravelHandler travelHandler) {
		Travel travel = travels.getRowData();
		TravelHandlerFactory.setTravelHandlerValues(travelHandler, getProfilRightUser(travel.getUser()));
		return travelHandler.showTravel(travel);
	}

	@Override
	public String deleteUser(ProfilHandler profilHandler, LoginHandler loginHandler) {
		setUser(UserDAO.loadUser(em, getUser()));
		ProfileRightUser tmp = profilHandler.getProfileRightUser();
		ProfilHandlerFactory.setProfilHandlerValues(profilHandler, users.getRowData());
		User value = profilHandler.delete();
		ProfileRightUser valueUser = getProfilRightUser(value, false);
		ProfilHandlerFactory.setProfilHandlerValues(profilHandler,
				tmp.equals(valueUser) ? getProfilRightUser(getUser()) : tmp);
		getAllUsers();
		if (value.equals(getUser())) {
			return loginHandler.logout();
		}
		return "/admin/users.xhtml?faces-redirect=true";
	}

	@Override
	public String showProfil(ProfilHandler profilHandler) {
		return showProfil(profilHandler, users.getRowData());
	}

	@Override
	public String showProfil(ProfilHandler profilHandler, Integer id) {
		return showProfil(profilHandler, getProfilRightUser(em.find(User.class, id)));
	}

	@Override
	public String showProfil(ProfilHandler profilHandler, User watch) {
		return showProfil(profilHandler, getProfilRightUser(watch));
	}

	@Override
	public String showProfil(ProfilHandler profilHandler, ProfileRightUser watch) {
		setUser(UserDAO.loadUser(em, getUser()));
		watch.reloadUser(em);
		ProfilHandlerFactory.setProfilHandlerValues(profilHandler, watch);
		return "/profil/showprofil.xhtml?faces-redirect=true";
	}

	@Override
	public String addTravel(TravelHandler travelHandler, User watch) {
		TravelHandlerFactory.setTravelHandlerValues(travelHandler, getProfilRightUser(watch));
		return travelHandler.allTravels();
	}

	@Override
	public String home() {
		return "/home.xhtml?faces-redirect=true";
	}

	private ProfileRightUser getProfilRightUser(User watch) {
		return getProfilRightUser(watch, true);
	}
	
	private ProfileRightUser getProfilRightUser(User watch, boolean mode) {
		if (mode) {
			watch = UserDAO.loadUser(em, watch);
		}
		return UserFactory.getProfileRightUser(watch, RightFactory.getInstance().getProfileRight(getUser(), watch));
	}

	@Override
	public DataModel<ProfileRightUser> getAllUsers() {
		String tmp = searchUsername;
		searchUsername = "%";
		setUser(UserDAO.loadUser(em, getUser()));
		users = UserDAO.toProfilRightUser(searchUsers(), getUser());
		searchUsername = tmp;
		return users;
	}

	@Override
	public DataModel<ProfileRightUser> getFoundUsers() {
		return users;
	}

	@Override
	public void setUsers(DataModel<ProfileRightUser> users) {
		this.users = users;
	}

	@Override
	public String getUserProfil(ProfilHandler profilHandler) {
		return showProfil(profilHandler, users.getRowData());
	}

	@Override
	public DataModel<ProfileRightUser> getUsers() {
		return users;
	}

	@Override
	public DataModel<User> searchUsers() {
		return getUser().performSearch(em, searchUsername);
	}

	public String getSearchUsername() {
		return searchUsername;
	}

	public void setSearchUsername(String searchUsername) {
		this.searchUsername = searchUsername;
	}

	@Override
	public String searchuser(FriendHandlerBasic friendHandlerBasic) {
		FriendHandlerFactory.setFriendHandlerFactory(friendHandlerBasic, getUser());
		setUsers(getAllUsers());
		return "/admin/users.xhtml?faces-redirect=true";
	}

	public void onRowEdit(RowEditEvent event) {
		User u = ((ProfileRightUser) event.getObject()).getUser();
		if (u != null) {
			UserDAO.save(em, utx, u);
			FacesMessage msg = new FacesMessage("User Edited", ((ProfileRightUser) event.getObject()).getUsername());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public String editCreditCard(ProfilHandlerBasic profilHandler) {
		ProfileRightUser watch = users.getRowData();
		watch.reloadUser(em);
		ProfilHandlerFactory.setProfilHandlerValues(profilHandler, watch);
		return profilHandler.editCreditCard();
	}

	public List<String> getGenders() {
		List<String> genders = Stream.of(user.Gender.values()).map(Enum::name).collect(Collectors.toList());
		return genders;
	}

	public Role[] getRoles() {
		return Role.values();
	}

	public String showTravels() {
		setUser(UserDAO.loadUser(em, getUser()));
		travels = getUser().perfomTravelSearch(em);
		return "/admin/travels.xhtml?faces-redirect=true";
	}

	@Override
	public DataModel<Travel> getTravels() {
		return travels;
	}

	@Override
	public void setTravels(DataModel<Travel> travels) {
		this.travels = travels;
	}

	@Override
	public String removeFriend(FriendHandlerBasic friendHandlerBasic, ProfileRightUser friend) {
		FriendHandlerFactory.setFriendHandlerFactory(friendHandlerBasic, this.getProfilRightUser(getUser()), null,
				null);
		friendHandlerBasic.removeFriend(friend);
		getAllUsers();
		return "/admin/users.xhtml?faces-redirect=true";
	}
}