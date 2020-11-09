package blz.addressbook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import blz.addressbook.*;

public class AddressBook {

	public static List<Person> personList = new ArrayList<Person>();
	static Scanner sc = new Scanner(System.in);
	AddressBookFileIOService addressBookIO = new AddressBookFileIOService();

	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}

	static int choice = 0;

	public AddressBook(List<Person> personList) {
		super();
		this.personList = personList;
	}

	public AddressBook() {}

	private void addPerson() {
		System.out.println("Enter Person Details");
		System.out.println("Enter FirstName: ");
		String firstName = sc.next();
		System.out.println("Enter lastName: ");
		String lastName = sc.next();
		System.out.println("Enter Address: ");
		String address = sc.next();
		System.out.println("Enter City: ");
		String city = sc.next();
		System.out.println("Enter State: ");
		String state = sc.next();
		System.out.println("Enter Mobile Number: ");
		String mobileNum = sc.next();
		System.out.println("Enter zipCode: ");
		long zipCode = sc.nextLong();

		Person person = new Person(firstName, lastName, address, city, state, mobileNum, zipCode);
		personList.add(person);
		System.out.println("Person details added successfully");
	}

	private void editContact() {
		if (personList.isEmpty()) {
			System.out.println("There are no contacts to print");
		} else {
			String address, city, state, mobileNum;
			long zipCode;
			int id;
			for (Person contact : personList) {
				System.out.println("ID " + personList.indexOf(contact) + ":\n" + contact);
			}
			System.out.println("Enter ID of contact to edit: ");
			id = sc.nextInt();
			System.out.println(personList.get(id));
			System.out.println(
					"Please select the option to edit\n1.Address\n2.City\n3.State\n4.zipCode\n5.Mobile Number");
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				System.out.println("Enter Address: ");
				address = sc.next();
				personList.get(id).setAddress(address);
				break;
			case 2:
				System.out.println("Enter City: ");
				city = sc.next();
				personList.get(id).setCity(city);
				break;
			case 3:
				System.out.println("Enter State: ");
				state = sc.next();
				personList.get(id).setState(state);
				break;
			case 4:
				System.out.println("Enter Zip Code: ");
				zipCode = sc.nextLong();
				personList.get(id).setZipCode(zipCode);
				break;
			case 5:
				System.out.println("Enter Mobile Number: ");
				mobileNum = sc.next();
				personList.get(id).setMobileNum(mobileNum);
				break;
			default:
				System.out.println("Error!! Choose correct Option");
				editContact();
			}
		}
	}

	private void printContact() {
		if (personList.isEmpty()) {
			System.out.println("There are no contacts to print");
		} else {
			for (Person contact : personList) {
				System.out.println(contact);
			}
		}
	}

	private void deleteContact() {
		if (personList.isEmpty()) {
			System.out.println("There are no contacts to delete in the addressbook");
		} else {
			System.out.println("Enter firstname to delete the person");
			String firstName = sc.next();
			for (int count = 0; count < personList.size(); count++) {
				if (personList.get(count).getFirstName().equals(firstName)) {
					personList.remove(personList.get(count));
				}
			}
			System.out.println("Person details deleted successfully");
		}
	}

	private void addMultiplePerson() {
		System.out.println("Enter how many contacts you want to add: ");
		int numofContacts = sc.nextInt();
		int createdContacts = 1;
		while (createdContacts <= numofContacts) {
			if ((addressBookWithUniqueName() == true) && (noDuplicateEntry() == true)) {
				addPerson();
			}
			createdContacts++;
		}
	}

	private boolean addressBookWithUniqueName() {
		System.out.println("FirstName of a person is referred to as AddressBookName");
		System.out.println("Enter AddressBook Name to check Uniqueness");
		String firstName = sc.next();
		boolean result = personList.stream().allMatch(n -> n.getFirstName().equals(firstName));
		if (result == true) {
			System.out.println("Already an AddressBook exist with this name");
			return false;
		} else {
			return true;
		}
	}

	private boolean noDuplicateEntry() {
		System.out.println("Enter your First Name to check Duplicate Entry");
		String name = sc.next();
		boolean result = personList.stream().allMatch(n -> n.getFirstName().equals(name));
		if (result == true) {
			System.out.println("Already an AddressBook exist with this name");
			return false;
		} else {
			return true;
		}
	}

	private void searchByCity() {
		System.out.println("Enter city name");
		String city = sc.next();
		personList.stream().filter(n -> n.getCity().equals(city)).forEach(n -> System.out.println(n.firstName));
	}

	private void viewByCity() {
		System.out.println("Enter city name");
		String city = sc.next();
		personList.stream().filter(n -> n.getCity().equals(city)).forEach(n -> System.out.println(n));
	}

	private void countBasedOnCity() {
		System.out.println("Enter city name");
		String city = sc.next();
		long cityCount = personList.stream().filter(n -> n.getCity().equals(city)).count();
		if (cityCount > 0)
			System.out.println("Number of persons in " + city + " is " + cityCount);
		else
			System.out.println("No person from " + city + " exist");
	}

	public void sortByFirstName() {
		Comparator<Person> nameComparator = Comparator.comparing(Person::getFirstName);
		List<Person> sortedList = new ArrayList<Person>();
		sortedList = personList.stream().sorted(nameComparator).collect(Collectors.toList());
		sortedList.stream().forEach(i -> System.out.println(i));
	}
	
	public void sortByCity(){
        Comparator<Person> nameComparator = Comparator.comparing(Person::getCity);
		List<Person> sortedList = new ArrayList<Person>();
		sortedList = personList.stream().sorted(nameComparator).collect(Collectors.toList());
		sortedList.stream().forEach(i -> System.out.println(i));
	}
	
	public void sortByState(){
        Comparator<Person> nameComparator = Comparator.comparing(Person::getState);
		List<Person> sortedList = new ArrayList<Person>();
		sortedList = personList.stream().sorted(nameComparator).collect(Collectors.toList());
		sortedList.stream().forEach(i -> System.out.println(i));
	}
	
	public void sortByZip(){
        Comparator<Person> nameComparator = Comparator.comparing(Person::getZipCode);
		List<Person> sortedList = new ArrayList<Person>();
		sortedList = personList.stream().sorted(nameComparator).collect(Collectors.toList());
		sortedList.stream().forEach(i -> System.out.println(i));
	}
	
	public static void writeAddressBookData(IOService ioService) {
		if (ioService.equals(AddressBook.IOService.CONSOLE_IO))
			System.out.println("Employee Payroll to Details : " + personList);
		if (ioService.equals(AddressBook.IOService.FILE_IO))
			new AddressBookFileIOService().writeData(personList);
	}

	public void readDataFromFile() {
		System.out.println("Enter address book name: ");
		String addressBookFile = sc.nextLine();
		Path filePath = Paths.get("F:\\BridgeLabz Fellowship Program\\practice\\AddressBookSystem\\" + addressBookFile + ".txt");
		try {
			Files.lines(filePath).map(line -> line.trim()).forEach(line -> System.out.println(line));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		AddressBook contact = new AddressBook();
		System.out.println("*****WELCOME TO ADDRESS BOOK PROGRAM*****");

			do	{
			System.out.println("1.Add Person\n2.Print contact details\n3.Edit contact details\n"
					+ "4.Delete contact details\n5.Add another Person\n6.Search By City\n7.View By City\n"
					+ "8.Count Based On City\n9.Sort by FirstName\n10.Sort By City\n11.Sort By State\n"
					+ "12.Sort By ZipCode\n13.Exit");
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				contact.addPerson();
				break;
			case 2:
				contact.printContact();
				continue;
			case 3:
				contact.editContact();
				break;
			case 4:
				contact.deleteContact();
				break;
			case 5:
				contact.addMultiplePerson();
				break;
			case 6:
				contact.searchByCity();
				break;
			case 7:
				contact.viewByCity();
				break;
			case 8:
				contact.countBasedOnCity();
				break;
			case 9:
				contact.sortByFirstName();
				break;
			case 10:
				contact.sortByCity();
				break;
			case 11:
				contact.sortByState();
				break;
			case 12:
				contact.sortByZip();
				break;
			case 13:
				System.out.println("Exited Successfully!!");
				break;
			default:
				System.out.println("Error! Choose right option from the above given options Only");
			}
		}while (choice != 13);
	}
}
