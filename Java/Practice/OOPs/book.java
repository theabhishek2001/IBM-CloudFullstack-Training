package OOPs;

public class book {
    String title;
    int pages;

    book(String title,int pages){
        this.title=title;
        this.pages=pages;
    }

    String display(){
        return this.title + " " + this.pages + " " + "pages"; 
    }
}
