package librarymanagement;

import java.util.*;
import java.time.LocalDate;

public class Main {

	static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		Librarian my_library = new Librarian();
		int choice;
		LocalDate today = LocalDate.now();

		Librarian.current_date.day = today.getDayOfMonth();
		Librarian.current_date.month = today.getMonthValue();
		Librarian.current_date.year = today.getYear();
		do {

			System.out.println("Welcome to library");
			System.out.println("Enter your choice:");
			System.out.println("1)Librarian login");
			System.out.println("2)User login");
			System.out.println("3)Quit");
			choice = input.nextInt();
			int option;
			input.nextLine();
			if (choice == 1) {
				String name, password;
				System.out.println("Enter your name:");
				name = input.nextLine();
				// name="Mohan";
				System.out.println("Enter your password");
				password = input.nextLine();
				System.out.println();
				if (my_library.check_entry(name, password)) {

					do {
						System.out.println("Select your option:");
						System.out.println("1)Add Category");
						System.out.println("2)Add Books");
						System.out.println("3)Edit quantity of a book");
						System.out.println("4)Delete Books");
						System.out.println("5)Set penalty for books not returned on time.");
						System.out.println("6)Add user along with initial password");
						System.out.println("7)Show report of defaulter list.");
						System.out.println("8)Show report of books not returned by due date.");
						System.out.println("9)Update user penalty amount and book return status.");
						System.out.println("10)Update current date.");
						System.out.println("11)Logout");
						option = input.nextInt();
						input.nextLine();
						switch (option) {
							case 1:
								my_library.setCategory();
								break;
							case 2:
								my_library.add_Books();
								break;
							case 3:
								my_library.Edit_quantity();
								break;
							case 4:
								my_library.Delete_books();
								break;
							case 5:
								my_library.setPenality();
								break;
							case 6:
								my_library.Add_user();
								break;
							case 7:
								my_library.getDefaultersList();
								break;
							case 8:
								my_library.show_not_returned_books();
								break;
							case 9:
								my_library.UpdateFineAmountAndBookReturnStatus();
								break;
							case 10:
								my_library.updateDateAndCalculateFine();
								break;
						}
					} while (option != 11);
				} else {
					System.out.println("Your username or password is wrong!");
				}

			} else if (choice == 2) {
				String name, password;
				System.out.println("Enter your name:");
				name = input.nextLine();
				if (name.isEmpty()) {
					System.out.println("Empty string is not accepted!");
					continue;
				}
				if (my_library.find_user(name) != null) {
					System.out.println("Enter your password");
					password = input.nextLine();
					if (password.isEmpty()) {
						System.out.println("Empty string is not accepted!");
						continue;
					}
					User user;
					if ((my_library.find_user(name)).check_entry(name, password)) {
						user = (my_library.find_user(name));
						if (!user.isInitialChanged) {
							System.out.println(
									"NOTE:You are signed in with a initial password!\nSo reset your password now!");
							user.reset_password();
						} else {
							do {
								Librarian.calculateFine();
								System.out.println("========Welcome=======");
								System.out.println("Select your option:");
								System.out.println("1)Reset password.");
								System.out.println("2)Browse books by category.");
								System.out.println("3)Search a book.");
								if (user.isapproved || user.totalPenality < 100)
									System.out.println("4)Rent a book.");
								System.out.println("5)Show my rented books.");
								System.out.println("6)Log out.");
								option = input.nextInt();
								input.nextLine();
								switch (option) {
									case 1:
										user.reset_password();
										break;
									case 2:
										user.browse_books_by_category();
										break;
									case 3:
										user.search_book();
										break;
									case 4:
										if (!user.isapproved)
											break;
										user.rent_books_by_category();
										break;
									case 5:
										user.showRentedBooks();
										break;
									case 6:
										break;
									// case 7:
									// user.show();
									// break;
								}
							} while (option != 6);
						}

					} else {
						System.out.println("Your username or password is wrong!");
					}
				} else {
					System.out.println("Check your user name!");
				}

			} else {
				if (choice != 3) {
					System.out.println("Select a valid option!");
				}
			}
		} while (choice != 3);
	}

}
