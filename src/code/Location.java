package code;

public class Location {
	
	/**
	 * Person object that specifies the type of person at a location
	 */
	
	private Person person;
	
	/**
	 * String that holds the codename of the location
	 */
	
	private String codename;
	
	/**
	 * Boolean value that when is {@code true) shows that the location was revealed and {@code false}
	 * when the location isn't yet revealed
	 */
	
	private boolean isRevealed;
	
	/**
	 * This contructor creates a location object by taking in a string {@code c) and Person {@code p}
	 * 
	 * @param c This String contains the name of the codename that's used to create the location
	 * @param p This Person is used to determine which role is in the location being created
	 */
	
	public Location(String c, Person p) {
		this.person = p;
		this.codename = c;
		setRevealed(false);
	}
	
	/**
	 * This method returns a Person at a location
	 * 
	 * @return A person that's in the location instance that this method was called on
	 */
	
	public Person getPerson()
	{
		return person;
	}
	
	
	/**
	 * This method returns the codename at a location
	 * 
	 * @return A String which specifies the code name in the location instance that this method was
	 * called on
	 */
	
	public String getCodename()
	{
		return codename;
	}

	/**
	 * This method checks if a location is currently revealed or not
	 * 
	 * @return {@code true} if the location is revealed otherwise {@code false}
	 */
	
	public boolean isRevealed() {
		return isRevealed;
	}

	/**
	 * This method sets if a location was revealed or not revealed
	 * 
	 * @param isRevealed This boolean statement can be set to {@code true} or {@code false}
	 * to set the locations visibilty to either true or false.
	 */
	
	public void setRevealed(boolean isRevealed) {
		this.isRevealed = isRevealed;
	}
}
