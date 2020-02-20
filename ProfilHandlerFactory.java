package handler.profilhandler;

import user.ProfileRightUser;

public class ProfilHandlerFactory {
	private ProfilHandlerFactory() {

	}

	public static void setProfilHandlerValues(ProfilHandlerBasic basicProfilHandler,
			ProfileRightUser profileRightUser) {
		basicProfilHandler.setProfileRightUser(profileRightUser);
	};
}
