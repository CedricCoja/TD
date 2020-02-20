package handler.profilhandler;

import handler.addresshandler.AddressHandler;
import handler.friendhandler.FriendHandlerBasic;
import user.Gender;
import user.PayInfoTyp;
import user.ProfileRightUser;
import user.User;

public abstract class ProfilHandlerBasic {

	private ProfileRightUser profileRightUser;

	public ProfileRightUser getProfileRightUser() {
		return profileRightUser;
	}

	public void setProfileRightUser(ProfileRightUser profileRightUser) {
		this.profileRightUser = profileRightUser;
	}

	public abstract Gender[] getGenderValues();

	public abstract User delete();

	public abstract String editCreditCard();

	public abstract String showAddresses(AddressHandler addressHandler);

	public abstract String save();

	public abstract String cancel();

	public abstract PayInfoTyp[] getPayInfoTypeValues();

	public abstract void upload();

	public abstract String savePassword();

	public abstract String changePassword();

	public abstract String getRepeat();

	public abstract void setRepeat(String repeat);

	public abstract String getPassword();

	public abstract void setPassword(String password);

	public abstract String getOldPassword();

	public abstract void setOldPassword(String oldPassword);

	public abstract String showFriendPage(FriendHandlerBasic friendHandlerBasic);


}
