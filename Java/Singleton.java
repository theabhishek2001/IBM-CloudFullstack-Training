
public class Singleton {
   

    public static void main(String[] args){

        Calculator c1= Calculator.getInstance();
        Calculator c2 =Calculator.getInstance();

        c1.x=4;
        c1.y=2;

        c2.x=7;
        c2.y=1;
        
        

        System.out.println(c1.diff());
        System.out.println(c2.diff());


    }
}
