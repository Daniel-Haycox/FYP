package model;

public class Customer implements Comparable<Customer> {

	private String name;
	private String street;
	private String city;
	private String postcode;
	
	/**
	 * empty customer constructor
	 */
	public Customer() {
		
		name = "";
		street = "";
		city = "";
		postcode = "";
		
	}
	
	/**
	 * full customer constructor
	 * @param name customers name
	 * @param street customers street
	 * @param city customers city
	 * @param postcode customers postcode
	 */
	public Customer(String name, String street, String city, String postcode) {
		
		this.name = name;
		this.street = street;
		this.city = city;
		this.postcode = postcode;
	}

	
	
	/**
	 * @return returns the name variable
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name sets the name variable
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return returns the street variable
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street sets the street variable
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return returns the city variable
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city sets the city variable
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return returns the postcode variable
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * @param postcode sets the postcode variable
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	public int compareTo(Customer other) {
		return this.getName().compareTo(other.getName());
	}
	
	@Override
	public String toString() {
		return this.name;
		
	}
	
	/**
	 * traditional toString
	 * @return returns the traditional toString format
	 */
	public String actualToString() {
		return "[name=" + name + ", street=" + street + ", city=" + city + ", postcode=" + postcode + "]";
	}
	
}
