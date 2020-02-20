package user;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.sql.rowset.serial.SerialBlob;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * This class models an user.
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */

@Entity
public class User extends UserBasic {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	private static final long serialVersionUID = 5345198306859656144L;
	private Role role;
	private String username;
	private Gender gender;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private LoginData loginData;
	private String lastName;
	private String firstName;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private PayInfo payInfo;
	private boolean payInfoInformation;
	private boolean addressInformation;
	private boolean detailInformation;
	private Date birthday;
	private String email;
	@Lob
	private byte[] profilePicture;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
	@NotFound(action = NotFoundAction.IGNORE)
	private Set<Address> addresses;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private Set<Travel> travels;
	@ElementCollection(fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	private Set<Integer> requestList;
	@ElementCollection(fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	private Set<Integer> friends;

	@Override
	public void prepare(ProfileRight profileRight) {
		profileRight.setBasicInformation(true);
		profileRight.setDetailInformation(detailInformation);
		profileRight.setAddressInformation(addressInformation);
		profileRight.setPayInfoInformation(payInfoInformation);
	}

	@Override
	public boolean changePassword(String password, String oldPassword) {
		return loginData.setpassword(password, oldPassword);
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public PayInfo getPayInfo() {
		return payInfo;
	}

	@Override
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public Gender getGender() {
		return gender;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public Set<Address> getAddresses() {
		return addresses;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setPayInfo(PayInfo payInfo) {
		this.payInfo = payInfo;
	}

	@Override
	public void setLoginData(LoginData loginData) {
		this.loginData = loginData;
	}

	@Override
	public Boolean isPayInfoInformation() {
		return payInfoInformation;
	}

	@Override
	public void setPayInfoInformation(Boolean payInfoInformation) {
		this.payInfoInformation = payInfoInformation;
	}

	@Override
	public Boolean isAddressInformation() {
		return addressInformation;
	}

	@Override
	public void setAddressInformation(Boolean addressInformation) {
		this.addressInformation = addressInformation;
	}

	@Override
	public Boolean isDetailInformation() {
		return detailInformation;
	}

	@Override
	public void setDetailInformation(Boolean detailInformation) {
		this.detailInformation = detailInformation;
	}

	@Override
	public void setRole(Role role) {
		this.role = role;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	@Override
	public LoginData getLoginData() {
		return loginData;
	}

	@Override
	public void setBirthday(Date birthday) {
		this.birthday = birthday;

	}

	@Override
	public Date getBirthday() {
		return birthday;
	}

	@Override
	public String getCompleteName() {
		return (gender.getLabel() + " " + firstName + " " + lastName).trim();
	}

	@Override
	public void setAddresses(HashSet<Address> addresses) {
		this.addresses = addresses;
	}

	@Override
	public Role getRole() {
		return role;
	}

	public Set<Travel> getTravels() {
		return travels;
	}

	public void setTravels(Set<Travel> travels) {
		this.travels = travels;
	}

	public Travel getTravel() {
		return (Travel) travels.toArray()[0];
	}

	public void setTravel(Travel travel) {
		travels.add(travel);
	}

	public Blob getProfilePicture() {
		try {
			return new SerialBlob(profilePicture);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setProfilePicture(Blob profilePicture) {
		try {
			if (profilePicture.length() != 0) {
				this.profilePicture = profilePicture.getBytes(1L, (int) profilePicture.length());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setRequestList(Set<Integer> set) {
		requestList = set;
	}

	@Override
	public Set<Integer> getRequestList() {
		return requestList;

	}

	@Override
	public Set<Integer> getFriends() {
		return friends;
	}

	@Override
	public void setFriends(Set<Integer> set) {
		friends = set;
	}
}