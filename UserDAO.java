package user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.model.CollectionDataModel;
import javax.faces.model.DataModel;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 * This class is a connection between the entity class User and the DB.
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
public class UserDAO {

	private UserDAO() {

	}

	public static List<User> getUsers(EntityManager em, String username) {
		Query query = em.createQuery("Select u from User u where u.username like :username");
		query.setParameter("username", username);
		return castList(User.class, query.getResultList());
	}

	public static Set<ProfileRightUser> getById(EntityManager em, Set<Integer> ids, User owner) {
		Iterator<Integer> iterator = ids.iterator();
		Set<ProfileRightUser> tmp = new HashSet<ProfileRightUser>();
		while (iterator.hasNext()) {
			Integer next = iterator.next();
			User user;
			if ((user = em.find(User.class, next)) != null) {
				tmp.add(UserFactory.getProfileRightUser(user, RightFactory.getInstance().getProfileRight(owner, user)));
			}
		}

		ProfileRightUser value = UserFactory.getProfileRightUser(owner,
				RightFactory.getInstance().getProfileRight(owner, owner));
		if (tmp.remove(value)) {
			tmp.add(value);
		}
		
		return tmp;
	}

	public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
		List<T> r = new ArrayList<T>(c.size());
		for (Object o : c)
			r.add(clazz.cast(o));
		return r;
	}

	public static void add(EntityManager em, UserTransaction utx, User user) {
		try {
			utx.begin();
			em.persist(user);
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void save(EntityManager em, UserTransaction utx, User user) {
		try {
			utx.begin();
			user = em.merge(user);
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void delete(EntityManager em, UserTransaction utx, User user) {
		try {
			utx.begin();
			user = em.merge(user);
			em.remove(user);
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DataModel<ProfileRightUser> toProfilRightUser(DataModel<User> users, User owner) {
		Iterator<User> iterator = users.iterator();
		Set<ProfileRightUser> tmp = new HashSet<ProfileRightUser>();
		while (iterator.hasNext()) {
			User user = iterator.next();
			tmp.add(UserFactory.getProfileRightUser(user, RightFactory.getInstance().getProfileRight(owner, user)));
		}
		ProfileRightUser value = UserFactory.getProfileRightUser(owner,
				RightFactory.getInstance().getProfileRight(owner, owner));
		if (tmp.remove(value)) {
			tmp.add(value);
		}
		DataModel<ProfileRightUser> model = new CollectionDataModel<ProfileRightUser>();
		model.setWrappedData(tmp);
		return model;
	}
	
	public static User loadUser(EntityManager em, Integer id) {
		User user =em.find(User.class, id);
		UserFactory.getInstance(user.getRole()).setValues(user);
		return user;
	}

	public static Set<ProfileRightUser> getById(EntityManager em, Set<Integer> friends, ProfileRightUser user) {
		return getById(em, friends, user.getUser(true));
	}

	public static User loadUser(EntityManager em, User user) {
		return loadUser(em, user.getId());
	}

}