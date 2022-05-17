package librarymanagement;

import java.util.*;

public class User extends Librarian {
	Scanner input = new Scanner(System.in);
	String name;
	String password;
	Boolean isapproved = true;
	Boolean isRented = false;// Defaultly user not rent any books.
	Boolean isInitialChanged = false; 
	int totalPenality = 0;
	int monthDays[] = { 31, 28, 31, 30, 31, 30,
			31, 31, 30, 31, 30, 31 };
	ArrayList<Book> My_books = new ArrayList<>();

	User(String Name, String pass) {
		name = Name;
		password = pass;
	}

	public Boolean check_entry(String l_name, String pass) {
		return (l_name.equals(name) && pass.equals(password));
	}

	public Book getBook(String title) {
		for (Book book : My_books) {
			if (book.title.equals(title)) {
				return book;
			}
		}
		return null;
	}

	public void reset_password() {
		System.out.println("\nEnter old password:");
		String temp = input.nextLine();
		if (temp.equals(password)) {
			do {
				System.out.println("Enter new password:");
				password = input.nextLine();
			} while (temp.equals(password) || password.isEmpty());
			System.out.println("\nPassword changed!");
			isInitialChanged = true;
		} else {
			System.out.println("Old password is wrong!");
		}
	}

	public void rent_books_by_category() {
		System.out.println("Enter the category of the book:");
		String cat = input.nextLine();
		//input.nextLine();
		if (getCategory(cat) != null) {
			if (getCategory(cat).books.isEmpty()) {
				System.out.println("This category is empty now!");
			} else {
				System.out.println("Books are....");
				Category temp = (getCategory(cat));
				temp.display();
				System.out.println("Enter the book title.");
				String title = input.nextLine();
				//input.nextLine();
				if (temp.get_book(title) != null) {
					if (temp.get_book(title).current_Quantity != 0) {
						Book tempBook = temp.get_book(title);
						System.out.println(
								"How many books do you want to rent?(Maximum " + tempBook.current_Quantity + " )");
						int noOfBooks = input.nextInt();
						if (noOfBooks <= tempBook.current_Quantity) {
							if (noOfBooks <= 0) {
								System.out.println("Invalid value!");
								return;
							}
							rentBook(temp.get_book(title), noOfBooks);
							tempBook.current_Quantity -= noOfBooks;
							tempBook.rating++;
							this.isRented = true;
							System.out.println("Book is rented.");
						} else {
							System.out.println(
									"Sorry, there is only " + tempBook.current_Quantity + " books are available!");
						}
					} else {
						System.out.println(title + " is not available!");
					}

				} else {
					System.out.println("Book is not found!");
				}
			}

		} else {
			System.out.println("The given category is not available!");
		}
		//input.nextLine();
	}

	public void browse_books_by_category() {
		this.display_categories();
		System.out.println("Enter the category of the book:");
		String cat = input.nextLine();
		if (getCategory(cat) != null) {
			if (getCategory(cat).books.isEmpty()) {
				System.out.println("This category is now empty!");
			} else {
				System.out.println("Books are....");
				getCategory(cat).display();
				System.out.println("\n");
			}
		}
	}

	private void rentBook(Book b, int noOfBooks) {
		Book temp = new Book(b.title, b.author, b.category, noOfBooks);
		temp.rentBy = this.name;
		this.My_books.add(temp);
		setDueDate(b.title);
	}

	private void display_categories() {
		System.out.println("Categories are....");
		for (String cat : All_cat.keySet()) {
			System.out.println(cat + "\n");
		}
	}

	public void showRentedBooks() {
		System.out.println("Books rented are....");
		System.out.println("|\tNo.\t|\tTitle\t|\tAuthor\t|\tRented on\t|\tDue date\t|\tPenality\t|");
		int i = 1;
		for (Book book : My_books) {
			System.out.println(
					"|\t" + i++ + "\t|\t" + book.title + "\t|\t" + book.author + "\t|\t" + book.getRentDate().getDate()
							+ "\t|\t" + book.getDueDate().getDate() + "\t|\tRs."
							+ (book.getPenalty()) + "\t|");
		}
	}

	public int getTotalPenality() {
		totalPenality = 0;
		for (Book book : My_books) {
			totalPenality += book.getPenalty();
		}
		return totalPenality;
	}

	public void setDueDate(String title) {
		for (Book book : My_books) {
			if (book.title.equals(title)) {
				if (book.getRentDate().year == 0)
					book.setRentDate(current_date.year, current_date.month, current_date.day);
			}
		}
	}

	public int calc_penality(Book book) {
		int penalty = 0;
		int totalDays = getDifference(book.getRentDate());
		book.setTotalDays(totalDays);
		if (totalDays <= DUE) {
			penalty = 0;
		} else {
			if (totalDays - DUE == 1) {
				penalty += FORDAY1;
			} else if (totalDays - DUE == 2) {
				penalty += FORDAY1 + FORDAY2;
			} else if (totalDays - DUE == 3) {
				penalty += FORDAY1 + FORDAY2 + FORDAY3;
			} else {
				penalty += FORDAY1 + FORDAY2 + FORDAY3;
				for (int i = 0; i < totalDays - DUE - 3; i++) {
					penalty += FORDAY4;
				}
			}
		}
		penalty *= (book.current_Quantity);
		book.setPenality(penalty);
		return penalty;
	}

	int countLeapYears(Date d) {

		int years = d.year;
		if (d.month <= 2) {
			years--;
		}
		return years / 4 - years / 100 + years / 400;
	}

	int getDifference(Date rentDate) {

		int n1 = rentDate.year * 365 + rentDate.day;

		for (int i = 0; i < rentDate.month - 1; i++) {
			n1 += monthDays[i];
		}

		n1 += countLeapYears(rentDate);

		int n2 = current_date.year * 365 + current_date.day;
		for (int i = 0; i < current_date.month - 1; i++) {
			n2 += monthDays[i];
		}
		n2 += countLeapYears(current_date);
		return (n2 - n1);
	}

	public void updateToTotalPenality(int newPenality) {
		if (newPenality < 0) {
			for (Book book : My_books)
				book.setPenality(0);
			totalPenality = 0;
		} else {
			totalPenality = newPenality;
		}
	}

	public void removeBook(String title) {
		(All_cat.get(getBook(title).category)).get_book(title).increaseToIntial();
		My_books.remove(My_books.indexOf(getBook(title)));
	}

	public void returnAll() {
		for (Book book : My_books) {
			All_cat.get(book.category).get_book(book.title).increase(book.current_Quantity);
		}
		My_books.clear();
	}

	public void calculateAllNotReturned() {
		for (Book book : My_books) {
			if (book.getPenalty() >= 100) {
				System.out.println(
						"|\t" + name + "\t|\t" + book.title + "\t|\t" + book.current_Quantity + "\t|\t"
								+ book.current_Quantity + "\t|\t" + book.getPenalty() + "\t|");
			}
		}
	}

	public void search_book() {
		System.out.println("Select your option:");
		System.out.println("1)Search book by title.");
		System.out.println("2)Search book by author.");
		System.out.println("3)Search book by rating.");
		int option = input.nextInt();
		input.nextLine();
		switch (option) {
			case 1:
				searchByTitle();
				break;
			case 2:
				searchByAuthor();
				break;
			case 3:
				searchByRating();
				break;
			default:
				System.out.println("Invalid option!");
		}
	}

	private void searchByTitle() {
		System.out.println("Enter the book title:");
		//input.nextLine();
		String[] titles = input.nextLine().trim().split("\\s+");
		System.out.println("|\tTitle\t|\tAuthors\t|\tCategory name\t|\tRating\t|\tAvailable Books\t|\n");
		for (String title : titles) {
			System.out.println("\nThese are the some books matched with '"+title+"'.\n");
			for (String cat : All_cat.keySet()) {
				Category temp = All_cat.get(cat);
				for (String book : temp.books.keySet()) {
					if (isSubString(book, title)) {
						Book tempBook = temp.get_book(book);
						System.out.println("|\t" + tempBook.title + "\t|\t" + tempBook.author + "\t|\t" + cat + "\t|\t"
								+ tempBook.rating + "\t|\t" + tempBook.current_Quantity + "\t|");
						System.out.println();
					}
				}
			}
		}
	}

	private void searchByAuthor() {
		System.out.println("Enter the author name:");
		//input.nextLine();
		String[] authorName = input.nextLine().trim().split("\\s+");
		System.out.println("|\tTitle\t|\tAuthors\t|\tCategory name\t|\tRating\t|\tAvailable Books\t|\n");
		for (String author : authorName) {
			System.out.println("\nThese are the some author names matched with '"+author+"'.\n");
			for (String cat : All_cat.keySet()) {
				Category temp = All_cat.get(cat);
				for (Book book : temp.books.values()) {
					if (isSubString(book.author, author)) {
						System.out.println("|\t" + book.title + "\t|\t" + book.author + "\t|\t" + cat + "\t|\t"
								+ book.rating + "\t|\t" + book.current_Quantity + "\t|");
						System.out.println();
					}
				}
			}
		}
	}

	private void searchByRating() {
		System.out.println("Enter the book rating:");
		int rat = input.nextInt();
		input.nextLine();
		System.out.println("These are the some books with the '"+rat+"' rating.");
		System.out.println("|\tTitle\t|\tAuthors\t|\tCategory name\t|\tRating\t|\tAvailable Books\t|\n");
		for (String cat : All_cat.keySet()) {
			Category temp = All_cat.get(cat);
			for (String book : temp.books.keySet()) {
				if (temp.books.get(book).rating == rat) {
					Book tempBook = temp.get_book(book);
					System.out.println("|\t" + tempBook.title + "\t|\t" + tempBook.author + "\t|\t" + cat + "\t|\t"
							+ tempBook.rating + "\t|\t" + tempBook.current_Quantity + "\t|");
					System.out.println();
				}
			}
		}
	}

}