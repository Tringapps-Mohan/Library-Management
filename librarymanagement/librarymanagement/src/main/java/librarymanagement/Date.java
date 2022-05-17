package librarymanagement;

import java.util.Scanner;

public class Date {
	int day;
	int month;
	int year;
	Scanner input = new Scanner(System.in);

	public void set(int d, int m, int y) {
		day = d;// starts from 1
		month = m;// starts from 1
		year = y;
	}

	int[] months = {
			31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
	};

	public Boolean isleap(int year) {
		if (year % 4 == 0 && year % 100 != 0) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean change_date() {
		System.out.println("Enter the year :");
		int year = input.nextInt();
		input.nextLine();
		int month;
		int day;
		if (year > 0) {
			if (isleap(year)) {
				months[1] = 29;
				System.out.println("Enter the month:");
				month = input.nextInt();
				input.nextLine();
				if (month == 2) {
					System.out.println("Enter the day:");
					day = input.nextInt();
					input.nextLine();
					if (day <= 29 && day >= 1) {
						set(day, month, year);
						return true;
					} else {
						System.out.println("Day is not valid!");
						return false;
					}
				} else {
					if (month > 0 && month < 13) {
						System.out.println("Enter the day:");
						day = input.nextInt();
						input.nextLine();
						if (day <= 29 && day >= 1) {
							set(day, month, year);
							return true;
						} else {
							System.out.println("Day is not valid!");
							return false;
						}
					} else {
						System.out.println("Month is not valid!");
						return false;
					}
				}
			} else {
				months[1] = 28;
				System.out.println("Enter the month:");
				month = input.nextInt();
				input.nextLine();
				if (month > 0 && month < 13) {
					System.out.println("Enter the day:");
					day = input.nextInt();
					input.nextLine();
					if (day <= months[month - 1] && day > 0) {
						set(day, month, year);
						return true;
					} else {
						System.out.println("Day is not valid!");
						return false;
					}
				} else {
					System.out.println("Month is not valid!");
					return false;
				}
			}
		} else {
			System.out.println("Year is not valid!");
			return false;
		}
	}

	public Boolean change_date_By_value(int day, int month, int year) {
		if (year > 0) {
			if (isleap(year)) {
				months[1] = 29;
				if (month == 2) {
					if (day <= 29 && day >= 1) {
						set(day, month, year);
						return true;
					} else {
						System.out.println("Day is not valid!");
						return false;
					}
				} else {
					if (month > 0 && month < 13) {
						if (day <= 29 && day >= 1) {
							set(day, month, year);
							return true;
						} else {
							System.out.println("Day is not valid!");
							return false;
						}
					} else {
						System.out.println("Month is not valid!");
						return false;
					}
				}
			} else {
				months[1] = 28;
				if (month > 0 && month < 13) {
					if (day <= months[month - 1] && day > 0) {
						set(day, month, year);
						return true;
					} else {
						System.out.println("Day is not valid!");
						return false;
					}
				} else {
					System.out.println("Month is not valid!");
					return false;
				}
			}
		} else {
			System.out.println("Year is not valid!");
			return false;
		}
	}

	public void shift(int no_of_days) {
		int temp = no_of_days;
		for (int i = 0; i < temp; i++) {
			no_of_days = 1;
			if (no_of_days > 0) {
				months[1] = isleap(year) ? 29 : 28;
				if ((day + no_of_days) <= months[month - 1]) {
					day += no_of_days;
				} else {
					int extra = months[month - 1] - day;
					if (month == 12) {
						year += 1;
						month = 1;
						day = no_of_days - extra;
					} else {
						month++;
						day = no_of_days - extra;
					}
				}
			} else {
				System.out.println("No of days must be positive!");
			}
		}

	}

	public String getDate() {
		return this.day + "/" + this.month + "/" + this.year;
	}
}
