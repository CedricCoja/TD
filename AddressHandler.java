package handler.addresshandler;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.primefaces.event.RowEditEvent;

import user.Address;
import user.AddressDAO;
import user.AddressFactory;
import user.User;

/**
 * This class is a Management Bean. This class manages addresses from a User.
 * 
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
public class AddressHandler extends AddressHandlerBasic {
	@PersistenceContext
	private EntityManager em;
	@Resource
	private UserTransaction utx;
	private DataModel<Address> addresses;
	private Address tmpAddress;

	@Override
	public String delete() {
		User user = null;
		if ((user = getProfileRightUser().getUser()) != null) {
			tmpAddress = addresses.getRowData();
			AddressDAO.delete(em, utx, tmpAddress, user);
			addresses.setWrappedData(user.getAddresses());
		}
		return "addresses.xhtml?faces-redirect=true";
	}

	@Override
	public String save() {
		User user = null;
		if ((user = getProfileRightUser().getUser()) != null) {
			AddressDAO.save(em, utx, tmpAddress, user);
			addresses.setWrappedData(user.getAddresses());
			return cancel();
		}
		return cancel();
	}

	@Override
	public String add() {
		User user = null;
		getProfileRightUser().reloadUser(em);
		if ((user = getProfileRightUser().getUser()) != null) {
			AddressDAO.add(em, utx, tmpAddress, user);
			AddressDAO.save(em, utx, tmpAddress, user);
			addresses.setWrappedData(user.getAddresses());
			return cancel();
		}
		return cancel();
	}

	@Override
	public String newAddress() {
		tmpAddress = AddressFactory.create(getProfileRightUser().getUser());
		if (tmpAddress != null) {
			return "address.xhtml?faces-redirect=true";
		}
		return "address.xhtml?faces-redirect=true";
	}

	@Override
	public String edit() {
		tmpAddress = addresses.getRowData();
		return "address.xhtml?faces-redirect=true";
	}

	@Override
	public String cancel() {
		return "addresses.xhtml?faces-redirect=true";
	}

	@Override
	public String back() {
		return "/profil/showprofil.xhtml";
	}

	@Override
	public DataModel<Address> getAddresses() {
		return addresses;
	}

	@Override
	public void setAddresses(DataModel<Address> addresses) {
		this.addresses = addresses;
	}

	@Override
	public Address getAddress() {
		return tmpAddress;
	}

	@Override
	public void setAddress(Address address) {
		tmpAddress = address;
	}

	@Override
	public void onRowEdit(RowEditEvent event) {
		tmpAddress = ((Address) event.getObject());
		this.save();
		FacesMessage msg = new FacesMessage("Address of " + tmpAddress.getUser().getUsername() + " Edited");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}