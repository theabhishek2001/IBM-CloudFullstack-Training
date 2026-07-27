package OOPs;
import OOPs.car;
import OOPs.Students;
import OOPs.book;

public class main {
    public static void main(String[] args) {
        // car c= new car();

        book book1= new book("Helen Keler", 800);
        book book2 = new book("Harry potter", 600);
        book book3 = new book("potter", 500);

        

        // System.out.println(book1.info());

        book[] books = {book1,book2,book3};

        library lib = new library("hamari", 2000, books);

        // lib.display();

        


        Students st1=new Students("Abhi",24,7);
        Students st2=new Students("Kash",25,8);
       

        // c.start();
        // c.stop();

        
        // System.out.println(c.topspeed);
        // System.out.println(st1.name);
        // System.out.println(st2.age);

        // st1.study();
        // st2.study();


        Integer a =new Integer(123);
        Double d= new Double(23.23);
        Boolean b =true; 

        System.out.println(a + " " + d + " " + b);
        
    }
}
