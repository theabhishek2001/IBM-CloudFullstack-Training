import java.util.Scanner;

public class compundInterest {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);

        double principle;
        double amount;
        double CI;
        double rate;
        int time;

        // A = A + CI;
        // CI = pow((P[1+R/100),2)

        System.out.print("Enter priciple value :");
        principle=sc.nextDouble();

        System.out.print("Enter amount :");
        amount=sc.nextDouble();

        System.out.println("Enter Rate and time :");
        time=sc.nextInt();
        rate=sc.nextDouble();

        CI= principle* (Math.pow((1+rate/100),time));

        amount =  amount + CI;

        System.out.printf("Total amount is  %.2f" ,amount);


    }
}
