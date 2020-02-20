package user;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashSet;

import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

/**
 * This class is a Factory for the class User.
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
public abstract class UserFactory {
	UserFactory() {

	}

	public static Blob createDefaultPicture() {
		try {
			InputStream inputStream = FacesContext.getCurrentInstance().getExternalContext()
					.getResourceAsStream("/resources/images/profilepicture.png");
			BufferedImage bi = ImageIO.read(inputStream);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			ImageIO.write(bi, "png", stream);
			stream.flush();
			return new SerialBlob(stream.toByteArray());
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ProfileRightUser getProfileRightUser(User user, ProfileRightView viewProfileRight) {
		return new ProfileRightUser(user, viewProfileRight);
	}

	public static UserFactory getInstance(Role role) {
		switch (role) {
		case CUSTOMER:
			return new CustomerFactory();
		case ADMIN:
			return new AdminFactory();
		case EMPLOYEE:
			return new EmployeeFactory();
		}
		return null;
	}

	public abstract User create(String username, String password);

	public abstract void setValues(UserBasic basicUser);

	void setStandardValues(UserBasic basicUser) {
		basicUser.setFirstName("");
		basicUser.setLastName("");
		basicUser.setEmail("");
		basicUser.setGender(Gender.NOTHING);
		basicUser.setPayInfo(new PayInfo());
		basicUser.setTravels(new HashSet<Travel>());
		basicUser.setAddresses(new HashSet<Address>());
		basicUser.setTravels(new HashSet<Travel>());
		basicUser.setProfilePicture(createDefaultPicture());
		basicUser.setRequestList(new HashSet<Integer>());
	}
}

class CustomerFactory extends UserFactory {

	@Override
	public User create(String username, String password) {
		User user = new User();
		user.setUsername(username);
		user.setLoginData(new Login(password));
		setStandardValues(user);
		setValues(user);
		return user;
	}

	@Override
	public void setValues(UserBasic basicUser) {
		basicUser.setRole(Role.CUSTOMER);
		basicUser.setUserBehavior(new Customer());
	}

}

class AdminFactory extends UserFactory {

	@Override
	public User create(String username, String password) {
		User user = new User();
		user.setUsername(username);
		user.setLoginData(new Login(password));
		setStandardValues(user);
		setValues(user);
		return user;
	}

	@Override
	public void setValues(UserBasic basicUser) {
		basicUser.setRole(Role.ADMIN);
		basicUser.setUserBehavior(new Admin());
	}

}

class EmployeeFactory extends UserFactory {

	@Override
	public User create(String username, String password) {
		User user = new User();
		user.setUsername(username);
		user.setLoginData(new Login(password));
		setStandardValues(user);
		setValues(user);
		return user;
	}

	@Override
	public void setValues(UserBasic basicUser) {
		basicUser.setRole(Role.EMPLOYEE);
		basicUser.setUserBehavior(new Employee());
	}
}