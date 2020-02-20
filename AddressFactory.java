package user;

/**
 * This class is a Factory for the class Address.
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
public class AddressFactory {
	/**
	 * Creates a new Address Object
	 * 
	 * @return Address
	 */
	public static Address create(User user) {
		Address address = new Address();
		address.setPlace("");
		address.setPlz("");
		address.setStreet("");
		address.setUser(user);
		return address;
	}

}
