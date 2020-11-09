package blz.addressbook;

import com.opencsv.bean.CsvBindByName;

public class Person {
	@CsvBindByName
	public String firstName;
	@CsvBindByName
	public String lastName;
	@CsvBindByName
	public String address;
	@CsvBindByName
	public String city;
	@CsvBindByName
	public String state;
	@CsvBindByName
	public String mobileNum;
	@CsvBindByName
	public long zipCode;

	public Person(String firstName, String lastName, String address, String city, String state, String mobileNum,
			long zipCode) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.mobileNum = mobileNum;
		this.zipCode = zipCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public long getZipCode() {
		return zipCode;
	}

	public void setZipCode(long zipCode) {
		this.zipCode = zipCode;
	}

	public String toString() {
		return "Name: " + firstName + " " + lastName + "\nAddress: " + address + "\nCity: " + city + "\nState: " + state
				+ "\nPin Code: " + zipCode + "\nMobile Number: " + mobileNum;

	}
}
