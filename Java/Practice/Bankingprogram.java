import java.util.Scanner;

public class Bankingprogram {

    static Scanner sc  = new Scanner(System.in);

    static void showBalance(double balance){
        System.out.printf("$%.2f\n",balance);
    }

    static double Deposit(){
        double amount;

        System.out.println("Enter the amount : ");
        amount=sc.nextDouble();

        if(amount<0){
            System.out.println("Amount can never be negative");
            return 0;
        }
        else {
            return amount;
        }

        
    }


    static double Withdraw(){

        double amount;
        System.out.println("Enter amount which you want to Withdraw : ");
        amount=sc.nextDouble();

        System.out.println(amount);

        return amount;
    }

    public static void main(String[] args) {

        

        // declare varibles
        

        double balance=0;
        double Deposit;
        boolean isRunning=true;
        int choice;

        // Display menu

        while (isRunning) {

            System.out.println("*** This is the main menu ***");
            System.out.println("1. Show Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");

            System.out.println("Enter your choice : ");

            choice = sc.nextInt();
            

            switch (choice) {
                case 1:
                    showBalance(balance);

                    break;

                case 2:
                    balance=balance + Deposit();
                    
                    break;

                case 3:
                    
                    balance=balance-Withdraw();
                    break;
                
                case 4:
                    isRunning=false;
                    break;    
            
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }

        

        // Show Balance
        




        // Get and process user choice
    }
}
