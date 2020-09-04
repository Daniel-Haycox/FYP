package model;

public class Owner {

	private String name;
	private String street;
	private String city;
	private String postcode;
	private String phone;
	private String days;
	
	public Owner() {
		
		name = "";
		street = "";
		city = "";
		postcode = "";
		phone = "";
		days = "";
		
	}
	
	/**
	 * full owner constructor
	 * @param name owners business name
	 * @param street owners business address street
	 * @param city owners business address city
	 * @param postcode owners business address postcode
	 * @param phone owners business address phone
	 * @param days days until an invoice is due after issuing
	 */
	public Owner(String name, String street, String city, String postcode, String phone, String days) {
		
		this.name = name;
		this.street = street;
		this.city = city;
		this.postcode = postcode;
		this.phone = phone;
		this.days = days;
	}

	
	
	/**
	 * returns the name variable
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets the name variable
	 * @param name name string to be set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * returns the name variable
	 * @return
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * sets the street variable
	 * @param street string to be set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * returns the city variable
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * sets the city variable
	 * @param city string to be set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * returns the postcode variable
	 * @return
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * sets the postcode variable
	 * @param postcode string to be set
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	/**
	 * gets the phone number variable
	 * @return
	 */
	public String getPhone() {
		return phone;
	}
	
	/**
	 * sets the phone number variable
	 * @param phone string to be set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
	 * returns the days variable
	 * @return
	 */
	public String getDays() {
		return days;
	}
	
	/**
	 * sets the days variable
	 * @param days number of days till invoice is due
	 */
	public void setDays(String days) {
		this.days = days;
	}
	
	@Override
	public String toString() {
		return this.name;
		
	}
	
	/**
	 * returns the traditional toString format
	 * @return
	 */
	public String actualToString() {
		return "[name=" + name + ", street=" + street + ", city=" + city + ", postcode=" + postcode + ", phone=" + phone + ", days=" + days + "]";
	}
	
}
