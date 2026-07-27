import java.util.Scanner;
import java.util.Random;

public class numberguessgame {
    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);

        Random rn =new Random();

        int number;
        

        int randno= rn.nextInt(100);

        System.out.println("Guess the number between 1 to 100");
        number= sc.nextInt();

        int trycount=0;

        while(true){
            if(number==randno){
                System.out.println("Great you have guessed it correctly");
                break;
            }
            else if(number>randno){
                System.out.println("Number is greater than the original");
                number= sc.nextInt();
            }
            else{
                System.out.println("Number is less than the original");
                number= sc.nextInt();
            }
            if(trycount>10){
                System.out.println("Try limit has been exeed");
                break;
            }
            trycount++;
            
        }



    }
}
