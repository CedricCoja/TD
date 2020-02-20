package user;

/**
 * This class is a Factory for the class ProfileRightView.
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
public class RightFactory {

	public static RightFactory getInstance() {
		return new RightFactory();
	}

	public ProfileRightView getProfileRight(UserBasic owner, UserBasic watch) {
		ProfileRight profileRight = null;
		if (owner != null) {
			if (owner.equals(watch)) {
				profileRight = new OwnerProfileRight(false);
			} else {
				boolean notFriend = !owner.getFriends().contains(watch.getId());
				switch (owner.getRole()) {
				case EMPLOYEE:
					profileRight = new EmployeeProfileRight(notFriend);
					break;
				case ADMIN:
					profileRight = new AdminProfileRight(notFriend);
					break;
				default:
					break;
				}
				if (profileRight == null) {
					if (notFriend) {
						profileRight = new BasicProfileRight(true);
					} else {
						profileRight = new FriendProfileRight(false);
					}
				}
			}
		}
		watch.prepare(profileRight);
		return profileRight;
	}

	class BasicProfileRight implements ProfileRight {
		private boolean isFriend;
		private boolean basicInformation;
		private boolean detailInformation;
		private boolean creditCardInformation;
		private boolean addressInformation;

		private BasicProfileRight() {
			this(false);
		}

		private BasicProfileRight(boolean friend) {
			isFriend = friend;
		}

		@Override
		public boolean isBasicInformation() {
			return basicInformation;
		}

		@Override
		public void setBasicInformation(boolean basicInformation) {
			this.basicInformation = basicInformation;
		}

		@Override
		public boolean isDetailInformation() {
			return detailInformation;
		}

		@Override
		public void setDetailInformation(boolean detailInformation) {
			this.detailInformation = detailInformation;
		}

		@Override
		public boolean isPayInfoInformation() {
			return creditCardInformation;
		}

		@Override
		public void setPayInfoInformation(boolean creditCardInformation) {
			this.creditCardInformation = creditCardInformation;
		}

		@Override
		public boolean isAddressInformation() {
			return addressInformation;
		}

		@Override
		public void setAddressInformation(boolean addressInformation) {
			this.addressInformation = addressInformation;
		}

		@Override
		public boolean isCanBlockLogin() {
			return false;
		}

		@Override
		public boolean isCanEdit() {
			return false;
		}

		@Override
		public boolean isCanDelete() {
			return false;
		}

		@Override
		public boolean isLoginDataInformation() {
			return false;
		}

		@Override
		public boolean isCanChangeRole() {
			return false;
		}

		@Override
		public boolean isCanSendFriendsMessages() {
			return isFriend;
		}
		
		@Override
		public boolean isCanBeFriend() {
			return isCanSendFriendsMessages();
		}
	}

	class FriendProfileRight extends BasicProfileRight {
		private FriendProfileRight() {

		}

		private FriendProfileRight(boolean friend) {
			super(friend);
		}

		@Override
		public boolean isBasicInformation() {
			return true;
		}

		@Override
		public boolean isDetailInformation() {
			return true;
		}

		@Override
		public boolean isAddressInformation() {
			return true;
		}

	}

	class EmployeeProfileRight extends FriendProfileRight {
		private EmployeeProfileRight() {

		}

		private EmployeeProfileRight(boolean friend) {
			super(friend);
		}

		@Override
		public boolean isPayInfoInformation() {
			return true;
		}

		@Override
		public boolean isCanBlockLogin() {
			return true;
		}

		@Override
		public boolean isCanDelete() {
			return false;
		}

		@Override
		public boolean isLoginDataInformation() {
			return true;
		}

	}

	class OwnerProfileRight extends EmployeeProfileRight {

		private OwnerProfileRight() {

		}

		private OwnerProfileRight(boolean friend) {
			super(friend);
		}

		@Override
		public boolean isCanBlockLogin() {
			return false;
		}

		@Override
		public boolean isCanEdit() {
			return true;
		}

		@Override
		public boolean isCanDelete() {
			return true;
		}

		@Override
		public boolean isCanBeFriend() {
			return true;
		}
	}

	class AdminProfileRight extends OwnerProfileRight {
		private AdminProfileRight() {

		}

		private AdminProfileRight(boolean friend) {
			super(friend);
		}

		@Override
		public boolean isCanBeFriend() {
			return isCanSendFriendsMessages();
		}

		@Override
		public boolean isCanBlockLogin() {
			return true;
		}

		@Override
		public boolean isCanChangeRole() {
			return true;
		}

	}
}