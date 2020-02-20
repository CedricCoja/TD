package user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 * This class is a connection between the entity class Travel, TravelDestination and the DB.
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
public class TravelDAO {
	private TravelDAO() {

	}
	
	public static void addTravel(EntityManager em, UserTransaction utx, Travel travel, User user) {
		try {
			utx.begin();
			travel.setUser(user);
			em.persist(travel);
			user = em.merge(user);
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void saveTravel(EntityManager em, UserTransaction utx, Travel travel, User user) {
		try {
			utx.begin();
			travel = em.merge(travel);
			user = em.merge(user);
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public static void removeTravel(EntityManager em, UserTransaction utx, Travel travel, User user) {
		try {
			utx.begin();
			user.getTravels().remove(travel);
			travel = em.merge(travel);
			em.remove(travel);
			user = em.merge(user);
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void addDestination(EntityManager em, UserTransaction utx, TravelDestination destination, Travel travel) {
		try {
			utx.begin();
			destination.setTravel(travel);
			travel.getTravelDestinations().add(destination);
			em.persist(destination);
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void saveDestination(EntityManager em, UserTransaction utx, TravelDestination destination) {
		try {
			utx.begin();
			destination = em.merge(destination);
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void removeDestination(EntityManager em, UserTransaction utx, TravelDestination destination, Travel travel) {
		try {
			utx.begin();
			travel.getTravelDestinations().remove(destination);
			destination = em.merge(destination);
			em.remove(destination);
			travel = em.merge(travel);
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<Travel>  getTravel(EntityManager em, String where) {
		Query query = em.createQuery("select t from Travel t " + where);
		return castList(Travel.class, query.getResultList());
	}

	public static List<Travel> getTravel(EntityManager em) {
		return getTravel(em,"");
	}
	
	public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
		List<T> r = new ArrayList<T>(c.size());
		for (Object o : c)
			r.add(clazz.cast(o));
		return r;
	}
}