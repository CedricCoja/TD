package user;

import java.io.Serializable;
import java.util.HashSet;

import javax.faces.model.DataModel;
import javax.persistence.EntityManager;

import user.UserBehavior;

/**
 * This class contains BasicUser functions and set behavior for some functions
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */

public abstract class UserBasic implements UserFunctions, Serializable {

	/**
	 * Test if the given input matches the hash password
	 * 
	 * @r
	 */
	UserBasic() {
		userBehavior = new Customer();
	}

	private static final long serialVersionUID = -7287692648922269578L;
	private UserBehavior userBehavior;

	public void setUserBehavior(UserBehavior userBehavior) {
		this.userBehavior = userBehavior;
	}

	public abstract void prepare(ProfileRight profileRight);

	public abstract LoginData getLoginData();

	protected abstract void setAddresses(HashSet<Address> hashSet);

	public DataModel<User> performSearch(EntityManager em, String username) {
		return userBehavior.search(em, username);
	}

	public boolean performIsWorkingByTravelDiary() {
		return userBehavior.isWorkingByTravelDiary();
	}

	public boolean performIsAdmin() {
		return userBehavior.isAdmin();
	}

	public DataModel<Travel> perfomTravelSearch(EntityManager em) {
		return userBehavior.travelSearch(em, getFriends());
	}

}