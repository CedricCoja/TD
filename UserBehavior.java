package user;

import java.util.Set;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.EntityManager;

/**
 * This class makes custom User behavior possible
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
public interface UserBehavior {
	public DataModel<User> search(EntityManager em, String username);

	public boolean isAdmin();

	public boolean isWorkingByTravelDiary();

	public DataModel<Travel> travelSearch(EntityManager em, Set<Integer> friends);
}

class Customer implements UserBehavior {
	@Override
	public DataModel<User> search(EntityManager em, String username) {
		ListDataModel<User> users = new ListDataModel<User>();
		users.setWrappedData(UserDAO.getUsers(em, username));
		return users;
	}

	@Override
	public boolean isAdmin() {
		return false;
	}

	@Override
	public boolean isWorkingByTravelDiary() {
		return false;
	}

	@Override
	public DataModel<Travel> travelSearch(EntityManager em, Set<Integer> friends) {
		ListDataModel<Travel> users = new ListDataModel<Travel>();
		StringBuilder search = new StringBuilder();
		friends.forEach((id) -> search.append(" or t.user.id = " + id));
		users.setWrappedData(TravelDAO.getTravel(em, "where t.publicTravel = true" + search));
		return users;
	}
}

class Employee extends Customer {

	@Override
	public boolean isWorkingByTravelDiary() {
		return true;
	}

	@Override
	public DataModel<Travel> travelSearch(EntityManager em, Set<Integer> friends) {
		ListDataModel<Travel> users = new ListDataModel<Travel>();
		users.setWrappedData(TravelDAO.getTravel(em));
		return users;
	}
}

class Admin extends Employee {
	@Override
	public boolean isAdmin() {
		return true;
	}
}