package librarymanagement;

import java.util.*;

public class Librarian {
	static HashMap<String, User> All_users = new HashMap<>();
	public static HashMap<String, Category> All_cat = new HashMap<>();
	public static ArrayList<Book> RentedBooks = new ArrayList<>();
	static int FORDAY1 = 1, FORDAY2 = 5, FORDAY3 = 10, FORDAY4 = 50, DUE = 7;
	Date current_date = new Date();
	String name = "Mohan", password = "pass";
	Scanner input = new Scanner(System.in);

	public String setCategory() {
		Boolean exit = false;
		String cat;
		System.out.println("Enter the name of the new category :");
		cat = input.nextLine();
		if (All_cat.get(cat) != null) {
			System.out.println("Category \'" + cat + "\' is already exists!");
			return null;
		} else if (!cat.isEmpty()) {
			//input.nextLine();
			System.out.println("The given category is not exists! Are you want to create new one? (yes-1,no-0)");
			int option = input.nextInt();
			input.nextLine();
			exit = option == 1 ? false : true;
			if (!exit) {
				All_cat.put(cat, new Category(cat));
				System.out.println("Category '" + cat + "' is created.");
			}
			return cat;
		} else {
			System.out.println("Empty string is not acceptable!");
			return cat;
		}
	}

	public static void setCategory(String s) {
		All_cat.put(s, new Category(s));
	}

	public static Category getCategory(String title) {
		return All_cat.get(title);
	}

	public void display() {
		System.out.println("All categories");
		int i = 1;
		for (String key : All_cat.keySet()) {
			System.out.println(i++ + ")" + key);
		}
	}

	public Boolean check_entry(String l_name, String pass) {
		return (l_name.equals(name) && pass.equals(password));
	}

	public void add_Books() {
		System.out.println("How many books do you want to add?");
		int no_of_books = input.nextInt();
		input.nextLine();
		String temp_category_name = "";
		if (no_of_books > 0) {
			display();
			int i = 1;
			System.out.println("Enter a number to select the category : ");
			int catNum = input.nextInt();
			input.nextLine();
			for (String catName : All_cat.keySet()) {
				if (i++ == catNum) {
					temp_category_name = catName;
					break;
				}
			}

			if (temp_category_name.isEmpty()) {
				System.out.println("Invalid option!");
				return;
			}

			if (All_cat.containsKey(temp_category_name)) {
				System.out.println("Enter the Title of the book:");
				String title = input.nextLine();
				if (!title.isEmpty()) {
					System.out.println("Enter the Author name of the book:");
					String author = input.nextLine();
					if (!author.isEmpty()) {
						if (getCategory(temp_category_name).get_book(title) != null) {
							System.out.println(title
									+ " is already added in this category. Are you want to increase the quantity?(yes - 1/no - 0)");

							Boolean yes_no = input.nextInt() != 1 ? false : true;
							input.nextLine();

							if (yes_no) {
								getCategory(temp_category_name).get_book(title).increase(no_of_books);
								System.out.println("Books are added to the category.");
							} else {
								return;
							}
						} else {
							getCategory(temp_category_name)
									.add_Books(new Book(title, author, temp_category_name, no_of_books));
							System.out.println("Books are added to the category.");
						}
					} else {
						System.out.println("Empty string is not acceptable!");
					}

				} else {
					System.out.println("Empty string is not acceptable!");
				}
			} else if (!temp_category_name.isEmpty()) {
				System.out.println("Given category is not available so creating a new category with this name....");
				System.out.println("Enter the Title of the book:");
				String title = input.nextLine();
				if (title.isEmpty()) {
					System.out.println("Empty string is not acceptable!");
					return;
				}
				System.out.println("Enter the Author name of the book:");
				String author = input.nextLine();
				if (author.isEmpty()) {
					System.out.println("Empty string is not acceptable!");
					return;
				}
				setCategory(temp_category_name);
				getCategory(temp_category_name).add_Books(new Book(title, author, temp_category_name, no_of_books));
				System.out.println("Books are added to the category.");
			} else {
				System.out.println("Empty string is not acceptable!");
			}
		} else {
			System.out.println("Invalid value!");
		}

	}

	public void Edit_quantity() {
		System.out.println("Enter the book title:");
		String title = input.nextLine();
		for (String categories : All_cat.keySet()) {
			if ((getCategory(categories)).get_book(title) != null) {
				System.out.println("Enter the number to increase the quantity of the book:");
				getCategory(categories).get_book(title).increase(input.nextInt());
				input.nextLine();
				System.out.println("Quantity of the book is changed.");
				return;
			}
		}
		System.out.println("\'" + title + "\' is not found!");
	}

	public void Delete_books() {
		System.out.println("Enter the book title:");
		String title = input.nextLine();
		for (String categories : All_cat.keySet()) {
			if ((getCategory(categories)).get_book(title) != null) {
				Book temp = ((getCategory(categories)).get_book(title));
				System.out.println("\'" + title + "\' is found!");
				if (temp.initial_Quantity == temp.current_Quantity) {
					(getCategory(categories)).remove_book(title);
					System.out.println("All books with this title are deleted.");
					return;
				} else {
					System.out.println("All books are not returned! So you can't delete a book!");
					return;
				}
			}
		}
		System.out.println("\'" + title + "\' is not found!");
		System.out.println();
	}

	public void Add_user() {
		System.out.println("Enter the user name:");
		String uname = input.nextLine();
		if(uname.isEmpty()){
			System.out.println("Empty string not accepted!");
			return;
		}
		if (All_users.containsKey(uname)) {
			System.out.println("This name is already registered!");
			return;
		}
		System.out.println("Enter the initial password:");
		String upass = input.nextLine();
		if(upass.isEmpty()){
			System.out.println("Empty string not accepted!");
			return;
		}
		All_users.put(uname, new User(uname, upass));
		System.out.println("New user added successfully.");
	}

	public void show() {
		for (String categories : All_cat.keySet()) {
			getCategory(categories).display();
		}
	}

	public void updateDateAndCalculateFine() {
		change_date();
		calculateFine();
	}

	public void getDefaultersList() {
		defaultersList();
	}

	public void setPenality() {
		int temp;
		System.out.println("Current penalty amounts are....");
		System.out.println("For Day 1 : " + FORDAY1);
		System.out.println("For Day 2 : " + FORDAY2);
		System.out.println("For Day 3 : " + FORDAY3);
		System.out.println("For Day 4 or above : " + FORDAY4);
		System.out.println(
				"[NOTE:If you don't want to change value for a certain day, then enter any negative number.]");
		System.out.println("Enter the fine values:");
		System.out.println("Enter the fine value for 'Day ONE':");
		temp = input.nextInt();
		input.nextLine();
		if (temp > 0)
			FORDAY1 = temp;
		System.out.println("Enter the fine value for 'Day TWO':");
		temp = input.nextInt();
		input.nextLine();
		if (temp > 0)
			FORDAY2 = temp;
		System.out.println("Enter the fine value for 'Day THREE':");
		temp = input.nextInt();
		input.nextLine();
		if (temp > 0)
			FORDAY3 = temp;
		System.out.println("Enter the fine value for 'Day FOUR':");
		temp = input.nextInt();
		input.nextLine();
		if (temp > 0)
			FORDAY4 = temp;
		System.out.println("CHANGED!\n");
		//input.nextLine();
		calculateFine();
	}

	public void UpdateFineAmountAndBookReturnStatus() {
		//input.nextLine();
		System.out.println("Enter the user name:");
		String name = input.nextLine();
		User user = All_users.get(name);
		if (All_users.get(name) != null) {
			System.out.println("Enter your choice:");
			System.out.println("1)Return and pay only for the specific book.");
			System.out.println("2)Return all the books and pay all amount.");
			int choice = input.nextInt();
			input.nextLine();
			if (choice == 1) {
				user.showRentedBooks();
				System.out.println("Enter the book number to return :");
				int no = input.nextInt();
				input.nextLine();
				int i = 1;
				String title = "";
				Book temp = null;
				for (Book book : user.My_books) {
					if (i++ == no) {
						title = book.title;
						temp = book;
					}
				}
				if (temp == null) {
					System.out.println("Check the number of the book.");
					return;
				}
				int penalty = temp.getPenalty();
				System.out.println("Do you want to pay Rs." + penalty + "?(yes-1,no-0)");
				Boolean readyToPay = input.nextInt() == 1 ? true : false;
				input.nextLine();
				if (readyToPay) {
					System.out.println("You successfully paid.");
					if (user.getTotalPenality() < 100) {
						user.isapproved = true;
					}
					user.removeBook(title);
				}

			} else if (choice == 2) {
				user.returnAll();
				user.isapproved = true;
				System.out.println("You successfully paid. All books are returned.");
			} else {
				System.out.println("Invalid choice!");
			}

		}
	}

	public Boolean change_date() {
		Boolean val = current_date.change_date();
		for (String name : All_users.keySet()) {
			if (All_users.get(name).isRented) {
				User user = All_users.get(name);
				for (Book book : user.My_books) {
					user.calc_penality(book);
				}
			}
		}
		return val;
	}

	public static void calculateFine() {
		for (String name : All_users.keySet()) {
			if (All_users.get(name).isRented) {
				User user = All_users.get(name);
				for (Book book : user.My_books) {
					user.calc_penality(book);
				}
				if (user.totalPenality >= 100)
					user.isapproved = false;
			}
		}
	}

	public void show_current_date() {
		System.out.println(
				"Current Date(dd/mm/yyyy) :" + current_date.day + "/" + current_date.month + "/" + current_date.year);
	}

	public User find_user(String name) {
		return (All_users.get(name));
	}

	public static void defaultersList() {
		System.out.println("|\tUser names\t|\tPenality amount\t|");
		for (String name : All_users.keySet()) {
			if (All_users.get(name).isRented) {
				User user = All_users.get(name);
				int penalty = user.getTotalPenality();
				if (penalty >= 100)
					System.out.println("|\t" + user.name + "\t|\tRs." + penalty + "\t|");
			}
		}
	}

	public void show_not_returned_books() {
		collectBooks();
		System.out.println("|\tRent by (user name)\t|\tTitle of books\t|\tNo of books\t|\tDue date\t|\tPenalty\t|");
		for (Book book : RentedBooks) {
			if (book.getPenalty() >= 100) {
				System.out.println(
						"|\t" + book.rentBy + "\t|\t" + book.title + "\t|\t" + book.current_Quantity + "\t|\t"
								+ book.getDueDate().getDate() + "\t|\t" + book.getPenalty() + "\t|");
			}
		}
	}

	public void collectBooks() {
		RentedBooks.clear();
		for (String name : All_users.keySet()) {
			RentedBooks.addAll(All_users.get(name).My_books);
		}
		for (int i = 0; i < RentedBooks.size() - 1; i++) {
			for (int j = i + 1; j < RentedBooks.size(); j++) {
				if (RentedBooks.get(i).getTotalDays() < RentedBooks.get(j).getTotalDays()) {
					Book temp = RentedBooks.get(i);
					RentedBooks.set(i, RentedBooks.get(j));
					RentedBooks.set(j, temp);
				}
			}
		}
	}

	public Boolean isSubString(String string, String subString) {
		string = string.toLowerCase();
		subString = subString.toLowerCase();
		int i, j = 0;
		for (i = 0; i < string.length(); i++) {
			if (j == subString.length())
				break;
			if (string.charAt(i) == subString.charAt(j))
				j++;
			else
				j = 0;
		}
		return subString.length() > j ? false : true;
	}

}
