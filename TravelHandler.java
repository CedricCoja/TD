package handler.travelHandler;

import java.util.Iterator;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.model.DataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polyline;

import user.Travel;
import user.TravelDAO;
import user.TravelDestination;
import user.TravelDestinationFactory;
import user.TravelFactory;
import user.User;

/**
 * This class is a Management Bean.
 * Which models the handler class for the travels
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */

public class TravelHandler extends TravelHandlerBasic {
	@PersistenceContext
	private EntityManager em;
	@Resource
	private UserTransaction utx;
	private DataModel<Travel> travels;
	private Travel tmpTravel;
	private DataModel<TravelDestination> destinations;
	private TravelDestination tmpDestination;
	private MapModel advancedModel;
	private Marker marker;
	private String center;
	private Polyline polyline;
	private boolean showPolylines;

	@PostConstruct
	public void init() {
	}

	@Override
	public void onMarkerSelect(OverlaySelectEvent event) {

		marker = (Marker) event.getOverlay();
	}

	/**
	 * Empties the map and opens the "New Travel"-Facelet.
	 */
	@Override
	public String newTravel() {
		tmpTravel = TravelFactory.create(getProfileRightUser().getUser());
		if (tmpTravel != null) {
			advancedModel = new DefaultMapModel();
			return "/map/MarkerWindow3.xhtml?faces-redirect=true";
		}
		return allTravels();
	}

	/**
	 * Creates a new travel or if it already exists, saves the changes.
	 */
	@Override
	public String addTravel() {
		User user = null;
		if ((user = getProfileRightUser().getUser()) != null) {
			if (tmpTravel.getId() != null) {
				TravelDAO.saveTravel(em, utx, tmpTravel, user);
			} else {
				TravelDAO.addTravel(em, utx, tmpTravel, user);
			}
			travels.setWrappedData(user.getTravels());
		}
		return editDestinations();
	}

	/**
	 * Fills the map with all Travels of an specific user and centers the map on HS
	 * Bremerhaven.
	 */
	@Override
	public String allTravels() {
		advancedModel = new DefaultMapModel();
		fillCompleteModel();
		center = "53.539784,8.581924";
		return "/map/MarkerWindow2.xhtml?faces-redirect=true";
	}

	@Override
	public String showTravel() {
		return showTravel(travels.getRowData());
	}

	@Override
	public String showTravel(Travel tmpTravel) {
		showPolylines = false;
		this.tmpTravel = tmpTravel;
		advancedModel = new DefaultMapModel();
		fillModel();
		destinations.setWrappedData(tmpTravel.getTravelDestinations());
		center = centerFirst();
		return "/map/ShowTravel.xhtml?faces-redirect=true";
	}

	/**
	 * Centers the map on the last destination of a travel.
	 */
	@Override
	public String centerFirst() {
		if (!tmpTravel.getTravelDestinations().isEmpty()) {
			String lat = String.valueOf(tmpTravel.getTravelDestinations().iterator().next().getLat());
			String lng = String.valueOf(tmpTravel.getTravelDestinations().iterator().next().getLng());
			return lat + "," + lng;
		}
		return "53.539784,8.581924";
	}

	/**
	 * Centers the map on the selected destination of a travel.
	 */
	@Override
	public void centerOn() {
		TravelDestination destination = destinations.getRowData();
		String lat = String.valueOf(destination.getLat());
		String lng = String.valueOf(destination.getLng());
		center = lat + "," + lng;
	}

	/**
	 * Centers the map on the first destination of a travel.
	 */
	@Override
	public String centerLast() {
		if (!tmpTravel.getTravelDestinations().isEmpty()) {
			Iterator<TravelDestination> itr = tmpTravel.getTravelDestinations().iterator();
			TravelDestination lastDestination = itr.next();
			while (itr.hasNext()) {
				lastDestination = itr.next();
			}
			String lat = String.valueOf(lastDestination.getLat());
			String lng = String.valueOf(lastDestination.getLng());
			return lat + "," + lng;
		}
		return "53.539784,8.581924";
	}

	@Override
	public String editTravel() {
		return "/map/MarkerWindow3.xhtml?faces-redirect=true";
	}

	@Override
	public String editDestinations() {
		destinations.setWrappedData(tmpTravel.getTravelDestinations());
		tmpDestination = TravelDestinationFactory.create(tmpTravel);
		return "/map/AddDestination.xhtml?faces-redirect=true";
	}

	/**
	 * Creates a new destination, adds it to a travel and centers the map on it.
	 */
	@Override
	public String addDestination() {
		TravelDAO.addDestination(em, utx, tmpDestination, tmpTravel);
		destinations.setWrappedData(tmpTravel.getTravelDestinations());

		LatLng coord = new LatLng(tmpDestination.getLat(), tmpDestination.getLng());
		advancedModel.addOverlay(new Marker(coord, tmpDestination.getTitle(), tmpDestination.getText(),
				"https://maps.google.com/mapfiles/ms/micons/blue-dot.png"));

		center = centerLast();
		tmpDestination = TravelDestinationFactory.create(tmpTravel);
		return "/map/AddDestination.xhtml?faces-redirect=true";
	}

	/**
	 * Saves the changes of a travel or if it doesn’t exists, creates it.
	 */
	@Override
	public String saveTravel() {
		User user = null;
		if ((user = getProfileRightUser().getUser()) != null) {

			user.getTravels().add(tmpTravel);

			if (tmpTravel.getId() != null) {
				TravelDAO.saveTravel(em, utx, tmpTravel, user);
			} else {
				TravelDAO.addTravel(em, utx, tmpTravel, user);
			}
			travels.setWrappedData(user.getTravels());
			return allTravels();
		}
		return null;
	}

	/**
	 * Deletes the selected travel and if it’s the last deletes the whole travel.
	 */
	@Override
	public String deleteTravel() {
		User user = null;
		if ((user = getProfileRightUser().getUser()) != null) {
			tmpTravel = travels.getRowData();
			TravelDAO.removeTravel(em, utx, tmpTravel, user);
			travels.setWrappedData(user.getTravels());
		}
		return allTravels();
	}

	public String saveDestination() {
		int index = advancedModel.getMarkers().indexOf(marker);
		TravelDestination currentDestination = tmpTravel.getTravelDestinations().get(index);
		currentDestination.setTitle(marker.getTitle());
		currentDestination.setText((String) marker.getData());
		TravelDAO.saveDestination(em, utx, currentDestination);
		return null;

	}

	/**
	 * Deletes the selected destination and if it’s the last deletes the whole
	 * travel.
	 */
	@Override
	public String deleteDestination(boolean mode) {
		TravelDestination deleteDestination;
		if (mode) {
			int index = advancedModel.getMarkers().indexOf(marker);
			deleteDestination = tmpTravel.getTravelDestinations().get(index);
		} else {
			deleteDestination = destinations.getRowData();
		}
		TravelDAO.removeDestination(em, utx, deleteDestination, tmpTravel);
		destinations.setWrappedData(tmpTravel.getTravelDestinations());
		advancedModel = new DefaultMapModel();
		fillModel();
		center = centerLast();
		return "/map/AddDestination.xhtml?faces-redirect=true";

	}

	/**
	 * Fills the Map with all destinations of a Travel.
	 */
	@Override
	public void fillModel() {
		if (tmpTravel.getTravelDestinations() != null) {
			for (TravelDestination tmpDestination : tmpTravel.getTravelDestinations()) {
				LatLng coord = new LatLng(tmpDestination.getLat(), tmpDestination.getLng());
				advancedModel.addOverlay(new Marker(coord, tmpDestination.getTitle(), tmpDestination.getText(),
						"https://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
			}
		}
	}

	/**
	 * Fills the Map with all destinations of all Travel.
	 */
	@Override
	public void fillCompleteModel() {
		if (travels != null) {
			for (Travel tmpTravel : travels) {
				for (TravelDestination tmpDestination : tmpTravel.getTravelDestinations()) {
					LatLng coord = new LatLng(tmpDestination.getLat(), tmpDestination.getLng());
					advancedModel.addOverlay(new Marker(coord, tmpDestination.getTitle(), tmpDestination.getText(),
							"https://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
				}
			}
		}
	}

	/**
	 * Adds or removes all Polylines of all travels depending on the showPolylines
	 * flag.
	 */
	@Override
	public String addRemoveCompletePolylines() {
		showPolylines = !showPolylines;
		if (showPolylines) {
			addCompletePolylines();
		} else {
			return allTravels();
		}
		return null;
	}

	/**
	 * Adds or removes all Polylines of all travels.
	 */
	@Override
	public void addCompletePolylines() {
		for (Travel tmpTravel : travels) {
			polyline = new Polyline();
			polyline.setStrokeWeight(3);
			Random random = new Random();
			int nextInt = random.nextInt(0xffffff + 1);
			polyline.setStrokeColor(String.format("#%06x", nextInt));
			polyline.setStrokeOpacity(0.7);
			for (TravelDestination tmpDestination : tmpTravel.getTravelDestinations()) {
				LatLng coord = new LatLng(tmpDestination.getLat(), tmpDestination.getLng());
				polyline.getPaths().add(coord);
			}
			advancedModel.addOverlay(polyline);
		}
	}

	/**
	 * Adds or remove a Polyline of a travel depending on the showPolylines flag.
	 */
	@Override
	public void addRemovePolylines() {
		showPolylines = !showPolylines;
		if (showPolylines) {
			addPolylines();
		} else {
			showTravel(tmpTravel);
		}
	}

	/**
	 * Adds a Polyline of a travel.
	 */
	@Override
	public void addPolylines() {
		for (Marker marker : advancedModel.getMarkers()) {
			polyline.getPaths().add(marker.getLatlng());
		}
		polyline.setStrokeWeight(3);
		polyline.setStrokeColor("#FFA500");
		polyline.setStrokeOpacity(0.7);

		advancedModel.addOverlay(polyline);
	}

	@Override
	public DataModel<Travel> getTravels() {
		return travels;
	}

	@Override
	public void setTravels(DataModel<Travel> travels) {
		this.travels = travels;
	}

	@Override
	public Travel getTmpTravel() {
		return tmpTravel;
	}

	@Override
	public void setTmpTravel(Travel tmpTravel) {
		this.tmpTravel = tmpTravel;
	}

	@Override
	public Travel getTravel() {
		return tmpTravel;
	}

	@Override
	public void setTravel(Travel travel) {
		tmpTravel = travel;
	}

	@Override
	public TravelDestination getTmpDestination() {
		return tmpDestination;
	}

	@Override
	public DataModel<TravelDestination> getDestinations() {
		return destinations;
	}

	@Override
	public void setDestinations(DataModel<TravelDestination> destinations) {
		this.destinations = destinations;
	}

	@Override
	public void setTmpDestination(TravelDestination tmpDestination) {
		this.tmpDestination = tmpDestination;
	}

	@Override
	public MapModel getAdvancedModel() {
		return advancedModel;
	}

	@Override
	public void setAdvancedModel(MapModel advancedModel) {
		this.advancedModel = advancedModel;
	}

	@Override
	public Marker getMarker() {
		return marker;
	}

	@Override
	public void setMarker(Marker marker) {
		this.marker = marker;
	}

	@Override
	public String getCenter() {
		return center;
	}

	@Override
	public void setCenter(String center) {
		this.center = center;
	}

	@Override
	public Polyline getPolyline() {
		return polyline;
	}

	@Override
	public void setPolyline(Polyline polyline) {
		this.polyline = polyline;
	}

	@Override
	public boolean isShowPolylines() {
		return showPolylines;
	}

	@Override
	public void setShowPolylines(boolean showPolylines) {
		this.showPolylines = showPolylines;
	}
}