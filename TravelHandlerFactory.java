package handler.travelHandler;

import javax.faces.model.CollectionDataModel;
import javax.faces.model.DataModel;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.Polyline;

import user.ProfileRightUser;
import user.Travel;
import user.TravelDestination;
import user.TravelDestinationFactory;
import user.TravelFactory;

public class TravelHandlerFactory {
	private TravelHandlerFactory() {

	}

	public static void setTravelHandlerValues(TravelHandlerBasic basicTravelHandler,
			ProfileRightUser profileRightUser) {
		basicTravelHandler.setProfileRightUser(profileRightUser);
		DataModel<Travel> travels = new CollectionDataModel<Travel>();
		DataModel<TravelDestination> destinations = new CollectionDataModel<TravelDestination>();
		travels.setWrappedData(profileRightUser.getTravels());
		basicTravelHandler.setTravels(travels);
		basicTravelHandler.setTmpTravel(TravelFactory.create(basicTravelHandler.getProfileRightUser().getUser()));
		basicTravelHandler.setTmpDestination(TravelDestinationFactory.create(basicTravelHandler.getTmpTravel()));
		destinations.setWrappedData(basicTravelHandler.getTmpTravel().getTravelDestinations());
		basicTravelHandler.setDestinations(destinations);
		basicTravelHandler.setAdvancedModel(new DefaultMapModel());
		basicTravelHandler.setCenter("53.539784,8.581924");
		basicTravelHandler.setPolyline(new Polyline());
		basicTravelHandler.setShowPolylines(false);
	}
}
