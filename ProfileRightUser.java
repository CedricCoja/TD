package user;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 * This class models an Interface of an User
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
public class ProfileRightUser implements UserFunctions {

	private User user;
	private final ProfileRightView viewProfileRight;

	public ProfileRightUser(User user, ProfileRightView viewProfileRight) {
		this.user = user;
		this.viewProfileRight = viewProfileRight;
	}

	@Override
	public void setFirstName(String firstName) {
		if (viewProfileRight.isCanEdit()) {
			user.setFirstName(firstName);
		}
	}

	@Override
	public String getFirstName() {
		if (!viewProfileRight.isDetailInformation()) {
			return null;
		}
		return user.getFirstName();
	}

	@Override
	public void setLastName(String lastName) {
		if (viewProfileRight.isCanEdit()) {
			user.setLastName(lastName);
		}
	}

	@Override
	public String getLastName() {
		if (!viewProfileRight.isDetailInformation()) {
			return null;
		}
		return user.getLastName();
	}

	@Override
	public void setPayInfo(PayInfo payInfo) {
		if (viewProfileRight.isCanEdit()) {
			user.setPayInfo(payInfo);
		}
	}

	@Override
	public PayInfo getPayInfo() {
		if (!viewProfileRight.isPayInfoInformation()) {
			return null;
		}
		return user.getPayInfo();
	}

	@Override
	public void setLoginData(LoginData loginData) {
		if (viewProfileRight.isCanEdit()) {
			user.setLoginData(loginData);
		}
	}

	@Override
	public void setGender(Gender gender) {
		if (viewProfileRight.isCanEdit()) {
			user.setGender(gender);
		}
	}

	@Override
	public Gender getGender() {
		if (!viewProfileRight.isDetailInformation()) {
			return null;
		}
		return user.getGender();
	}

	@Override
	public String getUsername() {
		if (!viewProfileRight.isBasicInformation()) {
			return null;
		}
		return user.getUsername();
	}

	@Override
	public void setUsername(String username) {
		if (viewProfileRight.isCanEdit()) {
			user.setUsername(username);
		}
	}

	@Override
	public void setAddresses(Set<Address> addresses) {
		if (!viewProfileRight.isCanEdit()) {
			user.setAddresses(addresses);
		}

	}

	@Override
	public Set<Address> getAddresses() {
		if (!viewProfileRight.isAddressInformation()) {
			return null;
		}
		return user.getAddresses();
	}

	@Override
	public void setTravels(Set<Travel> travels) {
		if (!viewProfileRight.isCanEdit()) {
			user.setTravels(travels);
		}

	}

	@Override
	public Set<Travel> getTravels() {
		if (!viewProfileRight.isAddressInformation()) {
			return null;
		}
		return user.getTravels();
	}

	@Override
	public void setDetailInformation(Boolean detailInformation) {
		if (viewProfileRight.isCanEdit()) {
			user.setDetailInformation(detailInformation);
		}
	}

	@Override
	public Boolean isDetailInformation() {
		if (!viewProfileRight.isDetailInformation()) {
			return null;
		}
		return user.isDetailInformation();
	}

	@Override
	public void setAddressInformation(Boolean addressInformation) {
		if (viewProfileRight.isCanEdit()) {
			user.setAddressInformation(addressInformation);
		}
	}

	@Override
	public Boolean isAddressInformation() {
		if (!viewProfileRight.isDetailInformation()) {
			return null;
		}
		return user.isAddressInformation();
	}

	@Override
	public void setPayInfoInformation(Boolean payInfoInformation) {
		if (viewProfileRight.isCanEdit()) {
			user.setPayInfoInformation(payInfoInformation);
		}
	}

	@Override
	public Boolean isPayInfoInformation() {
		if (!viewProfileRight.isDetailInformation()) {
			return null;
		}
		return user.isPayInfoInformation();
	}

	public User getUser() {
		if (!viewProfileRight.isCanEdit()) {
			return null;
		}
		return user;
	}

	@Override
	public void setBirthday(Date birthday) {
		if (viewProfileRight.isCanEdit()) {
			user.setBirthday(birthday);
		}
	}

	@Override
	public Date getBirthday() {
		if (!viewProfileRight.isDetailInformation()) {
			return null;
		}
		return user.getBirthday();
	}

	public ProfileRightView getViewProfileRight() {
		return viewProfileRight;
	}

	@Override
	public String getCompleteName() {
		if (!viewProfileRight.isDetailInformation()) {
			return null;
		}
		return user.getCompleteName();
	}

	@Override
	public Role getRole() {
		return user.getRole();
	}

	public void save(UserTransaction utx, EntityManager em) {
		UserDAO.save(em, utx, user);
	}

	@Override
	public String getEmail() {
		if (!viewProfileRight.isBasicInformation()) {
			return null;
		}
		return user.getEmail();
	}

	@Override
	public void setEmail(String email) {
		if (viewProfileRight.isCanEdit()) {
			user.setEmail(email);
		}
	}

	@Override
	public Travel getTravel() {
		if (!viewProfileRight.isDetailInformation()) {
			if (!user.getTravel().isPublicTravel()) {
				return null;
			}
		}
		return user.getTravel();
	}

	@Override
	public void setTravel(Travel travel) {
		if (viewProfileRight.isCanEdit()) {
			user.setTravel(travel);
		}
	}

	public StreamedContent getProfilePictureStream() {
		try {
			if (user != null && user.getProfilePicture() != null) {
				return new DefaultStreamedContent(user.getProfilePicture().getBinaryStream());

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public UploadedFile getProfilePicture() {
		return null;
	}

	public void setProfilePicture(UploadedFile file) {
		try {
			setProfilePicture(ImageToBlob.toBlob(file));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setProfilePicture(Blob image) {
		if (viewProfileRight.isCanEdit()) {
			user.setProfilePicture(image);
		}
	}

	@Override
	public boolean changePassword(String password, String oldPassword) {
		if (viewProfileRight.isCanEdit()) {
			return user.changePassword(password, oldPassword);
		}
		return false;
	}

	@Override
	public void setRole(Role role) {
		if (viewProfileRight.isCanEdit() && viewProfileRight.isCanChangeRole()) {
			user.setRole(role);
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProfileRightUser other = (ProfileRightUser) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public Integer getId() {
		return user.getId();
	}

	@Override
	public void setRequestList(Set<Integer> set) {
		if (viewProfileRight.isCanEdit()) {
			user.setRequestList(set);
		}
	}

	@Override
	public Set<Integer> getRequestList() {
		return user.getRequestList();
	}

	@Override
	public Set<Integer> getFriends() {
		if (viewProfileRight.isDetailInformation()) {
			return user.getFriends();
		}
		return null;
	}

	@Override
	public void setFriends(Set<Integer> set) {
		if (viewProfileRight.isCanEdit()) {
			user.setFriends(set);
		}

	}

	public void reloadUser(EntityManager em) {
		user = UserDAO.loadUser(em, user.id);
	}

	public void removeFriend(Integer id) {
		user.getFriends().remove(id);
	}

	public void removeRequest(Integer id) {
		user.getRequestList().remove(id);

	}

	public void addFriend(Integer id) {
		user.getFriends().add(id);
	}

	public User getUser(boolean mode) {
		if (mode) {
			return user;
		}
		return getUser();
	}
}