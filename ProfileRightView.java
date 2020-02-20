package user;

/**
 * This class allows differend User rightes
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */

public interface ProfileRightView {
	public boolean isBasicInformation();

	public boolean isDetailInformation();

	public boolean isPayInfoInformation();

	public boolean isAddressInformation();

	public boolean isCanBlockLogin();

	public boolean isCanEdit();

	public boolean isCanDelete();

	public boolean isLoginDataInformation();

	public boolean isCanChangeRole();

	public boolean isCanSendFriendsMessages();

	public boolean isCanBeFriend();

}

interface ProfileRight extends ProfileRightView {

	public void setBasicInformation(boolean basicInformation);

	public void setDetailInformation(boolean detailInformation);

	public void setPayInfoInformation(boolean creditCardInformation);

	public void setAddressInformation(boolean addressInformation);

}