package user;

/**
 * This class is a Factory for the class TravelDestination.
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
public class TravelDestinationFactory {
	public static TravelDestination create(Travel travel) {
		TravelDestination travelDestination = new TravelDestination();
		travelDestination.setTitle("");
		travelDestination.setText("");
		travelDestination.setTravel(travel);
		return travelDestination;
	}
}