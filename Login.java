package user;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Entity;

/**
 * This class models a login of a user.
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
@Entity
public class Login extends LoginData {

	private static final long serialVersionUID = -3565987182823353988L;
	private String addToPassword;
	private byte[] hashPassword;
	private String hashMethode;

	/**
	 * Creates a new Login Object without a password
	 * 
	 *
	 */
	
	public Login() {
		this(null);
	}

	/**
	 * Creates a new Login Object
	 * 
	 *
	 */
	
	public Login(String password) {
		StringBuilder buildAddToPassword = new StringBuilder(LoginData.STANDARD_LENGTH_ADD_TO);
		for (int i = 0; i < LoginData.STANDARD_LENGTH_ADD_TO; ++i) {
			buildAddToPassword.append((char) (Math.random() * Short.MAX_VALUE));
		}
		addToPassword = buildAddToPassword.toString();
		hashMethode = LoginData.STANDARD_HASH;
		if (password != null) {
			setpassword(password);
		}
	}

	/**
	 * Test if the given input matches the hash password
	 * 
	 * @return boolean
	 */
	@Override
	public boolean testPassword(String input) {
		if (hashPassword == null || !match(hashPassword, hashPassword(input))) {
			return false;
		}
		if (!hashMethode.equals(LoginData.STANDARD_HASH)) {
			hashMethode = LoginData.STANDARD_HASH;
			hashPassword = hashPassword(input);
		}
		return true;
	}

	private boolean match(byte[] match, byte[] with) {
		for (int i = 0; i < match.length; ++i) {
			if (match[i] != with[i]) {
				return false;
			}
		}
		return true;
	}

	private byte[] hashPassword(String input) {
		try {
			MessageDigest use = MessageDigest.getInstance(hashMethode);
			return use.digest((input + addToPassword).getBytes(LoginData.STRING_CODING));
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	/**
	 * Set a new password
	 * 
	 * @return void
	 */
	@Override
	public void setpassword(String newpassword) {
		setpassword(newpassword, null);
	}

	/**
	 * Set a new password and match with the given old password
	 * 
	 * @return boolean
	 */
	@Override
	public boolean setpassword(String newpassword, String password) {
		if (hashPassword == null || match(hashPassword, hashPassword(password))) {
			hashPassword = hashPassword(newpassword);
			return true;
		}
		return false;
	}
}