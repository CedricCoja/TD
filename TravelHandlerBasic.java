package handler.travelHandler;

import javax.faces.model.DataModel;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polyline;

import user.ProfileRightUser;
import user.Travel;
import user.TravelDestination;

public abstract class TravelHandlerBasic {

	private ProfileRightUser profileRightUser;

	public abstract String saveTravel();

	public abstract DataModel<Travel> getTravels();

	public abstract void setTravels(DataModel<Travel> travels);

	public abstract Travel getTravel();

	public abstract void setTravel(Travel travels);

	public abstract Travel getTmpTravel();

	public abstract void setTmpTravel(Travel tmpTravel);

	public abstract TravelDestination getTmpDestination();

	public abstract void setTmpDestination(TravelDestination tmpDestination);

	public abstract MapModel getAdvancedModel();

	public abstract void setAdvancedModel(MapModel advancedModel);

	public abstract Marker getMarker();

	public abstract void setMarker(Marker marker);

	public abstract String getCenter();

	public abstract void setCenter(String center);

	public abstract DataModel<TravelDestination> getDestinations();

	public abstract void setDestinations(DataModel<TravelDestination> destinations);

	public abstract Polyline getPolyline();

	public abstract void setPolyline(Polyline polyline);

	public abstract boolean isShowPolylines();

	public abstract void setShowPolylines(boolean showPolylines);

	public abstract String deleteTravel();

	public abstract void fillModel();

	public abstract void fillCompleteModel();

	public abstract void onMarkerSelect(OverlaySelectEvent event);

	public abstract String newTravel();

	public abstract String addTravel();

	public abstract String allTravels();

	public abstract String showTravel();

	public abstract String showTravel(Travel tmpTravel);

	public abstract String centerFirst();

	public abstract void centerOn();

	public abstract String centerLast();

	public abstract String editTravel();

	public abstract String editDestinations();

	public abstract String addDestination();

	public abstract String addRemoveCompletePolylines();

	public abstract void addCompletePolylines();

	public abstract void addRemovePolylines();

	public abstract void addPolylines();

	public ProfileRightUser getProfileRightUser() {
		return profileRightUser;
	}

	public void setProfileRightUser(ProfileRightUser profileRightUser) {
		this.profileRightUser = profileRightUser;
	}

	public abstract String deleteDestination(boolean mode);

}
