package user;

import java.util.LinkedList;

/**
 * This class is a Factory for the class Travel.
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
public class TravelFactory {
	public static Travel create(User user) {
		Travel travel = new Travel();
		travel.setTitle("");
		travel.setText("");
		travel.setUser(user);
		travel.setTravelDestinations(new LinkedList<TravelDestination>());
		return travel;
	}
}