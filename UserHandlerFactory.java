package handler.userhandler;

import javax.faces.model.CollectionDataModel;
import javax.faces.model.ListDataModel;

import user.ProfileRightUser;
import user.Travel;
import user.User;

public class UserHandlerFactory {
	private UserHandlerFactory() {

	}

	public static void setUserHandlerValues(UserHandlerBasic basicUserHandler, User user) {
		basicUserHandler.setUser(user);
		basicUserHandler.setUsers(new CollectionDataModel<ProfileRightUser>());
		basicUserHandler.setTravels(new ListDataModel<Travel>());
		basicUserHandler.setSearchUsername("");
	};
}