package librarymanagement;

public class Book {
    int initial_Quantity = 0, current_Quantity = 0, rating = 1;
    String title, author, category, rentBy; // rentBy - string that holds the user name
    private int penality = 0, totalDays = 0;
    Date rentDate = new Date(), dueDate = new Date();

    public void increase() {
        current_Quantity++;
        if (initial_Quantity < current_Quantity) {
            initial_Quantity = current_Quantity;
        }
    }

    public void increase(int value) {
        // int diff = initial_Quantity - current_Quantity;
        if (current_Quantity < initial_Quantity) {
            initial_Quantity += value;
            current_Quantity += value;
        } else {
            current_Quantity += value;
            if (initial_Quantity < current_Quantity) {
                initial_Quantity = current_Quantity;
            }
        }
    }

    Book(String Title, String Author, String Category, int Quantity) {
        title = Title;
        author = Author;
        category = Category;
        initial_Quantity = Quantity;
        current_Quantity = Quantity;
    }

    Book() {

    }

    public void increaseToIntial() {
        this.current_Quantity = this.initial_Quantity;
    }

    public int getPenalty() {
        return penality;
    }

    public void setPenality(int penalty) {
        this.penality = penalty;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public Date getRentDate() {
        return rentDate;
    }

    public void setRentDate(int year, int month, int day) {
        this.rentDate.year = year;
        this.rentDate.month = month;
        this.rentDate.day = day;
        setDueDate(year, month, day);
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(int year, int month, int day) {
        this.dueDate.year = year;
        this.dueDate.month = month;
        this.dueDate.day = day;
        this.dueDate.shift(Librarian.DUE);
    }
}