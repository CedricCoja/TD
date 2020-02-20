package user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * This class models an address of a user.
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */

@Entity
public class Address implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String street;
	private String place;
	private String plz;
	@ManyToOne
	private User user;

	/**
	 * Gets the user which could contain some addresses.
	 * 
	 * @return user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user which could contain some addresses.
	 * 
	 * @return void
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Gets the street of an address.
	 * 
	 * @return street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Sets the street of an address.
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Gets the place of an address.
	 * 
	 * @return place
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * Sets the place of an address.
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * Gets the post code (plz) of an address.
	 * 
	 * @return plz
	 */
	public String getPlz() {
		return plz;
	}

	/**
	 * Sets the post code (plz) of an address.
	 */
	public void setPlz(String plz) {
		this.plz = plz;
	}

	/**
	 * Gets the Id (id) for an address.
	 * 
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the Id (id) for an address.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

}
