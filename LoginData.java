package user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * This class contains Login informations for every User
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
@Entity
public abstract class LoginData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4982943306163949808L;
	public static final String STANDARD_HASH = "MD5";
	public static final String STRING_CODING = "UTF-8";
	public static final int STANDARD_LENGTH_ADD_TO = 255;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	public abstract boolean testPassword(String input);

	public abstract void setpassword(String newpassword);

	public abstract boolean setpassword(String newpassword, String password);

	public final Integer getId() {
		return id;
	}

	public final void setId(Integer id) {
		this.id = id;
	}
}
