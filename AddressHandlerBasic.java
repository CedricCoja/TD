package handler.addresshandler;

import javax.faces.model.DataModel;

import org.primefaces.event.RowEditEvent;

import user.Address;
import user.ProfileRightUser;

public abstract class AddressHandlerBasic {

	private ProfileRightUser profileRightUser;

	public abstract String delete();

	public abstract String save();

	public abstract String newAddress();

	public abstract String edit();

	public abstract void setAddress(Address address);

	public abstract Address getAddress();

	public abstract void setAddresses(DataModel<Address> addresses);

	public abstract DataModel<Address> getAddresses();

	public abstract String back();

	public abstract String cancel();
	
	public abstract void onRowEdit(RowEditEvent event);

	public ProfileRightUser getProfileRightUser() {
		return profileRightUser;
	}

	public void setProfileRightUser(ProfileRightUser profileRightUser) {
		this.profileRightUser=profileRightUser;		
	}

	public abstract String add();
}
