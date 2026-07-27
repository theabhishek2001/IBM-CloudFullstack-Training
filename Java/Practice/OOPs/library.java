package OOPs;

public class library {
    String name;
    int year;
    book[] books;

    library(String name,int year,book[] books){
        this.name=name;
        this.year=year;
        this.books=books;
    }

    void display(){
        System.out.println("The " + this.year + " " +  this.name);
        System.out.println("Book available : ");
        for(book book : books){
            System.out.println(book.display());
        }
    }


}
