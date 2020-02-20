package handler.friendhandler;

import java.util.HashSet;
import java.util.Set;

import javax.faces.model.CollectionDataModel;
import javax.faces.model.DataModel;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import user.ProfileRightUser;
import user.RightFactory;
import user.User;
import user.UserDAO;
import user.UserFactory;

public class FriendHandlerFactory {

	private FriendHandlerFactory() {

	}

	public static void setFriendHandlerFactory(FriendHandlerBasic friendHandlerBasic, User user) {
		setFriendHandlerFactory(friendHandlerBasic,
				UserFactory.getProfileRightUser(user, RightFactory.getInstance().getProfileRight(user, user)), null,
				null);
	}

	public static void setFriendHandlerFactory(FriendHandlerBasic friendHandlerBasic, ProfileRightUser user,
			EntityManager em, UserTransaction utx) {
		friendHandlerBasic.setUser(user);
		friendHandlerBasic.setFriends(new CollectionDataModel<ProfileRightUser>());
		friendHandlerBasic.setRequests(new CollectionDataModel<ProfileRightUser>());
		if (em != null) {
			Set<Integer> requests = removeFriendsFromRequest(user.getRequestList(), user.getFriends());
			friendHandlerBasic.getRequests().setWrappedData(UserDAO.getById(em, requests, user));
			friendHandlerBasic.getFriends().setWrappedData(UserDAO.getById(em, user.getFriends(), user));
			if (utx != null) {
				user.setRequestList(validUser(friendHandlerBasic.getRequests()));
				user.setFriends(validUser(friendHandlerBasic.getFriends()));
				user.save(utx, em);
			}
		}
	}

	private static Set<Integer> validUser(DataModel<ProfileRightUser> dataModel) {
		Set<Integer> found = new HashSet<Integer>();
		dataModel.forEach((n) -> {
			found.add(n.getId());
		});
		return found;
	}

	private static Set<Integer> removeFriendsFromRequest(Set<Integer> requests, Set<Integer> friends) {
		Set<Integer> found = new HashSet<Integer>();
		requests.forEach((n) -> {
			if (!friends.contains(n))
				found.add(n);
		});
		return found;
	}

}