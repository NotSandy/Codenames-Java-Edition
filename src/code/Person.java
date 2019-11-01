package code;

public class Person {

	/**
	 * String that contains the role of the person
	 */
	
	private String role;

	/**
	 * This is the contructor used to create a person object by taking in a String as parameter
	 * @param r Specifies the role of the person object being created
	 */
	
	public Person(String r) {
		this.role = r; // Sets role
	}
	
	/**
	 * This method sets the role of the person object it's called on
	 * @param role Specifies the role of the person object being created
	 */
	
	public void setRole(String role) {
		this.role = role;
	}
	
	/**
	 * This method retuns the role of the person object it's called on
	 * @return A string which specifies the role of the person (Blue, Red, Assassin, Bystander)
	 */
	
	public String getRole()
	{
		return role;
	}
}
