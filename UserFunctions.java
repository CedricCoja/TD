package user;

import java.sql.Blob;
import java.util.Date;
import java.util.Set;
/**
 * This Interface is a user functions Interface
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
public interface UserFunctions {
	public void setFirstName(String firstName);

	public String getFirstName();

	public void setLastName(String lastName);

	public String getLastName();

	public void setPayInfo(PayInfo payInfo);

	public PayInfo getPayInfo();

	public void setLoginData(LoginData loginData);

	public void setGender(Gender gender);

	public Gender getGender();

	public String getUsername();

	public void setUsername(String username);
	
	public String getEmail();
	
	public void setEmail(String email);

	public Set<Address> getAddresses();

	public void setDetailInformation(Boolean detailInformation);

	public Boolean isDetailInformation();

	public void setAddressInformation(Boolean addressInformation);

	public Boolean isAddressInformation();

	public void setPayInfoInformation(Boolean payInfoInformation);

	public Boolean isPayInfoInformation();

	public void setBirthday(Date birthday);
	
	public Date getBirthday();

	public String getCompleteName();
	
	public Role getRole();

	public void setAddresses(Set<Address> addresses);

	public abstract void setTravels(Set<Travel> travels);

	public abstract Set<Travel> getTravels();

	public void setProfilePicture(Blob image);

	public boolean changePassword(String password, String oldPassword);

	public void setRole(Role role);

	public void setTravel(Travel travel);

	public Travel getTravel();

	public Integer getId();

	public void setRequestList(Set<Integer> set);

	public Set<Integer> getRequestList();

	Set<Integer> getFriends();

	void setFriends(Set<Integer> set);

}