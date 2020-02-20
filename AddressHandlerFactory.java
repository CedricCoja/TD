package handler.addresshandler;

import javax.faces.model.CollectionDataModel;
import javax.faces.model.DataModel;

import user.Address;
import user.ProfileRightUser;


public class AddressHandlerFactory {
	private AddressHandlerFactory() {

	}

	public static void setAddressHandlerValues(AddressHandlerBasic basicAddressHandler,
			ProfileRightUser profileRightUser) {
		basicAddressHandler.setProfileRightUser(profileRightUser);
		DataModel<Address> adresses = new CollectionDataModel<Address>();
		adresses.setWrappedData(profileRightUser.getAddresses());
		basicAddressHandler.setAddresses(adresses);
	};
}
