package handler.friendhandler;

import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import user.ProfileRightUser;
import user.UserDAO;

/**
 * This class is a Management Bean.
 * Manages two ID lists. 
 * Which friends and requests represent.
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
public class FriendHandler extends FriendHandlerBasic {

	@PersistenceContext
	private EntityManager em;
	@Resource
	private UserTransaction utx;

	private DataModel<ProfileRightUser> requests;

	private DataModel<ProfileRightUser> friends;

	public void sendFriendRequest(ProfileRightUser to) {
		to.getRequestList().add(getUser().getId());
		to.save(utx, em);
		ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages",
				FacesContext.getCurrentInstance().getViewRoot().getLocale());
		String text = bundle.getString("sendRequest");
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage("form:sendFriendRequest", new FacesMessage(FacesMessage.SEVERITY_ERROR, text, text));
	}

	@Override
	public DataModel<ProfileRightUser> getFriends() {
		return friends;
	}

	@Override
	public void setFriends(DataModel<ProfileRightUser> friends) {
		this.friends = friends;
	}

	@Override
	public DataModel<ProfileRightUser> getRequests() {
		return requests;
	}

	@Override
	public void setRequests(DataModel<ProfileRightUser> requests) {
		this.requests = requests;
	}

	@Override
	public String removeRequest() {
		getUser().getRequestList().remove(requests.getRowData().getId());
		getUser().save(utx, em);
		requests.setWrappedData(UserDAO.getById(em, getUser().getRequestList(), getUser()));
		return null;
	}

	@Override
	public String addFriend() {
		ProfileRightUser friend = requests.getRowData();
		Integer friendId = friend.getId();
		removeRequest();
		getUser().getFriends().add(friendId);
		getUser().save(utx, em);
		friends.setWrappedData(UserDAO.getById(em, getUser().getFriends(), getUser()));
		friend.removeRequest(getUser().getId());
		friend.addFriend(getUser().getId());
		friend.save(utx, em);
		return null;
	}

	@Override
	public String removeFriend() {
		removeFriend(getUser(), friends.getRowData());
		friends.setWrappedData(UserDAO.getById(em, getUser().getFriends(), getUser()));
		return null;
	}

	@Override
	public String removeFriend(ProfileRightUser friend) {
		removeFriend(getUser(), friend);
		return null;
	}

	@Override
	public String removeFriend(ProfileRightUser self, ProfileRightUser friend) {
		friend.removeFriend(getUser().getId());
		friend.save(utx, em);
		self.getFriends().remove(friend.getId());
		self.save(utx, em);
		return null;
	}
}