package handler.userhandler;

import javax.faces.model.DataModel;

import handler.addresshandler.AddressHandler;
import handler.basichandler.LoginHandler;
import handler.friendhandler.FriendHandlerBasic;
import handler.profilhandler.ProfilHandler;
import handler.travelHandler.TravelHandler;
import user.ProfileRightUser;
import user.Travel;
import user.User;

public abstract class UserHandlerBasic {

	UserHandlerBasic() {
	}

	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public abstract String showAddresses(ProfilHandler profilHandler, AddressHandler addressHandler);

	public abstract String showProfil(ProfilHandler profilHandler, User watch);

	public abstract String home();

	public abstract String searchuser(FriendHandlerBasic friendHandlerBasic);

	public abstract String getUserProfil(ProfilHandler profilHandler);

	public abstract DataModel<ProfileRightUser> getAllUsers();

	public abstract DataModel<ProfileRightUser> getUsers();

	public abstract void setUsers(DataModel<ProfileRightUser> users);

	public abstract DataModel<ProfileRightUser> getFoundUsers();

	public abstract String getSearchUsername();

	public abstract void setSearchUsername(String searchUsername);

	public abstract DataModel<User> searchUsers();

	public abstract String addTravel(TravelHandler travelHandler, User watch);

	public abstract String showProfil(ProfilHandler profilHandler, ProfileRightUser watch);

	public abstract String showProfil(ProfilHandler profilHandler);

	public abstract String deleteUser(ProfilHandler profilHandler, LoginHandler loginHandler);

	public abstract void setTravels(DataModel<Travel> travels);

	public abstract DataModel<Travel> getTravels();

	public abstract String showProfil(ProfilHandler profilHandler, Integer id);

	public abstract String removeFriend(FriendHandlerBasic friendHandlerBasic, ProfileRightUser user);

}