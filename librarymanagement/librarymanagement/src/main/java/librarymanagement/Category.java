package librarymanagement;

import java.util.*;

public class Category {
	HashMap<String,Book> books = new HashMap<>();
    String category_name;
    Category(String cat){
        category_name=cat;
    }

    public void add_Books(Book... new_book){
        for(Book b: new_book){
            if(books.get(b.title)==null)
            books.put(b.title, b);
            else
            books.get(b.title).current_Quantity++;
        }
    }

    public void remove_book(String title){
        books.remove(title);
    }

    public void display(){
        System.out.println("|\tTitle\t|\tAuthors\t|\tCategory name\t|\tRating\t|\tAvailable Books\t|\n");
        for(String key:books.keySet()){
            System.out.println("|\t"+books.get(key).title+"\t|\t"+books.get(key).author+"\t|\t"+category_name+"\t|\t"+books.get(key).rating+"\t|\t"+books.get(key).current_Quantity+"\t|");
        }
    }

    public Book get_book(String title){
        return books.get(title);
    }
}
