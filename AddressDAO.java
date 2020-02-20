package user;

import javax.persistence.EntityManager;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * This class is a connection between the entity class Address and the DB.
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */

public class AddressDAO {

	private AddressDAO() {

	}

	/**
	 * Saves a Address Object in the DB
	 * 
	 * @return void
	 */
	public static void save(EntityManager em, UserTransaction utx, Address address, User user) {
		try {
			utx.begin();
			address = em.merge(address);
			user = em.merge(user);
			utx.commit();
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
		}
	}

	public static void add(EntityManager em, UserTransaction utx, Address address, User user) {
		try {
			utx.begin();
			address.setUser(user);
			user.getAddresses().add(address);
			em.persist(address);
			utx.commit();
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Removes a Address Object from the DB
	 * 
	 * @return void
	 */
	public static void delete(EntityManager em, UserTransaction utx, Address address, User user) {
		try {
			utx.begin();
			user.getAddresses().remove(address);
			address = em.merge(address);
			em.remove(address);
			user = em.merge(user);
			utx.commit();
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
		}
	}
}