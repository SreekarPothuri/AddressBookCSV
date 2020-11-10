package blz.addressbook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import blz.addressbook.*;

public class AddressBook {

	public static List<Person> personList = new ArrayList<Person>();
	static Scanner sc = new Scanner(System.in);
	static String addressBookName;
	Person newEntry;
	boolean isExist;
	static int choice = 0;

	public AddressBook(List<Person> personList) {
		super();
		this.personList = personList;
	}

	public AddressBook() {
	}

	private void addPerson(String addressBookName) {
		isExist = false;
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

		if (personList.size() > 0) {
			for (Person details : personList) {
				newEntry = details;
				if (firstName.equals(newEntry.firstName) && lastName.equals(newEntry.lastName)) {
					System.out.println("Contact " + newEntry.firstName + " " + newEntry.lastName + " already exists");
					isExist = true;
					break;
				}
			}
		}
		if (!isExist) {
			newEntry = new Person(firstName, lastName, address, city, state, mobileNum, zipCode);
			personList.add(newEntry);
			addDataToFile(firstName, lastName, address, city, state, mobileNum, zipCode, addressBookName);
			try {
				addDataToCSVFile(addressBookName);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				addDataToJSONFile(addressBookName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
				addPerson(addressBookName);
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

	public void sortByCity() {
		Comparator<Person> nameComparator = Comparator.comparing(Person::getCity);
		List<Person> sortedList = new ArrayList<Person>();
		sortedList = personList.stream().sorted(nameComparator).collect(Collectors.toList());
		sortedList.stream().forEach(i -> System.out.println(i));
	}

	public void sortByState() {
		Comparator<Person> nameComparator = Comparator.comparing(Person::getState);
		List<Person> sortedList = new ArrayList<Person>();
		sortedList = personList.stream().sorted(nameComparator).collect(Collectors.toList());
		sortedList.stream().forEach(i -> System.out.println(i));
	}

	public void sortByZip() {
		Comparator<Person> nameComparator = Comparator.comparing(Person::getZipCode);
		List<Person> sortedList = new ArrayList<Person>();
		sortedList = personList.stream().sorted(nameComparator).collect(Collectors.toList());
		sortedList.stream().forEach(i -> System.out.println(i));
	}

	public void addDataToFile(String firstName, String lastName, String address, String city, String state,
			String phoneNumber, long zip, String addressBookName) {
		System.out.println("Enter name for txt written file : ");
		String fileName = sc.next();
		File file = new File("F:\\BridgeLabz Fellowship Program\\FilesIncluded\\" + fileName + ".txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Contact:" + "\n1.First name: " + firstName + "\n2.Last name: " + lastName + "\n3.Address: "
					+ address + "\n4.City: " + city + "\n5.State: " + state + "\n6.Phone number: " + phoneNumber
					+ "\n7.Zip: " + zip + "\n");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readDataFromFile() {
		System.out.println("Enter address book name : ");
		String fileName = sc.nextLine();
		Path filePath = Paths.get("F:\\BridgeLabz Fellowship Program\\FilesIncluded" + fileName + ".txt");
		try {
			Files.lines(filePath).map(line -> line.trim()).forEach(line -> System.out.println(line));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addDataToCSVFile(String addressBookName) throws IOException {
		System.out.println("Enter name for csv written file : ");
		String fileName = sc.next();
		Path filePath = Paths.get("F:\\BridgeLabz Fellowship Program\\FilesIncluded\\" + fileName + ".csv");

		if (Files.notExists(filePath))
			Files.createFile(filePath);
		File file = new File(String.valueOf(filePath));

		try {
			FileWriter outputfile = new FileWriter(file, true);
			CSVWriter writer = new CSVWriter(outputfile);
			List<String[]> data = new ArrayList<>();
			for (Person detail : personList) {
				data.add(new String[] { "Contact:" + "\n1.First name: " + detail.firstName + "\n2.Last name: "
						+ detail.lastName + "\n3.Address: " + detail.address + "\n4.City: " + detail.city
						+ "\n5.State: " + detail.state + "\n6.Phone number: " + detail.mobileNum + "\n7.Zip: "
						+ detail.zipCode + "\n" });
			}
			writer.writeAll(data);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readDataFromCSVFile() {
		System.out.println("Enter address book name : ");
		String fileName = sc.next();
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader("F:\\BridgeLabz Fellowship Program\\FilesIncluded\\" + fileName + ".csv"));
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				for (String token : nextLine) {
					System.out.println(token);
				}
				System.out.print("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addDataToJSONFile(String addressBookName) throws IOException {
		System.out.println("Enter name for json written file : ");
		String fileName = sc.nextLine();
		Path filePath = Paths.get("E:\\Fellowship\\STS\\File\\" + fileName + ".json");
		Gson gson = new Gson();
		String json = gson.toJson(personList);
		FileWriter writer = new FileWriter(String.valueOf(filePath));
		writer.write(json);
		writer.close();
	}

	public void readDataFromJSONFile() throws FileNotFoundException {
		System.out.println("Enter address book name : ");
		String fileName = sc.nextLine();
		Path filePath = Paths.get("E:\\Fellowship\\STS\\File\\" + fileName + ".json");
		Gson gson = new Gson();
		BufferedReader br = new BufferedReader(new FileReader(String.valueOf(filePath)));
		Person[] data = gson.fromJson(br, Person[].class);
		List<Person> personList = Arrays.asList(data);
		for (Person details : personList) {
			System.out.println("Firstname : " + details.firstName);
			System.out.println("Lastname : " + details.lastName);
			System.out.println("Address : " + details.address);
			System.out.println("City : " + details.city);
			System.out.println("State : " + details.state);
			System.out.println("Zip : " + details.zipCode);
			System.out.println("Phone no : " + details.mobileNum);
		}
	}

	public static void main(String args[]) throws IOException {
		AddressBook contact = new AddressBook();
		System.out.println("*****WELCOME TO ADDRESS BOOK PROGRAM*****");

		do {
			System.out.println("1.Add Person\n2.Print contact details\n3.Edit contact details\n"
					+ "4.Delete contact details\n5.Add another Person\n6.Search By City\n7.View By City\n"
					+ "8.Count Based On City\n9.Sort by FirstName\n10.Sort By City\n11.Sort By State\n"
					+ "12.Sort By ZipCode\n13.Add to text File\n14.Read From Text File\n15.Add to CSV File\n16.Read from CSV file\n17.Add Data to JSON File\n18.Read from JSON File\n19.Exit");
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				contact.addPerson(addressBookName);
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
				contact.addPerson(addressBookName);
				System.out.println("Added Successfully to text file!!");
				break;
			case 14:
				contact.readDataFromFile();
				break;
			case 15:
				contact.addPerson(addressBookName);
				System.out.println("Added Successfully to csv file!!");
				break;
			case 16:
				contact.readDataFromCSVFile();
				break;
			case 17:
				contact.addPerson(addressBookName);
				System.out.println("Added Successfully to JSON file!!");
				break;
			case 18:
				contact.readDataFromJSONFile();
				break;
			case 19:
				System.out.println("Exited Successfully!!");
				break;
			default:
				System.out.println("Error! Choose right option from the above given options Only");
			}
		} while (choice != 19);
	}
}