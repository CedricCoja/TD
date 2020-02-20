package user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Future;



/**
 * This class models the payment data of a user.
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *  
 */
@Entity
public class PayInfo implements Serializable {

	private static final long serialVersionUID = -160524145103789490L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	private PayInfoTyp payInfoTyp;
	private String owner;
	@Future
	private Date dateOfExpiry;
	private String number;

	public PayInfoTyp getPayInfoTyp() {
		return payInfoTyp;
	}

	public void setPayInfoTyp(PayInfoTyp payInfoTyp) {
		this.payInfoTyp = payInfoTyp;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Date getDateOfExpiry() {
		return dateOfExpiry;
	}

	public void setDateOfExpiry(Date dateOfExpiry) {
		this.dateOfExpiry = dateOfExpiry;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}