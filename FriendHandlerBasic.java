package handler.friendhandler;

import javax.faces.model.DataModel;

import user.ProfileRightUser;

public abstract class FriendHandlerBasic {
	private ProfileRightUser user;

	public ProfileRightUser getUser() {
		return user;
	}

	public void setUser(ProfileRightUser user) {
		this.user = user;
	}

	public abstract DataModel<ProfileRightUser> getFriends();

	public abstract void setFriends(DataModel<ProfileRightUser> friends);

	public abstract DataModel<ProfileRightUser> getRequests();

	public abstract void setRequests(DataModel<ProfileRightUser> requests);

	public abstract String removeRequest();
	
	public abstract String removeFriend();

	public abstract String addFriend();

	public abstract String removeFriend(ProfileRightUser self, ProfileRightUser friend);

	public abstract String removeFriend(ProfileRightUser friend);
}