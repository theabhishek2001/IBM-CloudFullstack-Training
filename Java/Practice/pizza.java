import java.util.Scanner;

public class pizza {
    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);

        String pizza;
        double price;
        int quantity;
        double total;


        System.out.print("What item you have bought : ");
        pizza=sc.nextLine();

        System.out.print("What is the price for each : $");
        price=sc.nextDouble();

        System.out.print("How many would you like :");
        quantity=sc.nextInt();

        total= price*quantity;

        System.out.println("You have bought " + quantity + " " + pizza );
        System.out.println("Your total is $"+ total);

    }
}
