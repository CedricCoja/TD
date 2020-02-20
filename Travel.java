package user;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * This class models an Travel.
 * 
 * @author Tobias Stelter, Maximilian Werner, Philipp Köhnken
 *
 */
@Entity
public class Travel implements Serializable {
	private static final long serialVersionUID = -1070610092174290859L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	private String title;
	
	private String text;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "travel")
	@NotFound(action=NotFoundAction.IGNORE)
	private List<TravelDestination> travelDestinations;
	
	private boolean publicTravel;
	
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	private User user;
	
	/**
	 * Gets the user which could contain some travels.
	 * 
	 * @return user
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * Sets the user which could contain some travels.
	 * 
	 * @return void
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * Gets the title of a travel.
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the title of a travel.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the description text of a travel.
	 * 
	 * @return text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the description text of a travel.
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Gets the travel destinations of a travel as a LinkedList.
	 * 
	 * @return text
	 */
	public List<TravelDestination> getTravelDestinations() {
		return travelDestinations;
	}

	/**
	 * Sets the travel destinations of a travel as a LinkedList.
	 */
	public void setTravelDestinations(List<TravelDestination> travelDestinations) {
		this.travelDestinations = travelDestinations;
	}
	
	/**
	 * Gets the id of a travel.
	 * 
	 * @return id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Sets the Id (id) for a travel.
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * Gets the isPublic flag of a travel.
	 * 
	 * @return isPublic
	 */
	public boolean isPublicTravel() {
		return publicTravel;
	}

	/**
	 * Sets the isPublic flag for a travel.
	 */
	public void setPublicTravel(boolean publicTravel) {
		this.publicTravel = publicTravel;
	}
	
	@Override
	public int hashCode() {
		final int number = 1997;
		int result = 1;
		result = number * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Travel other = (Travel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}