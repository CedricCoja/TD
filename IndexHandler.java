package handler.basichandler;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import user.Role;
import user.User;
import user.UserDAO;
import user.UserFactory;

/**
 * This class is a Management Bean. It generate basic Users (admin, nobody)
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
public class IndexHandler {
	@PersistenceContext
	private EntityManager em;
	@Resource
	private UserTransaction utx;

	/**
	 * Creates user admin and nobody, if not existing
	 * 
	 */
	@PostConstruct
	public void init() {
		try {
			Query query = em.createQuery("select u from User u where u.username = 'nobody'");
			if (query.getResultList().size() == 0) {
				User user = UserFactory.getInstance(Role.CUSTOMER).create("nobody", "nobody");
				UserDAO.add(em, utx, user);
			}
			query = em.createQuery("select u from User u where u.username = 'admin'");
			if (query.getResultList().size() == 0) {
				User user = UserFactory.getInstance(Role.ADMIN).create("admin", "admin");
				UserDAO.add(em, utx, user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get path to login page.
	 * 
	 * @return "/login.xhtml?faces-redirect=true"
	 */
	public String logIn() {
		return "/login.xhtml?faces-redirect=true";
	}

	/**
	 * Get path to login signup page.
	 * 
	 * @return "/signup.xhtml?faces-redirect=true"
	 */
	public String signUp() {
		return "/signup.xhtml?faces-redirect=true";
	}
}